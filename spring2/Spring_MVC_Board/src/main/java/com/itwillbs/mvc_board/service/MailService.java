package com.itwillbs.mvc_board.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.mvc_board.handler.GenerateRandomCode;
import com.itwillbs.mvc_board.handler.MailClient;
import com.itwillbs.mvc_board.vo.MailAuthInfo;
import com.itwillbs.mvc_board.vo.MemberVO;

@Service
public class MailService {

	@Autowired
	private MailClient mailClient;
	
	public MailAuthInfo sendAuthMail(MemberVO member) {
		
		// 인증 메일에 포함시킬 인증코드(난수) 생성
		String auth_code = GenerateRandomCode.getRandomCode(5);
		System.out.println("생성된 인증 코드: " + auth_code);
		// ===============================================
		// [ 인증 메일 발송 요청 ]
		String subject = "[아이티윌] 가입 인증 메일입니다.";
		String url = "http://localhost:8080/mvc_board/MemberEmailAuth?email=" + member.getEmail() + "&auth_code=" + auth_code;
		String content = "<a href='" + url + "'>이메일 인증을 수행하려면 이 링크를 클릭!</a>";
		
		// MailClient - sendMail() 메서드 호출하여 메일 발송 요청
//		mailClient.sendMail(member.getEmail(), subject, content);
		// 단, 메일 발송 과정에서 메일 전송 상황에 따라 시간 지연이 발생할 수 있는데
		// 이 과정에서 다음 작업이 실행되지 못하고 발송완료 시점까지 대기하게 된다.
		// (ex. 사용자 입장에서 가입 완료 화면이 표시되지 않고 요청 화면이 그대로 유지됨)
		// 따라서, 메일 발송 작업과 나머지 작업을 별도로 분리하여 동작 시키기 위해
		// 메일 발송 메서드 호출 작업을 하나의 쓰레드로 동작시키면 별도로 분리가 가능!
		// 즉, 메일 발송이 완료되지 않더라도 다음 작업 진행이 가능!
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				mailClient.sendMail(member.getEmail(), subject, content);
//			}
//		}).start();
		
		// 람다식
		mailClient.sendMail(member.getEmail(), subject, content);
//		new Thread(() -> mailClient.sendMail(member.getEmail(), subject, content)).start();
		
		MailAuthInfo mailAuthInfo = new MailAuthInfo(member.getEmail(), auth_code);
		return mailAuthInfo;
	}
}






