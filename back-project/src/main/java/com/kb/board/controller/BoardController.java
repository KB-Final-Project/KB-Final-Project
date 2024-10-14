package com.kb.board.controller;

import com.kb.board.dto.*;
import com.kb.board.service.BoardService;
import com.kb.member.dto.Member;
import com.kb.member.service.MemberService;
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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
@Api(value = "BoardController", tags = "게시판 정보")
@PropertySource({"classpath:/application.properties"})
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

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
        postParam.setBno(bno);      // 게시글 번호 추가
        BoardPostPageResult postResult = service.getPostList(postParam);

        // 각 게시글에 작성자 ID 추가
        List<BoardPost> postsWithAuthors = postResult.getPostList().stream()
                .map(post -> {
                    Member member = memberService.findByMno((int) post.getMemberId()); // memberId로 Member 조회
                    post.setAuthorId(member.getId()); // 작성자 ID 설정
                    return post; // 수정된 BoardPost 객체 반환
                })
                .collect(Collectors.toList());

        postResult.setPostList(postsWithAuthors); // 수정된 리스트 업데이트

        return ResponseEntity.ok(postResult);
    }

    @GetMapping("/{postId}") // 게시글 조회
    public ResponseEntity<BoardPost> getById(@PathVariable long postId) {
        return ResponseEntity.ok(service.getBoard(postId));
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
        boardPost.setAuthorId(String.format("%d", principal.getMno()));

        log.info("Creating boardPost with bno: " + boardPost.getPostId());

        BoardPost createdPost = boardService.createBoardPost(boardPost, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<BoardPost> update(@PathVariable int postId,
                                            BoardDTO boardDTO,
                                            @RequestParam(name = "files", required = false) List<MultipartFile> files) {
        try {
            BoardPost boardPost = boardDTO.toBoardPost();
            boardPost.setPostId(postId); // 게시글 번호를 postId로 설정
            System.out.println("files " + files);
            return ResponseEntity.ok(service.updateBoard(boardPost, files));
        } catch (Exception e) {
            log.error("Error updating board post: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @DeleteMapping("/{postId}")
    public ResponseEntity<BoardPost> delete(@PathVariable long postId) {
        return ResponseEntity.ok(service.deleteBoard(postId));
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

    // 좋아요 버튼
    @PostMapping("/{postId}/like")
    public ResponseEntity<BoardPost> likePost(@PathVariable long postId, @AuthenticationPrincipal Member principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            // 사용자가 이미 좋아요를 눌렀는지 확인
            boolean alreadyLiked = boardService.checkLikeExists(postId, principal.getMno());

            if (alreadyLiked) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 이미 좋아요를 눌렀으면 409 Conflict 반환
            }

            // 좋아요 추가
            boardService.addLike(postId, principal.getMno());

            // 좋아요 수 업데이트 (여기서 게시물 정보를 다시 조회)
            BoardPost updatedPost = boardService.getPostWithLikesCount(postId);

            return ResponseEntity.ok(updatedPost);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error liking post: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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

    @PostMapping("/reply/{postId}")
    public ResponseEntity<BoardReply> createReply(
                        @PathVariable long postId,
                      @RequestBody BoardReplyDTO replyDTO,
                      @AuthenticationPrincipal Member principal) throws Exception {
        BoardReply reply = replyDTO.toReply();
        reply.setPostId(postId);
        reply.setMno(principal.getMno());
        BoardReply result = service.createReply(reply);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reply/{rno}")
    public ResponseEntity<BoardReply> deleteReply(
            @PathVariable int rno,
            @AuthenticationPrincipal Member principal) throws Exception {

        if (principal == null) {
            throw new IllegalAccessException("User is not authenticated");
        }

        BoardReply reply = service.getReply(rno);
        if (reply == null) {
            throw new NoSuchElementException("Reply not found for rno: " + rno);
        }

        if (!reply.getMemberId().equals(principal.getId())) {
            throw new IllegalAccessException("User does not have permission to delete this reply");
        }

        int result = service.deleteReply(rno);
        if (result != 1) {
            throw new Exception("DB error");
        }

        return ResponseEntity.ok(reply);
    }
}
