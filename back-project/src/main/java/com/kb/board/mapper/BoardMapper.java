package com.kb.board.mapper;

import com.kb.board.dto.*;

import java.util.List;

public interface BoardMapper {
    List<BoardCategory> selectBoardCategory();
    List<BoardPost> selectBoardList(BoardParam param);
    int selectBoardCount(BoardParam param);
    BoardPost selectBoardByBoardId(long boardId);
    int insertBoard(BoardPost boardPost);
    int updateBoard(BoardPost boardPost);
    int updateReadCount(BoardPost boardPost);
    int deleteBoard(long boardId);
    BoardAttachFile selectAttachFileByFno(long fno);
    List<BoardAttachFile> selectAttachFileByBno(long bno);
    int insertAttachFile(BoardAttachFile attachFile);
    int deleteAttachFile(long fno);
    int insertReply(BoardReply reply);
    BoardReply selectReplyByRno(long rno);
    List<BoardReply> selectReplyByBno(long bno);
    int deleteReply(long rno);
}
