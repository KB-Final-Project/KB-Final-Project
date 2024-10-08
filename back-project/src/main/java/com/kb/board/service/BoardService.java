package com.kb.board.service;

import com.kb.board.dto.*;
import com.kb.board.mapper.BoardMapper;
import com.kb.common.pagination.PageInfo;
import com.kb.common.util.UploadFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
public class BoardService {
    @Value("#{'${os_type}' == 'win' ? '${file_save_location_win}/board':'${file_save_location_other}/board'}")
    public String BASE_DIR;

    private final BoardMapper mapper;
    private static List<BoardCategory> categoryList;
    private static final int PAGE_LIMIT = 5;
    private static final int LIST_LIMIT = 12; // 게시글 보여질 list 수

    public List<BoardCategory> getCategoryList() {
        if (categoryList == null) {
            categoryList = mapper.selectBoardCategory();
        }
        return categoryList;
    }

    public BoardPageResult getBoardList(BoardParam boardParam) {
        int totalSize = mapper.selectBoardCount(boardParam);
        int listLimit = boardParam.getAmount() == 0 ? LIST_LIMIT : boardParam.getAmount();
        PageInfo pageInfo = new PageInfo(boardParam.getPage(), totalSize, listLimit, PAGE_LIMIT);
        boardParam.setLimit(pageInfo.getListLimit());
        boardParam.setOffset(pageInfo.getStartList() - 1);
        List<BoardPost> boardPostList = mapper.selectBoardList(boardParam);
        if (boardPostList == null || boardPostList.isEmpty()) {
            boardPostList = new ArrayList<>();
        }
        return new BoardPageResult(boardPostList, boardParam, pageInfo, getCategoryList(),totalSize);
    }

    @Transactional
    public BoardPost getBoard(int bno) {
        log.info("get......" + bno);
        BoardPost boardPost = mapper.selectBoardByBno(bno);
        // null 체크 추가
        if (boardPost == null) {
            throw new NoSuchElementException("No board found with id: " + bno);
        }

        boardPost.setReadCount(boardPost.getReadCount() + 1);
        mapper.updateReadCount(boardPost);
        log.info("========================" + boardPost);
        return Optional.of(boardPost)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(rollbackFor = Exception.class) // 2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
    public BoardPost createBoard(BoardPost boardPost, List<MultipartFile> files) {
        log.info("create......" + boardPost);
        int result = mapper.insertBoard(boardPost);
        if (result != 1) {
            throw new NoSuchElementException("Failed to insert board post.");
        }

        // 방금 삽입한 게시물 ID를 다시 가져오기
        long generatedBno = boardPost.getBno(); // 여기에 데이터베이스에서 생성된 ID를 설정

        // 파일 업로드 처리
        if (files != null && !files.isEmpty()) {
            upload(generatedBno, files);
        }
        return getBoard((int) generatedBno);
    }


    private void upload(long bno, List<MultipartFile> files) {
        for (MultipartFile part : files) {
            if (part.isEmpty()) continue;
            try {
                String renameFileName = UploadFiles.upload(BASE_DIR, part);
                BoardAttachFile attach = new BoardAttachFile(0, bno, part.getOriginalFilename(), renameFileName, part.getContentType(), part.getSize(), null);
                mapper.insertAttachFile(attach);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteFile(String savePath, BoardAttachFile boardAttachFile) {
        File file = new File(savePath + "/" + boardAttachFile.getRenamedFilename());
        if (file.exists()) {
            file.delete();
        }
    }


    @Transactional
    public BoardPost updateBoard(BoardPost boardPost, List<MultipartFile> files) {
        log.info("update...... " + boardPost);
//        Board oldBoard = getBoard(board.getBno());

        int result = mapper.updateBoard(boardPost);
        if (result != 1) {
            throw new NoSuchElementException();
        }

        // 파일 업로드 처리
        if (files != null && !files.isEmpty()) {
            upload(boardPost.getBno(), files);
        }

        return getBoard(boardPost.getBno());
    }


    @Transactional
    public BoardPost deleteBoard(long bno) {
        log.info("delete...." + bno);
        BoardPost boardPost = getBoard((int) bno);

        List<BoardAttachFile> oldFiles = boardPost.getBoardAttachFileList();
        for (BoardAttachFile old : oldFiles) {
            deleteFile(BASE_DIR, old);
        }

        int result = mapper.deleteBoard(bno);
        if (result != 1) {
            throw new NoSuchElementException();
        }
        return boardPost;
    }

    public BoardAttachFile getAttachment(long fno) {
        return mapper.selectAttachFileByFno(fno);
    }

    public boolean deleteAttachment(long fno) {
        BoardAttachFile attachFile =  mapper.selectAttachFileByFno(fno);
        deleteFile(BASE_DIR, attachFile);
        return mapper.deleteAttachFile(fno) == 1;
    }

    public BoardReply createReply(BoardReply reply) {
        int result = mapper.insertReply(reply);
        reply = mapper.selectReplyByRno(reply.getRno());
        return reply;
    }

    public BoardReply getReply(long rno) {
        return mapper.selectReplyByRno(rno);
    }

    public int delteReply(long rno) {
        return mapper.deleteReply(rno);
    }
}
