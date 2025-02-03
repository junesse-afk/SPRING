package com.itwillbs.mvc_board2.handler;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.stereotype.Component;

// 자바 메일 기능 사용 시 메일서버(ex. 네이버, Gmail 등)
// 인증을 위한 정보를 관리하는 클래스 정의
@Component
public class MailAuth extends Authenticator {

	PasswordAuthentication passwordAuthentication;
	public MailAuth() {}
	
	/*
	 * 인증 정보 관리 객체(PasswordAuthentication)를 외부로 리턴하는 get() 메서드 정의
	 * => 주의! Getter 메서드를 직접 정의 시 멤버변수명에 따라 메서드명이 달라지는데
	 *    외부에서 getPasswordAuthentication() 메서드를 직접 호출하는 것이 아니라
	 *    객체 내에서 자동으로 호출되므로 미리 약속된 메서드명으로 정의 필수!
	 * => 슈퍼클래스인 Authenticator 클래스의 getPasswordAuthentication 메서드 오버라이딩! 
	 * */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		/*
		 * 네이버, Gmail 기준 2단계 인증 미사용 시: 계정명, 패스워드
		 * 네이버, Gmail 기준 2단계 인증 사용 시: 계정명, 앱 비밀번호
		 * 	(단, 사용 메일 계정에 대한 2단계 인증 활성화 및 앱 비밀번호 등록 필요)
		 *  (앱 비밀번호는 로그인 등의 다른 서비스에서는 사용불가능하며, 특정 서비스에서만 사용)
		 * */
		passwordAuthentication = new PasswordAuthentication("dwcha6005@gmail.com", "vkjwpkaikezsiuju");
		return passwordAuthentication;
	}
}
