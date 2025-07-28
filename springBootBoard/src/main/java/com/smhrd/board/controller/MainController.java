package com.smhrd.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.service.BoardService;

@Controller
public class MainController {

    @Autowired
    private BoardService boardService;

    //  메인 페이지
    @GetMapping("/")
    public String index(Model model) {
        // 게시글 전체 리스트 조회
        ArrayList<BoardEntity> list = boardService.selectAll();

        // 조회된 리스트를 모델에 담기
        model.addAttribute("boardList", list);

        // index.html 렌더링
        return "index";
    }

    //  로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //  회원가입 페이지
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    //  글쓰기 페이지
    @GetMapping("/write")
    public String write() {
        return "write";
    }
}
