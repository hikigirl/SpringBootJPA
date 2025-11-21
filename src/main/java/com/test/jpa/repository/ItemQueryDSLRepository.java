package com.test.jpa.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.jpa.entity.Item;
import com.test.jpa.entity.QItem;
import com.test.jpa.model.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.test.jpa.entity.QItem.item;

//DAO or Service 역할
@Repository
@RequiredArgsConstructor
public class ItemQueryDSLRepository {
    private final JPAQueryFactory factory; //mapper 역할

    public List<Item> n05() {
        //QClass의 메서드를 사용한 쿼리 작성

        //select * from 테이블
        // - 매개변수에는 순수 엔티티가 아닌 Q클래스를 적는다.
        List<Item> list = factory
                    .selectFrom(item) //SQL(JPQL) 생성 + ResultSet 생성
                    .fetch();        //selectList + Mapping

        return list;
    }

    public Item m30(String name) {
        //JPQL에서 테이블에 접근할 때에는 엔티티, 컬럼에 접근할때는 엔티티.필드
        //select name from tblItem = select i.name from Item as i

        return factory
                .selectFrom(item)
                .where(item.name.eq(name))
                .fetchOne();
    }

    public List<String> n07() {
        //모든 컬럼: selectFrom()
        //특정 컬럼: select()

        return factory
                .select(item.name)
                .from(item)
                .fetch();
    }

    public List<Tuple> n08() {
        //특정 컬럼 중 여러개 조회 - Tuple
        return factory
                .select(item.name, item.color, item.price) //콤마 찍고 컬럼 여러개 명시
                .from(item)
                .fetch();
    }

    public List<ItemDTO> n09() {
        //특정 컬럼 중 여러개 조회 - Tuple
        //DTO의 생성자 필요
        return  factory
                .select(Projections.constructor(ItemDTO.class, item.name, item.color, item.price))
                .from(item)
                .fetch();
    }

    public List<Item> n10() {
        /*return  factory
                .selectFrom(item)
                //.where(item.color.ne("black"))
                //.where(item.description.isNull())
                //.where(item.description.isNotNull())
                //.where(item.price.gt(300000))
                //.where(item.price.lt(300000))
                //.where(item.price.goe(300000))
                //.where(item.price.loe(300000))
                //.where(item.price.between(300000, 500000))
                //.where(item.color.in("red", "yellow"))
                //.where(item.color.notIn("red", "yellow"))
                //.where(item.description.startsWith("최신"))
                //.where(item.description.endsWith("입니다."))
                //.where(item.description.contains("최신"))
                //.where(item.description.like("%최신%"))
                .fetch();
         */
        return  factory
                .selectFrom(item)
                .where(item.color.eq("black").and(item.description.isNotNull()))
                .fetch();
    }

    public List<Item> n11() {
        //정렬
        return factory
                .selectFrom(item)
                //.orderBy(item.color.asc())
                //.orderBy(item.color.desc())
                //.orderBy(item.color.asc(), item.price.desc(), item.qty.asc())
                //.orderBy(item.qty.asc().nullsFirst())
                //.orderBy(item.qty.desc().nullsLast())
                .fetch();
    }

    public List<Item> n12(int offset, int limit) {
        //페이징
        return factory
                .selectFrom(item)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public Object n13() {
        //return factory.selectFrom(item).fetchCount();
        return factory
//                .select(item.count())
//                .select(item.qty.count())
//                .select(item.qty.sum())
//                .select(item.qty.avg())
//                .select(item.qty.min())
                .select(item.qty.max())
                .from(item)
                .fetchOne();
    }

    public Tuple n13_1() {
        return factory
                .select(item.count(), item.price.max(), item.price.min(), item.price.sum(), item.price.avg())
                .from(item)
                .fetchOne();
    }

    public List<Tuple> n14() {
        //GROUP BY, HAVING
        return factory
                .select(item.color,item.count(), item.price.avg())
                .from(item)
                .groupBy(item.color)
                .having(item.count().goe(5))
                .fetch();
    }

    public List<Item> n17() {
        //서브쿼리
        //select * from tblItem where price>=(select avg(price) from tblItem)
        QItem item2 = item; //서브쿼리 from절에 사용할 엔티티=> item2
        return factory
                .selectFrom(item) //select * from tblItem
                .where(item.price.goe(
                    JPAExpressions.select(item2.price.avg()).from(item2) //서브쿼리
                )) //where price>=(서브쿼리결과)
                .fetch();
    }

    public List<Tuple> n18() {
        //서브쿼리
        // select name, price, (select avg(price) from tblItem) from tblItem
        QItem item2 = item; //서브쿼리 from절에 사용할 엔티티=> item2
        return factory
                .select(item.name, item.price, (
                    JPAExpressions.select(item2.price.avg()).from(item2) //서브쿼리
                ))
                .from(item)
                .fetch();
    }

    public List<Item> n19(ItemDTO dto) {
        //동적 쿼리
        //n19 -> select * from tblItem
        //n19?color=black               -> select * from tblItem where c
        //n19?price=100000              -> select * from tblItem where price >= 100000
        //n19?color=black&price=100000  -> select * from tblItem where color=black and price >= 100000

        //조건절 생성기
        BooleanBuilder builder = new BooleanBuilder();
        if(dto.getColor() != null) {
            builder.and(item.color.eq(dto.getColor()));
        }
        if(dto.getPrice() != null) {
            builder.and(item.price.goe(dto.getPrice()));
        }

        return factory
                .selectFrom(item)
                .where(builder)
                .fetch();
    }
}
