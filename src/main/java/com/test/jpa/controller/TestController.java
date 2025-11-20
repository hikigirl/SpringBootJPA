package com.test.jpa.controller;

import com.test.jpa.entity.Item;
import com.test.jpa.model.ItemDTO;
import com.test.jpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TestController {
    //주입
    private final ItemRepository itemRepository;

    // TBLITEM의 CRUD 기능
    @GetMapping("/m1")
    public String m1(Model model) {
        return "m1";
    }
    @PostMapping("/m1ok")
    public String m1ok(Model model, ItemDTO dto) {
        // INSERT, 레코드 추가하기
        // - 추가할 정보를 사용해서 엔티티 객체를 생성(***)
        // - ItemDTO -> 매핑, 변환 -> Item(엔티티)

        //기존 방식
        //Item item = new Item(dto.getSeq(), dto.getName(), dto.getPrice(),dto.getColor(), dto.getQty(), dto.getDescription());
        
        //Builder 패턴 사용
        Item item = Item.builder()
                        .seq(dto.getSeq())
                        .name(dto.getName())
                        .price(dto.getPrice())
                        .color(dto.getColor())
                        .qty(dto.getQty())
                        .description(dto.getDescription())
                        .build();

        itemRepository.save(item);

        return "result";
    }
    
    @GetMapping("/m2")
    public String m2(Model model, @RequestParam("seq") Long seq) {
        //SELECT
        // 1개의 레코드 가져오기
        // itemRepository.findById(PK)
        // itemRepository.getById(PK) (오래된거)
        // itemRepository.getOne(PK) (오래된거)
        //pk로 검색 -> 레코드 1개 = 엔티티 1개
        Optional<Item> item = itemRepository.findById(seq); //PK로 검색한다(PK가 long이여서 1L로 검색)
        System.out.println(item);
        /* System.out.println(item); 결과
            select
                i1_0.seq,
                i1_0.color,
                i1_0.description,
                i1_0.name,
                i1_0.price,
                i1_0.qty
            from
                tblItem i1_0
            where
                i1_0.seq=?
        */
//        if(item.isPresent()) {
//            model.addAttribute("dto", item.get().toDTO());
//        }

        item.ifPresent(entity -> model.addAttribute("dto", entity.toDTO()));

        return "result";
    }
    @GetMapping("/m3")
    public String m3(Model model, @RequestParam("seq") Long seq) {
        //m3?seq=10
        Optional<Item> item = itemRepository.findById(seq);
        item.ifPresent(value -> model.addAttribute("dto", value.toDTO()));
        return "m3";
    }
    @PostMapping("/m3ok")
    public String m3ok(Model model, ItemDTO dto) {
        /*Item item = Item.builder()
                .seq(3L)
                .name("태블릿 업데이트")
                .price(350000)
                .color("white")
                .qty(100)
                .description("업데이트 테스트입니다.")
                .build();*/

        //Item item = dto.toEntity();
        Optional<Item> result = itemRepository.findById(dto.getSeq());
        Item result2 = result.get();
        result2.update(dto.getName(), dto.getPrice(), dto.getColor(), dto.getQty(), dto.getDescription());

        itemRepository.save(result2);
        return "result";
    }

    /**
     * DELETE
     * @param model
     * @param seq
     * @return
     */
    @GetMapping("/m4")
    public String m4(Model model, @RequestParam("seq") Long seq) {
        //m3?seq=10
        //a. 엔티티 직접 생성 후 삭제
        //b. 엔티티 검색 반환 후 삭제
//        Item item = Item.builder()
//                .seq(seq)
//                .build();

        Optional<Item> item = itemRepository.findById(seq);
        if(item.isPresent()){
            itemRepository.delete(item.get());
        }
        return "result";
    }

    /**
     * Query Method 사용
     * @param model
     * @return
     */
    @GetMapping("/m5")
    public String m5(Model model) {
//        Optional<Item> item = itemRepository.findById(1L);
        //findBy + 컬럼명
        Optional<Item> item = itemRepository.findByName("노트북");
        //Item item = itemRepository.findByName("노트북");
        model.addAttribute("dto", item.get().toDTO());
        return "result";
    }

    @GetMapping("/m6")
    public String m6(Model model) {
        Long count = itemRepository.count();
        Boolean exist = itemRepository.existsById(1L);
        model.addAttribute("count", count);
        model.addAttribute("exist", exist);
        return "result";
    }
    @GetMapping("/m7")
    public String m7(Model model) {

        //전체 레코드 가져오기
        List<Item> list = itemRepository.findAll();

        //List<엔티티> >> List<DTO>
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());

        model.addAttribute("dtoList", dtoList);

        return "result";
    }
    @GetMapping("/m8")
    public String m8(Model model) {
        //Is, Equals
        //Optional<Item> item = itemRepository.findByName("노트북");
        //Optional<Item> item = itemRepository.findByNameIs("태블릿");
        //model.addAttribute("dto", item.get().toDTO());

        //List<Item> list = itemRepository.findByColor("white");
        List<Item> list = itemRepository.findByColorIgnoreCase("Yellow");
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        //Query did not return a unique result: 4 results were returned
//        Optional<Item> item = itemRepository.findByColor("yellow");
//        model.addAttribute("dto", item.get().toDTO());

        return "result";
    }

    @GetMapping("/m9")
    public String m9(Model model) {
        //first, top: 가져올 레코드의 개수를 지정한다. 결과셋 맨 위에서부터 n개
        // select * from table where rownum <= 3; (ORACLE)
        // select * from table limit 0, 3; (MYSQL)
        // select top 3 * from table; (MSSQL)

        //Optional<Item> item = itemRepository.findFirstByColor("white");
        //Optional<Item> item = itemRepository.findFirstByPrice(55000);
        //model.addAttribute("dto", item.get().toDTO());

        //List<Item> list = itemRepository.findFirst3ByColor("white");
        List<Item> list = itemRepository.findTop5ByColor("white");
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/m10")
    public String m10(Model model) {
        //And, Or
//        List<Item> list = itemRepository.findByNameAndColor("노트북", "black");
//        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
//        model.addAttribute("dtoList", dtoList);
        List<Item> list = itemRepository.findByNameOrColor("노트북", "black");
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }
    @GetMapping("/m11")
    public String m11(Model model) {
        // 비교 연산 키워드
        // After, Before : 날짜시간 비교용
        // GreaterThan(GreaterThanEqual), LessThan(LessThanEqual): 숫자 비교용
        // Between : 날짜, 숫자 혼용
        
        //After, GreaterThan(GreaterThanEqual) : 크다
        //Before, LessThan(LessThanEqual): 작다

        //List<Item> list = itemRepository.findByPriceGreaterThan(500000);
        //List<Item> list = itemRepository.findByPriceGreaterThanEqual(550000);
        //List<Item> list = itemRepository.findByPriceLessThanEqual(550000);
        //List<Item> list = itemRepository.findByColorAndPriceGreaterThanEqual("white",500000);
        List<Item> list = itemRepository.findByPriceBetween(300000, 500000); //30만 <= price <= 50만

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        return "result";
    }

    @GetMapping("/m12")
    public String m12(Model model) {
        //isEmpty, isNull
        //isNotEmpty, isNotNull
        // - isNull -> null 체크
        // - isEmpty -> 빈문자열, 집합(size:0) 등을 체크
        
        //qty, description 컬럼에 null값이 존재함

        //List<Item> list = itemRepository.findByQtyIsNull();
        //List<Item> list = itemRepository.findByDescriptionIsNull();
        //List<Item> list = itemRepository.findByQtyIsNullAndDescriptionIsNull();

        List<Item> list = itemRepository.findByDescriptionIsNotNullAndColorAndPriceGreaterThanEqual("white", 300000);

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        return "result";
    }

    @GetMapping("/m13")
    public String m13(Model model) {
        //In, NotIn
        // 열거형 조건
        List<String> colors = List.of("white", "black"); //수정 불가능, 읽기 전용(ArrayList)

        //List<Item> list = itemRepository.findByColorIn(colors);
        List<Item> list = itemRepository.findByColorNotIn(colors);

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        return "result";
    }

    @GetMapping("/m14")
    public String m14(Model model) {
        /*
         * StartsWith, StartingWith
         * EndsWith, EndingWith
         * Contains
         * Like : 사용자 정의형
        */

        //List<Item> list = itemRepository.findByNameStartsWith("스마트");
        //List<Item> list = itemRepository.findByNameEndsWith("폰");
        //List<Item> list = itemRepository.findByDescriptionContains("기능");
        List<Item> list = itemRepository.findByDescriptionNotContains("기능");

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        return "result";
    }

    @GetMapping("/m15")
    public String m15(Model model) {
/*
        정렬
        OrderBy컬럼명Asc
        OrderBy컬럼명Desc

        다중 정렬
        OrderBy컬럼명Asc컬럼명Desc
*/
        //List<Item> list = itemRepository.findAll();
        //List<Item> list = itemRepository.findAllByOrderByNameAsc();
        //List<Item> list = itemRepository.findAllByOrderByNameDesc();
        //List<Item> list = itemRepository.findByColorOrderByPriceAsc("black");
        List<Item> list = itemRepository.findAllByOrderByColorAscPriceDesc();

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/m15_1")
    public String m15_1(Model model, @RequestParam("price") Integer price, @RequestParam("order") String orderString) {

        // m15_1?price=300000
        // m15_1?price=300000&order=asc
        // m15_1?price=300000&order=desc

        //List<Item> list = itemRepository.findByPriceGreaterThan(price);
        //List<Item> list = itemRepository.findByPriceGreaterThanOrderByPriceAsc(price);
        //List<Item> list = itemRepository.findByPriceGreaterThanOrderByPriceDesc(price);

        //List<Item> list = itemRepository.findByPriceGreaterThan(Sort.by("price"), price);
        Sort.Direction order = Sort.Direction.ASC;
        if (orderString.equals("desc")) {
            order = Sort.Direction.DESC;
        }

        List<Item> list = itemRepository.findByPriceGreaterThan(Sort.by(order, "price"), price);

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }
    
    @GetMapping("/m16")
    public String m16(Model model) {
        //정렬 - 다른 방식

        //List<Item> list = itemRepository.findAllByOrderByPriceAsc();
        //List<Item> list = itemRepository.findAll(Sort.by("price")); //asc

        List<Item> list = itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price")); //desc

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        return "result";
    }

    @GetMapping("/m17")
    public String m17(Model model) {

        //List<Item> list = itemRepository.findAll(Sort.by("color", "price"));
        List<Item> list = itemRepository.findAll(Sort.by(
                Sort.Order.asc("color"),
                Sort.Order.desc("price")
        ));

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/m18")
    public String m18(Model model) {
        //페이징
        PageRequest pageRequest = PageRequest.of(0, 10);
        //List 대신 Page
        Page<Item> list = itemRepository.findAll(pageRequest);

        //Page 배열 - 페이징과 관련된 여러 정보를 제공
        System.out.println(list.getNumber());
        System.out.println(list.getNumberOfElements());
        System.out.println(list.getTotalElements());
        System.out.println(list.getTotalPages());
        System.out.println(list.getSize());

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/m19_1")
    public String m19_1(Model model,
                      @RequestParam(name="page", required = false, defaultValue = "1") Integer page) {
        page--;
        PageRequest pageRequest = PageRequest.of(page, 10);

        Page<Item> list = itemRepository.findAll(pageRequest);

        //페이지 바
        String temp = "";
        for (int i=1; i<=list.getTotalPages(); i++) {
            temp += """
                    <a href="/m19_1?page=%d">%d</a>
                    """.formatted(i, i);
        }

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());

        model.addAttribute("temp", temp);
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/m19")
    public String m19(Model model, @PageableDefault(size = 10) Pageable pageable) {
        // 페이징 (다른 스타일)
        // 이전/다음페이지
        
        //PageRequest : 직접 생성
        //Pageable : 매개변수(page, size, sort)

        //http://localhost:8080/m19
        //http://localhost:8080/m19?page=1
        //http://localhost:8080/m19?page=2
        //http://localhost:8080/m19?page=3

        Page<Item> list = itemRepository.findAll(pageable);
        System.out.println("page 관련 로그");
        System.out.println(list.getNumber());
        System.out.println(list.getTotalPages());
        System.out.println(list.hasContent());
        System.out.println(list.hasNext());
        System.out.println(list.hasPrevious());
        System.out.println(list.nextOrLastPageable());
        System.out.println(list.nextPageable());
        System.out.println(list.previousOrFirstPageable());
        System.out.println(list.previousPageable());
        System.out.println(list.isFirst());
        System.out.println(list.isLast());

        String temp = "";
        temp += """
                <a href="/m19?page=%d">이전 페이지</a>
                """.formatted(list.previousOrFirstPageable().getPageNumber());
        temp += """
                <a href="/m19?page=%d">다음 페이지</a>
                """.formatted(list.nextOrLastPageable().getPageNumber());
        model.addAttribute("temp", temp);

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        return "result";
    }
    /**
     * 템플릿
     * @param model
     * @return
     */
    @GetMapping("/m")
    public String m(Model model) {
        return "result";
    }
}
