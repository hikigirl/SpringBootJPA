package com.test.jpa.controller;

import com.querydsl.core.Tuple;
import com.test.jpa.entity.Item;
import com.test.jpa.entity.User;
import com.test.jpa.model.ItemDTO;
import com.test.jpa.repository.ItemQueryDSLRepository;
import com.test.jpa.repository.ItemRepository;
import com.test.jpa.repository.UserQueryDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class Test3Controller {
    //주입
    private final ItemRepository itemRepository;
    private final ItemQueryDSLRepository itemQueryDSLRepository;
    private final UserQueryDSLRepository userQueryDSLRepository;

    @GetMapping("/n01")
    public String n01(Model model) {

        List<Item> list = itemRepository.n01();

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/n02")
    public String n02(Model model) {
        List<String> names = itemRepository.n02();
        model.addAttribute("names", names);
        return "result";
    }
    @GetMapping("/n03")
    public String n03(Model model, @RequestParam(name="color", defaultValue = "black") String color) {
        List<Item> list = itemRepository.n03(color);

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }
    @GetMapping("/n04")
    public String n04(Model model, ItemDTO dto) {
        //http://localhost:8080/n04?color=white&price=300000
        List<Item> list = itemRepository.n04(dto);

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/n05")
    public String n05(Model model) {
        //전체 리스트 조회
        List<Item> list = itemQueryDSLRepository.n05();

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/n06")
    public String n06(Model model, @RequestParam(name = "name", defaultValue = "노트북") String name) {
        //레코드 1개 반환: fetchOne()
        //조건절: where() 메서드
        Item item = itemQueryDSLRepository.m30(name);
        model.addAttribute("dto", item.toDTO());
        return "result";
    }

    @GetMapping("/n07")
    public String n07(Model model) {
        //특정 컬럼 하나 조회
        List<String> names = itemQueryDSLRepository.n07();
        model.addAttribute("names", names);
        return "result";
    }
    @GetMapping("/n08")
    public String n08(Model model) {
        //일부 컬럼 조회 -> 엔티티 사용 X
        //1. Tuple
        List<Tuple> tuplelist = itemQueryDSLRepository.n08();
        model.addAttribute("tuplelist", tuplelist);
        return "result";
    }
    @GetMapping("/n09")
    public String n09(Model model) {
        //일부 컬럼 조회 -> 엔티티 사용 X
        //2. DTO
        List<ItemDTO> list = itemQueryDSLRepository.n09();
        model.addAttribute("dtoList", list);
        return "result";
    }

    @GetMapping("/n10")
    public String n10(Model model) {
        //where()
        List<Item> list = itemQueryDSLRepository.n10();
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/n11")
    public String n11(Model model) {
        //정렬
        List<Item> list = itemQueryDSLRepository.n11();
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/n12")
    public String n12(Model model, @RequestParam(name="page", defaultValue = "1") Integer page) {
        //페이징
        //offset: 가져올 레코드의 시작 위치(변함)
        //limit: 가져올 레코드 개수(고정, pageSize)
        //offset : 0, limit: 10
        int offset = (page - 1) * 10;
        int limit = 10;
        List<Item> list = itemQueryDSLRepository.n12(offset, limit);
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }

    @GetMapping("/n13")
    public String n13(Model model) {
        //집계함수
//        Object num = itemQueryDSLRepository.n13();
//        model.addAttribute("num", num);
        Tuple tuple = itemQueryDSLRepository.n13_1();
        model.addAttribute("tuple", tuple);
        return "result";
    }

    @GetMapping("/n14")
    public String n14(Model model) {
        //Group By + 집계함수(튜플로)
        List<Tuple> glist = itemQueryDSLRepository.n14();
        model.addAttribute("glist", glist);
        return "result";
    }

    @GetMapping("/n15")
    public String n15(Model model) {
        //조인 (1:1) tblUser - userinfo
        User user = userQueryDSLRepository.n15();
        model.addAttribute("user", user.toDTO());
        model.addAttribute("userInfo", user.getUserInfo().toDTO());

        return "result";
    }
    @GetMapping("/n16")
    public String n16(Model model) {
        //조인 (1:N)
        List<User> ulist = userQueryDSLRepository.n16();
        model.addAttribute("ulist", ulist);
        return "result";
    }

    @GetMapping("/n17")
    public String n17(Model model) {
        //서브쿼리(where절, select절)
        //select * from tblItem where price>=(select avg(price) from tblItem)
        List<Item> list = itemQueryDSLRepository.n17();

        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);

        return "result";
    }

    @GetMapping("/n18")
    public String n18(Model model) {
        List<Tuple> dlist = itemQueryDSLRepository.n18();
        model.addAttribute("dlist", dlist);
        return "result";
    }

    @GetMapping("/n19")
    public String n19(Model model, ItemDTO dto) {
        //동적 쿼리
        //n19 -> select * from tblItem
        //n19?color=black               -> select * from tblItem where c
        //n19?price=100000              -> select * from tblItem where price >= 100000
        //n19?color=black&price=100000  -> select * from tblItem where color=black and price >= 100000

        List<Item> list = itemQueryDSLRepository.n19(dto);
        List<ItemDTO> dtoList = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("dtoList", dtoList);
        return "result";
    }
}