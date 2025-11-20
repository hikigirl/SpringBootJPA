package com.test.jpa.entity;

import com.test.jpa.model.BoardDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tblBoard")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @SequenceGenerator(name = "seqBoardGen", allocationSize = 1, sequenceName = "seqBoard")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBoardGen")
    private Long seq;

    @Column(nullable = false, length = 1000)
    private String subject;

    @Column(nullable = false, length = 4000)
    private String content;

    @Column(nullable = false)
    private String regdate;
    //private String user;

    //Board to User -> 다대일
    @ManyToOne
    @JoinColumn(name = "id")
    private User user; //사용자 ID(FK)

    //Board to Tagging -> 일대다
    @OneToMany(mappedBy = "board")
    private List<Tagging> taggings;

    //엔티티를 DTO로 변환, User에서 ID만 꺼내서 DTO로 변환한다.
    public BoardDTO toDTO() {
        return BoardDTO.builder()
                .seq(this.seq)
                .subject(this.subject)
                .content(this.content)
                .regdate(this.regdate)
                .user(user.getName())
                .build();
    }
}
