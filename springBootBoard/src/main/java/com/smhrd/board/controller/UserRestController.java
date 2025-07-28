package com.smhrd.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.board.service.UserSerivce;

@RestController
public class UserRestController {
		//RestController 란?
		//Controller 역활을 하지만 HTML이 아닌 데이터(Json)을 보내주는 컨트롤러
	
	@Autowired
	UserSerivce userService;
	
	@GetMapping("/user/check-id")
	public HashMap<String, Boolean> checkId(@RequestParam String id) {
		//1. 필요한 거 --> id
		// 2. DB 연결 -> sercive -> serive 연걸 
		boolean exist = userService.check(id);
		
		//Map 구조로 만들어서 Json화 시켜 데이터 전송
		// Map ===python dict K:V
		// <k의 자료형, V의 자료형>
		HashMap<String, Boolean> res = new HashMap<>();
		//map 에 데이터 넣는 법
		//arraylist --> .add()
		//map -> put
		res.put("exist", exist);
		return res;
		//중복체크 버튼을 누르면 false가 노오고 Exist를 누르면 true나 나온다
	}
}
