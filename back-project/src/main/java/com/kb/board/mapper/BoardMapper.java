package com.kb.board.mapper;

import com.kb.board.dto.*;

import java.util.List;

public interface BoardMapper {
    List<BoardCategory> selectBoardCategory();
    List<BoardPost> selectBoardList(BoardParam param);
    int selectBoardCount(BoardParam param);
    BoardPost selectBoardByBno(int bno);
    int insertBoard(BoardPost boardPost);
    int updateBoard(BoardPost boardPost);
    int updateReadCount(BoardPost boardPost);
    int deleteBoard(long bno);
    BoardAttachFile selectAttachFileByFno(long fno);
    List<BoardAttachFile> selectAttachFileByBno(int bno);
    int insertAttachFile(BoardAttachFile attachFile);
    int deleteAttachFile(long fno);
    int insertReply(BoardReply reply);
    BoardReply selectReplyByRno(long rno);
    List<BoardReply> selectReplyByBno(int bno);
    int deleteReply(long rno);
}
