package com.kb.board.dto;

// 기타 import 생략

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardPost {
    private long boardId;       // 게시물 ID
    @NotNull(message = "Title cannot be null")
    private String title;       // 제목
    @NotNull(message = "Content cannot be null")
    private String content;     // 내용
    private int readCount;      // 조회수
    private String type;        // 타입
    private BoardStatus status; // 상태 ('y' 또는 'n')
    private int commentCount;    // 댓글 수
    private int likesCount;     // 좋아요 수
    private long memberId;      // 작성자 ID
    private Date createdDate;   // 생성 날짜
    private Date modifiedDate;  // 수정 날짜

    private ArrayList<BoardReply> replyList;
    private ArrayList<BoardAttachFile> boardAttachFileList;

    public BoardPost toEntity() {
        return BoardPost.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .readCount(readCount)
                .type(type)
                .status(status) // 이미 BoardStatus 타입으로 사용
                .commentCount(commentCount)
                .likesCount(likesCount)
                .memberId(memberId)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }

}
