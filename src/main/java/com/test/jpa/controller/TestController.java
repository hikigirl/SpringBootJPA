package com.test.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {
    // TBLITEM의 CRUD 기능
    @GetMapping("/m1")
    public String m1(Model model) {
        
        return "m1";
    }
    @PostMapping("/m1ok")
    public String m1ok(Model model) {

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
