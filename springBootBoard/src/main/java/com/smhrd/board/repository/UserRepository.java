package com.smhrd.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.board.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	//1. annotaion 달아주기
	// 2. entity 구성
	// 3. JpaRepository 인터페이스 상속
	// T : entity
	//id : entity의 pk값의 자료형 

	//exist() -> 데이터의 존재 여부 판단 -> True /False
	//커스텈 응용 existBy 컬럼명
	
	boolean existsById(String id);

	// 로그인 기능
	UserEntity findAllByIdAndPw(String id, String pw);
}