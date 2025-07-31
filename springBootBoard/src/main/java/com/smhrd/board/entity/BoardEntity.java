package com.smhrd.board.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

public class BoardEntity {
	
	@Id // pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increase
	private Long id;
	
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String writer;
	@Column(nullable = false, columnDefinition = "TEXT") // DB 컬럼을 text로 바꾼다는 코드
	private String content;
	
	private String imgpath;// 이미지 저장 데이터베이스에 이미지 저장하지 않고
	//  서버에 이미지 저장하고 서버경로를 데이터베이스에 저장
	
	//현재 날짜(글 작성 시간 일시)
	@Column(nullable = false) // updatable = false update 불가
	private LocalDate writeDay;
	
	//JPA 글 작성시 자동으로 오늘 날까 입력할 수 있게 하는 코드
	@PrePersist
	protected void onWirteDay() {
		this.writeDay = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public LocalDate getWriteDay() {
		return writeDay;
	}

	public void setWriteDay(LocalDate writeDay) {
		this.writeDay = writeDay;
	}

	public BoardEntity(Long id, String title, String writer, String content, String imgpath, LocalDate writeDay) {
		super();
		this.id = id;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.imgpath = imgpath;
		this.writeDay = writeDay;
	}

	
	public BoardEntity() {

	}
	
	
	
}
