package com.itwillbs.mvc_board.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoginCheckAspect {
	
	//포인트컷 설정
	//com.itwillbs.mvc_board.aop.LoginCheck 인터페이스에 해당하는 어노테이션 지정된 Target 실행전
	// =>@annotation 지정 시 특정 어노테이션이 붙은 Target 탐색
	// => 메서드 정의 시 리턴 타입은 상황에 맞게 지정(void도 가능)
	// => 단, 메서드 파라미터로 스프링 빈으로 관리되는 객체 지정 불가능(컴파일 에러는 없지만 실행X)
	
	//@Before("@annotation(com.itwillbs.mvc_board.aop.LoginCheck)")
	//public void loginCheck() {}
	
	// 일반 사용자용 세션과 관리자용 세션을 구분하여 체크할 경우
	// 만약, advice 메서드 내에서 해당 어노테이션 내의 메서드 및 enum에 접근하려면
	// 포인트컷 설정 시 && 연산자 뒤에 @annotation(변수명)을 기술하고
	// 메서드 파라미터로 어노테이션 타입 변수를 설정하되, 변수명을 @annotation(변수명)과 동일하게 지정

	@Before("@annotation(com.itwillbs.mvc_board.aop.LoginCheck)&&@annotation(loginCheck)")
	public void loginCheck(LoginCheck loginCheck) {
//		System.out.println("loginCheck() 실행됨!!!!!");
//		System.out.println(">>>>>>>>회원타입 : " + loginCheck.memberRole());
		
//		switch (loginCheck.memberRole()) {
//		case ADMIN: System.out.println("ADMIN!!!!"); break;
//		default USER: System.out.println("USER!!!!"); break;
//		}
		//---------------------------------------------------
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		if(attrs == null) return;
		HttpServletRequest req = ((ServletRequestAttributes)attrs).getRequest();
		HttpServletResponse res = ((ServletRequestAttributes)attrs).getResponse();
		
		//HttpSession 객체 가져오기
		HttpSession session = req.getSession();
		String id = (String)session.getAttribute("sId");
		if(id == null) {
			// 포워딩 또는 리다이렉트를 직접 기술할 경우 해당 코드들에 대한 중복이 또 발생할 수 있다
			// 이때, Exception 처리 기능을 응용하여 아이디 없을 때 강제로 특정 예외를 발생시킨 후
			// 해당 예외 처리시 포워딩이나ㅣ 리다이렉트를 수행하도록 할 수 있다!
			
			throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED,
					"회원만 이용 가능합니다!|/MemberLogin") {};
		}
		
	}
}
