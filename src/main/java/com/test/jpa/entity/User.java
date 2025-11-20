package com.test.jpa.entity;

import com.test.jpa.model.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tblUser")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @Column(length = 50)
    private String id;

    @Column(nullable = false, length = 50)
    private String pw;

    @Column(nullable = false, length = 50)
    private String name;

    //나 to 상대
    @OneToOne(mappedBy = "user")
    private UserInfo userInfo;

    //나 to 상대 (User to Board)
    @OneToMany(mappedBy = "user")
    private List<Board> boards;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(this.id)
                .pw(this.pw)
                .name(this.name)
                .build();
    }
}
