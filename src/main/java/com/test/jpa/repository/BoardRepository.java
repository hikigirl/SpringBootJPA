package com.test.jpa.repository;

import com.test.jpa.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository<Board, Long>
//<엔티티명, 엔티티의 PK>
public interface BoardRepository extends JpaRepository<Board, Long> {
}
