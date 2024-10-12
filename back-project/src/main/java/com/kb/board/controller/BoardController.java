package com.kb.board.controller;

import com.kb.board.dto.*;
import com.kb.board.service.BoardService;
import com.kb.member.dto.Member;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
@Api(value = "BoardController", tags = "게시판 정보")
@PropertySource({"classpath:/application.properties"})
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Value("#{'${os_type}' == 'win' ? '${file_save_location_win}/board':'${file_save_location_other}/board'}")
    public String BASE_DIR;

    private final BoardService service;

    @GetMapping("/types")
    public ResponseEntity<List<BoardCategory>> getTypes() {
        List<BoardCategory> types = service.getCategoryList();
        return ResponseEntity.ok(types);
    }

    @GetMapping("") // 게시판 목록 내용 조회
    public ResponseEntity<BoardPageResult> getList(BoardParam boardParam) {
        BoardPageResult result = service.getBoardList(boardParam);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{bno}/posts") // 게시글 bno 별 리스트 조회
    public ResponseEntity<BoardPostPageResult> getPosts(@PathVariable Long bno, PostParam postParam) {
        postParam.setBoardId(bno);  // 게시판 ID 설정
        BoardPostPageResult postResult = service.getPostList(postParam);

        return ResponseEntity.ok(postResult);
    }


    @GetMapping("/{bno}")
    public ResponseEntity<BoardPost> getById(@PathVariable int bno) {
        return ResponseEntity.ok(service.getBoard(bno));
    }

    @PostMapping("/{type}") // 게시판 글 작성
    public ResponseEntity<BoardPost> create(
            @PathVariable String type, // URL의 마지막 부분에서 type 받기
            @ModelAttribute @Valid BoardDTO boardDTO,
            @RequestParam(name = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal Member principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        int bno;

        // type에 따라 bno 값 설정
        switch (type) {
            case "stability":
                bno = 1;
                break;
            case "neutral":
                bno = 2;
                break;
            case "activeInvestment":
                bno = 3;
                break;
            case "aggressiveInvestment":
                bno = 4;
                break;
            default:
                return ResponseEntity.badRequest().body(null); // 잘못된 type인 경우
        }

        // BoardDTO에 bno와 type 설정
        boardDTO.setBno(bno);
        boardDTO.setType(type);

        BoardPost boardPost = boardDTO.toBoardPost(); // DTO를 게시글 객체로 변환
        boardPost.setMemberId(principal.getMno());

        log.info("Creating boardPost with bno: " + boardPost.getBno());

        BoardPost createdPost = boardService.createBoardPost(boardPost, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{bno}")
    public ResponseEntity<BoardPost> update(@PathVariable int bno,
                                            BoardDTO boardDTO,
                                            @RequestParam(name = "files", required = false) List<MultipartFile> files) {
        BoardPost boardPost = boardDTO.toBoardPost();
        boardPost.setBno(bno);
        System.out.println("files " + files);
        return ResponseEntity.ok(service.updateBoard(boardPost, files));
    }

    @DeleteMapping("/{bno}")
    public ResponseEntity<BoardPost> delete(@PathVariable int bno) {
        return ResponseEntity.ok(service.deleteBoard(bno));
    }

    @GetMapping("/download/{fno}")
    public ResponseEntity<Resource> download(@PathVariable long fno) throws Exception {
        BoardAttachFile attach = service.getAttachment(fno);
        Resource resource = new UrlResource("file:" + BASE_DIR + "/" + attach.getRenamedFilename());
        String fileName = new String(attach.getOriginalFilename().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1); // 크롬
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString()).body(resource);
    }

    // 이미지 출력
    @GetMapping("/file/{fileName}")
    @ResponseBody
    public Resource downloadImage(@PathVariable("fileName") String fileName) throws Exception {
        return new UrlResource("file:" + BASE_DIR + fileName);
    }

    @DeleteMapping("/attachment/{fno}")
    public ResponseEntity<Boolean> deleteAttachment(@PathVariable long fno) throws Exception {
        return ResponseEntity.ok(service.deleteAttachment(fno));
    }

    @PostMapping("/reply/{bno}")
    public ResponseEntity<BoardReply> createReply(
                        @PathVariable int bno,
                      @RequestBody BoardReplyDTO replyDTO,
                      @AuthenticationPrincipal Member principal) throws Exception {
        BoardReply reply = replyDTO.toReply();
        reply.setBno(bno);
        reply.setMno(principal.getMno());
        BoardReply result = service.createReply(reply);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reply/{rno}")
    public ResponseEntity<BoardReply> deleteReply(@PathVariable long rno,
                    @AuthenticationPrincipal Member principal) throws Exception {
        BoardReply reply = service.getReply(rno);
        if(!reply.getMemberId().equals(principal.getId())) {
            throw new IllegalAccessException();
        }
        int result = service.delteReply(rno);
        if(result != 1) {
            throw new Exception("DB 에러");
        }
        return ResponseEntity.ok(reply);
    }
}
