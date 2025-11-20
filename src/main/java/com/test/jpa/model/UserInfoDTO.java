package com.test.jpa.model;

import com.test.jpa.entity.UserInfo;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String id;
    private Integer age;
    private String address;
    private String gender;

    public UserInfo toEntity() {
        return UserInfo.builder()
                .id(this.id)
                .age(this.age)
                .address(this.address)
                .gender(this.gender)
                .build();
    }
}
