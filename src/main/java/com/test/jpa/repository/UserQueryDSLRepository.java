package com.test.jpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.jpa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.test.jpa.entity.QBoard.board;
import static com.test.jpa.entity.QUser.user;
import static com.test.jpa.entity.QUserInfo.userInfo;

@Repository
@RequiredArgsConstructor
public class UserQueryDSLRepository {
    //주입
    private final JPAQueryFactory factory; //mapper 역할

    public User n15() {
        //조인
        return factory
                .selectFrom(user)
                .join(user.userInfo, userInfo)
                .where(user.id.eq("hong"))
                .fetchOne();
    }

    public List<User> n16() {
        return factory
                .selectFrom(user)
                //.join(user.boards, board)
                .leftJoin(user.boards, board)
                .leftJoin(user.userInfo, userInfo)
                .fetch();
    }
}
