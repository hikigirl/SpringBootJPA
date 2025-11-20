package com.test.jpa.controller;

import com.test.jpa.entity.Board;
import com.test.jpa.entity.User;
import com.test.jpa.entity.UserInfo;
import com.test.jpa.model.BoardDTO;
import com.test.jpa.repository.BoardRepository;
import com.test.jpa.repository.UserInfoRepository;
import com.test.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class Test2Controller {
    //주입
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/ex01")
    public String ex01(Model model) {
        //1:1 관계
        //tblUser:tblUserInfo
        //User:UserInfo

        //JPA에 설정해둔것 때문에 자식 테이블의 정보도 같이 가져온다
        //부모를 가져올 때 자식의 정보도 가져오기
        /*
            select
                u1_0.id,
                u1_0.name,
                u1_0.pw,
                ui1_0.id,
                ui1_0.address,
                ui1_0.age,
                ui1_0.gender
            from
                tblUser u1_0
            left join
                tblUserInfo ui1_0
                    on u1_0.id=ui1_0.id
            where
                u1_0.id=?
        */
        Optional<User> user = userRepository.findById("hong");
        model.addAttribute("user", user.get().toDTO());
        model.addAttribute("userInfo", user.get().getUserInfo().toDTO());

        return "result";
    }
    @GetMapping("/ex02")
    public String ex02(Model model) {
        //1:1 관계
        //tblUser:tblUserInfo
        //User:UserInfo
        
        //자식을 가져올 때 부모의 정보도 가져오기
        /*
        select
            ui1_0.id,
            ui1_0.address,
            ui1_0.age,
            ui1_0.gender,
            u1_0.id,
            u1_0.name,
            u1_0.pw
        from
            tblUserInfo ui1_0
        left join
            tblUser u1_0
                on u1_0.id=ui1_0.id
        where
            ui1_0.id=?
        */
        Optional<UserInfo> userInfo = userInfoRepository.findById("hong");
        model.addAttribute("userInfo", userInfo.get().toDTO());
        model.addAttribute("user", userInfo.get().getUser().toDTO());

        return "result";
    }

    @GetMapping("/ex03")
    public String ex03(Model model) {
        //1:N 관계
        // - tblUser : tblBoard
        // - User : Board

        //Optional<User> user = userRepository.findById("hong");
        //model.addAttribute("user", user.get().toDTO());
        //model.addAttribute("boards", user.get().getBoards()); //실제로 작업할 땐 DTO로 꼭 바꿔서 넘기기

        List<Board> list = boardRepository.findAll();
        List<BoardDTO> blist = list.stream().map(item -> item.toDTO()).collect(Collectors.toList());
        model.addAttribute("blist", blist);
        return "result";
    }

    /**
     * 템플릿
     * @param model
     * @return result.html
     */
    @GetMapping("/ex")
    public String ex(Model model) {

        return "result";
    }
}
