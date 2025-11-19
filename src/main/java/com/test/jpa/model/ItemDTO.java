package com.test.jpa.model;

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
}
