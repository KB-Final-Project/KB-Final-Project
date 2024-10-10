package com.kb.board.dto;

// 기타 import 생략

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardPost {
    private long postId;
    private int bno;       // 게시물 ID
    @NotNull(message = "Title cannot be null")
    private String title;       // 제목
    @NotNull(message = "Content cannot be null")
    private String content;     // 내용
    private int readCount;      // 조회수
    private String type;        // 타입
    private String status; // 상태 ('y' 또는 'n')
    private int commentCount;    // 댓글 수
    private int likesCount;     // 좋아요 수
    private long memberId;      // 작성자 ID
    private Date createdDate;   // 생성 날짜
    private Date modifiedDate;  // 수정 날짜

    private ArrayList<BoardReply> replyList;
    private ArrayList<BoardAttachFile> boardAttachFileList;

    public static BoardPost fromDTO(BoardDTO boardDTO, long memberId) {
        return BoardPost.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .readCount(0) // 기본값 설정
                .type(boardDTO.getType())
                .status(boardDTO.getStatus()) // 상태는 String으로 가져옴
                .commentCount(0) // 기본값 설정
                .likesCount(0) // 기본값 설정
                .memberId(memberId)
                .createdDate(new Date()) // 현재 날짜로 설정
                .modifiedDate(new Date()) // 현재 날짜로 설정
                .build();
    }

    @Override
    public String toString() {
        return "BoardPost{" +
                "bno=" + bno +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", readCount=" + readCount +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", commentCount=" + commentCount +
                ", likesCount=" + likesCount +
                ", memberId=" + memberId +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
