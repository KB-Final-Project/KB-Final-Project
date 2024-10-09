package com.kb.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {
    private long bno;        // 게시물 번호
    private String type;     // 타입
    private String title;    // 제목
    private String content;  // 내용
    private String status;   // 상태 ('y' 또는 'n')

    public Board toBoard() {
        return Board.builder()
                .bno(bno)
                .type(type)
                .title(title)
                .content(content)
                .status(status)
                .build();
    }
}
