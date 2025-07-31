package com.smhrd.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.board.config.FileUploadConfig;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.service.BoardService;
import com.smhrd.board.config.WebConfig;

import jakarta.servlet.http.HttpSession;

//Controller default mapping 주소설정
@Controller
@RequestMapping("/board")
public class BoardController {

	private final WebConfig webConfig;
	@Autowired
	BoardService boardserive;

	@Autowired
	FileUploadConfig fileUploadConfig;

	BoardController(WebConfig webConfig) {
		this.webConfig = webConfig;}

	// 게시글 작성 기능
	@PostMapping("/write")
	public String write(@RequestParam String title, @RequestParam String content, HttpSession session,
			@RequestParam MultipartFile image) {
		// 1. 필요한 것?? input 태그에서는 file 넘어노는 중 (file을 받아주여야 함니다)
		// title, writer, content, imgpath,
		// (session)

		// image 처리
		String imPath = "";
		// 이미지를 저장 할 경로 - C : upload/
		String uploadDir = fileUploadConfig.getUploadDir();
		if (!image.isEmpty()) {
			// 1. 파일의 이름 설정
			// UUID : 고유 식별자(중복을 막으려고)
			String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
			// 2. 파일 저장 될 이름과 경로 설정
			String filePath = Paths.get(uploadDir, fileName).toString();
			// 3. 서버에 저장 및 경로 설정
			try {
				image.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 4. DB에 저장될 경로 문자열 설정
			imPath = "/home/git/uploads/" + fileName;
		}
		BoardEntity board = new BoardEntity();
		board.setTitle(title);
		board.setContent(content);
		// writer
		UserEntity user = (UserEntity) session.getAttribute("user");
		board.setWriter(user.getId());
		board.setImgpath(imPath);
		boardserive.write(board);

		// 2. DB 연결 -> repository, service

		return "redirect:/";
	}

	/// 게시글 상세페이지 이동
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) { // URL에 담긴 값 가지고 오는 방법 @PathVariable
		System.out.println(id);
		// 1. 필요한것
		// --> id, 다음페이지로 값을 보내기 위한
		// 2. service 연결
		Optional<BoardEntity> detail = boardserive.detailPage(id);
		// detail --> optional타입 --> BoardEntity 타입
		// .get
		model.addAttribute("detail", detail.get());
		return "detail";
	}

	// 게시판 수정 기능 edit.html
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		// 1. 필요한거

		// 2. service 연결
		Optional<BoardEntity> board = boardserive.detailPage(id);

		model.addAttribute("board", board.get());
		return "edit";
	}

	// 게시판 수정 기능
	@PostMapping("/update")
	public String update(@RequestParam String title, @RequestParam String content, @RequestParam MultipartFile image,
			@RequestParam String oldImgPath, @RequestParam Long id) {
		// 1, title, content, imgpath, id, oldImgPath
		// db 접근해서 게시글의 정보를 다시 가지고 오겠습니다
		BoardEntity entity = boardserive.detailPage(id).get();
		// file업로드 경로
		String uploadDir = fileUploadConfig.getUploadDir();
		// 기존 이미지 처리
		if (!image.isEmpty()) { // 새롭게 이미지를 업로드
			// 기존에 있던 이미지 삭제
			if (oldImgPath != null && !oldImgPath.isEmpty()) {
				Path oldFilePath = Paths.get(uploadDir, oldImgPath.replace("/uploads/", ""));
				try {
					Files.deleteIfExists(oldFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 새로운 이미지 저장
			String newFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
			Path newFilePath = Paths.get(uploadDir, newFileName);

			try {
				image.transferTo(newFilePath);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			entity.setImgpath("/uploads/" + newFileName);
			//
		}
		// 작성자
		// BoardEntity entity = new BoardEntity();
		entity.setTitle(title);
		entity.setContent(content);

		// 업데이트문 실행 == service 에 연결
		// *
		// * findById() 해준후 save 코드를 실행하면
		// * jpa가 자동으로 업데이트라고 인식을 함
		// * 대규모로 업데이트 복잡한 update @Query("sql문 작성")

		boardserive.write(entity);

		return "redirect:/board/detail/" + id;

	}
}
