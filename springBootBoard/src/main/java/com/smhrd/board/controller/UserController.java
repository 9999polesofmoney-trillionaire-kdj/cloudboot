package com.smhrd.board.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.board.SpringBootBoardApplication;
import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.repository.UserRepository;
import com.smhrd.board.service.UserSerivce;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class UserController {

    private final SpringBootBoardApplication springBootBoardApplication;
	
	@Autowired
	private UserSerivce userService;

    UserController(SpringBootBoardApplication springBootBoardApplication) {
        this.springBootBoardApplication = springBootBoardApplication;
    }
	
	//화원가입 기능
	@PostMapping("/register.do")
	public String register(@RequestParam String pw, @RequestParam String id, 
			@RequestParam String name, @RequestParam int age) {
		/// 1. 필요한거 ???
		///// --> id, pw, age, name 
		/////2. DB 연결--> Repository, Entity생성 --> service
		//3. Service 연결
		UserEntity entity = new UserEntity();
		entity.setId(id);
		entity.setPw(pw);
		entity.setName(name);
		entity.setAge(age);
		
		String result = userService.register(entity);
		
		if(result.equals("sucess")){
			return "redirect;/login";
			
		}else {
			return "redirect;/register";
		}
		
	}
	@Autowired
	private UserRepository userRepository;
	//로그인 기능
	@PostMapping("login.do")
	public String login(@RequestParam String id, @RequestParam String pw, HttpSession session) {
		// 1. 필요한 거
		// id, pw
		// 2. db연결 --> sevice --> repository 연결 --> repository 적절한 메소드 생성(커스텀사용)  
		
		// 찌니 2번 service 연결
		UserEntity user = userService.login(id, pw);
		
		if(user != null) {//로그인 성공
			session.setAttribute("user", user);
			return "redirect:/";
		}else {
			return "redirect:/login";
		}
		// 로그인이 성공 하면 로그인 정보를 저장 후 index 페이지로 이동
		
	}
	//로그아웃 기능
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
}
