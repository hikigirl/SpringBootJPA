package com.test.jpa.entity;

import com.test.jpa.model.ItemDTO;
import jakarta.persistence.*;
import lombok.*;

//엔티티 클래스
//역할: tblItem 테이블을 자바와 중계해주는 역할
//Java에서 item 클래스를 조작 -> (개발자는 개입X) -> DB의 tblItem에 반영
//DTO 역할과 일부 유사
//DTO는 단순한 상자, Entity는 기능이 많은 상자 정도..

@Entity
@Getter //읽기 전용으로 사용하는 것을 권장
@Builder
@NoArgsConstructor //필수) 엔티티 클래스에는 반드시 기본 생성자가 구현되어야 한다.
@AllArgsConstructor
@ToString
@Table(name = "tblItem") //DB에 있는 테이블과 엔티티 클래스를 연결
public class Item {
    @Id //PK에 붙이는 어노테이션
    @Column(name = "seq") //실제 테이블의 컬럼명, 생략 가능하지만 멤버명과 컬럼명 동일해야 함
    @SequenceGenerator(name = "seqItemGen", allocationSize = 1, sequenceName = "seqItem")
    //@GeneratedValue(strategy = 기본키를 확보하는 방식(DB별로 상이))
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqItemGen")
    private Long seq;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "color", nullable = false, length = 50)
    private String color;

    //nullable = true는 default라 보통 생략하는 편
    @Column(name = "qty")
    private Integer qty;

    @Column(name = "description", length = 1000)
    private String description;

    /**
     * Entity - DTO간 변환 메서드
     * @return DTO 변환 결과
     */
    public ItemDTO toDTO() {
        return ItemDTO.builder()
                .seq(this.seq)
                .color(this.color)
                .name(this.name)
                .price(this.price)
                .qty(this.qty)
                .description(this.description)
                .build();
    }

}