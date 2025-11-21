package com.test.jpa.model;

import com.test.jpa.entity.Item;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
    private Long seq;
    private String name;
    private Integer price;
    private String color;
    private Integer qty;
    private String description;

    public ItemDTO(String name, String color, Integer price) {
        this.name = name;
        this.color = color;
        this.price = price;
    }
    /**
     * DTO와 Entity 매핑을 위한 메서드
     * @return 엔티티로 변환한 결과
     */
    public Item toEntity() {
        return Item.builder()
                    .seq(this.seq)
                    .color(this.color)
                    .name(this.name)
                    .price(this.price)
                    .qty(this.qty)
                    .description(this.description)
                    .build();
    }
}