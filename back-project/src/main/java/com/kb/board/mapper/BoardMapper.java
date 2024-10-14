package com.kb.board.mapper;

import com.kb.board.dto.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoardMapper {
    List<BoardCategory> selectBoardCategory();
    List<BoardPost> selectBoardList(BoardParam param);
    int selectBoardCount(BoardParam param);
    BoardPost selectBoardPostByPostId(Long postId);
    int selectPostCount(PostParam postParam); // 게시글 수 조회
    List<BoardPost> selectPostList(PostParam postParam); // 게시글 목록 조회
    int insertBoardPost(BoardPost boardPost);
    int updateBoardPost(BoardPost boardPost);
    int updateReadCount(BoardPost boardPost);
    int deleteBoard(long bno);
    BoardAttachFile selectAttachFileByFno(long fno);
    List<BoardAttachFile> selectAttachFileByPostId(long postId);
    int insertAttachFile(BoardAttachFile attachFile);
    int deleteAttachFile(long fno);
    int insertReply(BoardReply reply);
    BoardReply selectReplyByRno(long rno);
    List<BoardReply> selectReplyByBno(int bno);
    int deleteReply(int rno);
    String getAuthorIdByMno(Integer mno);
    void incrementLikesCount(@Param("postId") long postId);
}
