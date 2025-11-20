package com.test.jpa.controller;

import com.test.jpa.entity.Item;
import com.test.jpa.model.ItemDTO;
import com.test.jpa.repository.ItemRepository;
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

    /**
     * 템플릿
     * @param model
     * @return result.html
     */
    @GetMapping("/n")
    public String n(Model model) {

        return "result";
    }
}
