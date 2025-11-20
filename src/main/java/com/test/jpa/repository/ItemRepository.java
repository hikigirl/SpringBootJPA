package com.test.jpa.repository;

import com.test.jpa.entity.Item;
import com.test.jpa.model.ItemDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//엔티티 -> CRUD 구현
// - 인터페이스명 -> 엔티티명 + Repository
// DB 테이블 개수와 동일하게 생성됨...
// JpaRepository를 상속받음(인터페이스-인터페이스 간 상속 필요)
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(String name);

    Optional<Item> findByNameIs(String name);

    List<Item> findByColor(String black);
//    Optional<Item> findByColor(String black);

    List<Item> findByColorIgnoreCase(String color);

    Optional<Item> findFirstByColor(String color);

    Optional<Item> findFirstByPrice(int i);

    List<Item> findFirst3ByColor(String color);

    List<Item> findTop5ByColor(String color);

    List<Item> findByNameAndColor(String name, String color);

    List<Item> findByNameOrColor(String name, String color);

    List<Item> findByPriceGreaterThan(Sort price, int i);

    List<Item> findByPriceGreaterThanEqual(Integer price);

    List<Item> findByPriceLessThanEqual(int i);

    List<Item> findByColorAndPriceGreaterThanEqual(String color, int i);

    List<Item> findByPriceBetween(int i, int i1);

    List<Item> findByQtyIsNull();

    List<Item> findByDescriptionIsNull();

    List<Item> findByQtyIsNullAndDescriptionIsNull();

    List<Item> findByDescriptionIsNotNullAndColorAndPriceGreaterThanEqual(String color, int i);

    List<Item> findByColorIn(List<String> colors);

    List<Item> findByColorNotIn(Collection<String> colors);

    List<Item> findByNameStartsWith(String name);

    List<Item> findByNameEndsWith(String name);

    List<Item> findByDescriptionContains(String description);

    List<Item> findByDescriptionNotContains(String description);

    List<Item> findAllByOrderByNameAsc();

    List<Item> findAllByOrderByNameDesc();

    List<Item> findByColorOrderByPriceAsc(String black);

    List<Item> findAllByOrderByColorAscPriceDesc();

    List<Item> findByPriceGreaterThanOrderByPriceAsc(Integer price);

    List<Item> findByPriceGreaterThanOrderByPriceDesc(Integer price);

    List<Item> findAllByOrderByPriceAsc();

    //JPQL
    //select * from tblItem
    //@Query("select i from Item as i")
    @Query(value = "select * from tblItem", nativeQuery = true)
    List<Item> n01();

    @Query("select i.name from Item as i")
    List<String> n02();

    //모든 컬럼에는 alias를 명시해야한다
//    @Query("select i from Item as i where i.color = ?1")
//    List<Item> n03(String color);
    @Query("select i from Item as i where i.color = :color")
    List<Item> n03(@Param(value="color") String color);

    @Query("select i from Item as i where i.color= :#{#dto.color} and i.price>=:#{#dto.price}")
    List<Item> n04(@Param(value="dto")ItemDTO dto);
}
