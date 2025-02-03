package com.itwillbs.mvc_board2.vo;
import java.sql.Date;

import lombok.Data;

/*
[ spring_mvc_board.member 테이블 정의 ]
------------------------------------------
CREATE DATABASE spring_mvc_board;
------------------------------------------
CREATE TABLE member (
	idx INT PRIMARY KEY AUTO_INCREMENT,
 	name VARCHAR(10) NOT NULL,
 	id VARCHAR(16) NOT NULL UNIQUE,
 	passwd VARCHAR(100) NOT NULL,
 	post_code VARCHAR(10) NOT NULL,
 	address1 VARCHAR(100) NOT NULL,
 	address2 VARCHAR(100) NOT NULL,
 	email VARCHAR(50) NOT NULL UNIQUE,
 	job VARCHAR(10) NOT NULL,
 	gender VARCHAR(1) NOT NULL,
 	hobby VARCHAR(50) NOT NULL,
 	motivation VARCHAR(500) NOT NULL,
 	reg_date DATETIME NOT NULL,
 	withdraw_date DATETIME,
	member_status INT NOT NULL,
	mail_auth_status CHAR(1) NOT NULL
);
*/

@Data
public class MemberVO {
	private int idx;
	private String name;
	private String id;
	private String passwd;
	private String post_code;
	private String address1;
	private String address2;
	// -------------------------------------------
	// 회원가입 폼에서 입력받은 이메일 주소는 email1, 2 라는 파라미터명을 사용하므로
	// MemberVO 객체에 email1, 2 라는 멤버변수가 필요하며
	// INSERT 과정에서 email1과 email2 값을 결합 후 전달도 가능하다!
	// 단, 뷰페이지(JSP)에서 자바스크립트를 활용하여 email1, email2 파라미터를 결합하여
	// 폼 파라미터로 전송할 경우 email1, 2 멤버변수는 불필요
	// -------------------------------------------
	private String email;
	private String email1;
	private String email2;
	private String job;
	private String gender;
	private String hobby;
	private String motivation;
	private Date reg_date; // 가입일자
	private Date withdraw_date; // 탈퇴일자
	private int member_status; // 회원상태(1: 정상, 2: 휴면, 3: 탈퇴)
	private String mail_auth_status; // 이메일 인증상태 (Y/N)
}
