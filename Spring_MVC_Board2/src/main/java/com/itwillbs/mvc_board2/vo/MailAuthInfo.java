package com.itwillbs.mvc_board2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * [ 회원 이메일 인증 정보를 관리할 mail_auth_info 테이블 정의 ]
 * --------------------------------------------------------------
 * 이메일(email) - 50자, PK
 * 인증코드(auth_code) - 50자, UN, NN
 * --------------------------------------------------------------
   CREATE TABLE mail_auth_info (
   		email VARCHAR(50) PRIMARY KEY,
   		auth_code VARCHAR(50) UNIQUE NOT NULL
   );
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailAuthInfo {
	private String email;
	private String auth_code;
}
