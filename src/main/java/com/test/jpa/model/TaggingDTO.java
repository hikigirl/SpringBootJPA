package com.test.jpa.model;

import com.test.jpa.entity.Tagging;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaggingDTO {
    private Long seq;
    private Long tagSeq;
    private Long boardSeq;

    public Tagging toEntity() {
        return Tagging.builder()
                .seq(this.seq)
                //.tag().board() //따로 처리 필요
                .build();
    }
}
