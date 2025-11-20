package com.test.jpa.model;

import com.test.jpa.entity.Board;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long seq;
    private String subject;
    private String content;
    private String regdate;
    private String user; //사용자 ID(FK)

    public Board toEntity() {
        return Board.builder()
                .seq(this.seq)
                .subject(this.subject)
                .content(this.content)
                .regdate(this.regdate)
                .build(); //user는 따로 처리가 필요함
    }
}
