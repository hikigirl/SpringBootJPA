package com.test.jpa.entity;

import com.test.jpa.model.TagDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tblTag")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @SequenceGenerator(name = "seqTagGen", allocationSize = 1, sequenceName = "seqTag")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTagGen")
    private Long seq;

    @Column(nullable = false, length = 100)
    private String tag;

    //Tag to Tagging -> 일대다
    @OneToMany(mappedBy = "tag")
    private List<Tagging> taggings;

    public TagDTO toDTO() {
        return TagDTO.builder()
                .seq(this.seq)
                .tag(this.tag)
                .build();
    }
}
