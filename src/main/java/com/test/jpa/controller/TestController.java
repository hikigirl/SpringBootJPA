package com.test.jpa.controller;

import com.test.jpa.entity.Item;
import com.test.jpa.model.ItemDTO;
import com.test.jpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
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
        Optional<Item> item = itemRepository.findByNameIs("태블릿");
        model.addAttribute("dto", item.get().toDTO());
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
