package com.smhrd.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data // getter/ setter
@Table(name = "user") // 이미 만들어 높은 DB 사용할 수 없나요, DB이름을 다르게 지정하고 싶어요 

public class UserEntity {
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	//pk 값이 필수
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increase
	
	private Long idx; // 객체타입으로 삽입 --> 

	@Column(nullable = false, unique = true)
	private String id;

	private String pw;
	private String name;
	private Integer age;
	public void setAddress(String address) {
		
	}
	public void setEmail(String email) {
		
	}
	public void setTel(String tel) {
		
	}
}