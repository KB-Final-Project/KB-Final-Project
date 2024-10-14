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
    BoardAttachFile selectAttachFileByFno(long postId);
    List<BoardAttachFile> selectAttachFileByPostId(long postId);
    int insertAttachFile(BoardAttachFile attachFile);
    int deleteAttachFile(long fno);
    int insertReply(BoardReply reply);
    BoardReply selectReplyByRno(long rno);
    List<BoardReply> selectReplyByBno(int bno);
    int deleteReply(int rno);
    String getAuthorIdByMno(Integer mno);
    // 게시글 조회
    BoardPost getBoardPost(long postId);
    // 특정 게시물에 대한 사용자의 좋아요 여부 확인
    boolean checkLikeExists(@Param("postId") long postId, @Param("memberId") long memberId);
    // 좋아요 추가
    void insertLike(@Param("postId") long postId, @Param("memberId") long memberId);
    // 특정 게시물의 좋아요 수 계산
    int countLikes(long postId);
}
