package com.test.jpa.repository;

import com.test.jpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//엔티티 -> CRUD 구현
// - 인터페이스명 -> 엔티티명 + Repository
// DB 테이블 개수와 동일하게 생성됨...
// JpaRepository를 상속받음(인터페이스-인터페이스 간 상속 필요)
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(String name);

    Optional<Item> findByNameIs(String name);
}
