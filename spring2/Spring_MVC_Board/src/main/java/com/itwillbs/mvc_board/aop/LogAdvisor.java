package com.itwillbs.mvc_board.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvisor {
	
	//Advice(분리된 관심사)에 해당하는 메서드 정의
	//=>이때, Advice의 종류와 함께 Target의 JoinPoint에 결합되도록 Pointcut도 설정
	//1)Advice 종류 지정
	//  => showLog() 메서드를 Target의 JoinPoint 호출 전에 실행되도록 @Before 지정
	//2)Advice 어노테이션 내부에 Pointcut 설정
	//  => Pointcut 표현식 활용하여 Target의 JoinPoint 지정
	
	//@Before("execution(* com.itwillbs.mvc_board.service.MemberService.*(..))")
	// => MemberService 클래스의 모든 메서드를 대상으로 pointcut 설정
	
	@Before("execution(* com.itwillbs.mvc_board.service.BoardService.registBoard(com.itwillbs.mvc_board.vo.BoardVO))")
	public void showLog() {
		System.out.println("---------------AOP로 출력되는 로그---------------");
	}
}
