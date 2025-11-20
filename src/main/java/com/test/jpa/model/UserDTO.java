package com.test.jpa.model;

import com.test.jpa.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String pw;
    private String name;

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .pw(this.pw)
                .name(this.name)
                .build();
    }
}
