package com.kb.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardPostDTO {
    private int boardId;        // 게시글 ID
    private String title;       // 제목
    private String content;     // 내용
    private int readCount;      // 조회수
    private String createdDate; // 생성일 (Timestamp 형식)
    private String modifiedDate; // 수정일 (Timestamp 형식)
    private String type;        // 카테고리 타입
    private int commentCount;    // 댓글 수
    private int likesCount;      // 좋아요 수
    private int memberId;       // 회원 ID

    // 필요한 경우 toEntity 메서드 추가
}
