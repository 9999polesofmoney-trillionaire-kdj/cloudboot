package com.smhrd.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.repository.BoardRepository;

import jakarta.persistence.Entity;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;

	// 게시글 작성 가능
	public BoardEntity write(BoardEntity entity) {
		return boardRepository.save(entity);
	}

	// 게시글 전부 가지고오기
	public ArrayList<BoardEntity> selectAll() {
		// 게시글이 오래된 순서대로 출력
		// 게시글이 최신순으로 출력 하게 만들고 싶습니다!
		// sql문 적으로 풀어보면 order by write day desc
		return (ArrayList<BoardEntity>) boardRepository.findAllByOrderByWriteDayDesc();
	}

	// 게시글 상세보기 기능
	public Optional<BoardEntity> detailPage(Long id) {
		// repository 연결 후 실행
		// 내장되어 있는 메소드 사용할때는 repository 코드 수정
		// sql -select *from ~~~ where id =?

		// optional 객체
		// findById 의 return 타입이 Opttional
		//
		// null 체크하는 객체
		// BoardEntity가 null일 수도 있음
		// 장점
		// np 에러 (null pointer exception)
		return boardRepository.findById(id);
	}
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}
 	// 검색기능
	public List<BoardEntity> searchResult(String type, String keyword) {
		List<BoardEntity> list = null;
		// 조건 처리
		switch (type) {
		case "title":
			list = 	boardRepository.findByTitleContaining(keyword);
			break;

		case "content":
			list =	boardRepository.searchContent(keyword);
			break;

		case "writer":

			break;
			
			default:
				break;
		}
		return list;
	}
	

}
