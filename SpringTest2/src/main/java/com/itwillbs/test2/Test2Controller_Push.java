package com.itwillbs.test2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwillbs.test2.vo.TestVO;

@Controller
public class Test2Controller_Push {
	/*
	 * < 스프링에서 다른 페이지로 Dispatcher 방식 포워딩 시 데이터 전달 방법 2가지 >
	 * 1. 기존 JSP에서 사용하던 HttpServletRequest 객체의 setAttribute() 사용
	 * 	=> 컨트롤러 클래스 메서드 JSP 파일이 아니므로 JSP 내장 객체(request)가 존재하지도 않고
	 *     컨트롤러 클래스 메서드 파라미터에 HttpServletRequest 타입 변수가 명시되어 있지도 않음
	 *  => JSP에서는 톰켓에 의해 doGet() 또는 doPost() 메서드 호출 시
	 *     HttpServletRequest 객체가 자동으로 파라미터로 전달되어졌다.
	 *  => 스프링에서는 의존 주입(DI = Dependency Injection)을 통해 객체를 외부로부터 주입 받을 수 있음   
	 *  => push() 메서드 선언부의 파라미터 타입으로 HttpServletRequest 타입 변수를 선언하기만 하면
	 *     스프링에 의해 해당 메서드 호출 시점에 HttpServletRequest 객체가 자동으로 주입됨!  
	 * -----------------------------------------------------------
	 * 2. 스프링 전용 Model 객체의 addAttribute() 사용 => 스프링에서 사용하는 방법
	 * 	=> org.springframework.ui.Model 타입을 파라미터로 지정 시
	 * 	   데이터 저장이 가능한 Model 객체를 자동으로 주입받을 수 있음
	 * 	=> HttpServletRequest 객체와 데이터 저장 측면에서 유사하며
	 *     java.util.Map 객체 기반으로 만든 스프링에서 제공하는 데이터 공유 전용 객체
	 *     (스프링 전용 라이브러리 필요)
	 *  => Mode 객체에 데이터 저장 시 스프링에 의해 자동으로 HttpServletRequest 객체에 저장(후처리)   
	 * 	=> request.setAttribute() 와 마찬가지로 model.addAttribute() 메서드로 저장
	 *  => request 객체의 범위(Scope)동일 (클라이언트 요청에 대한 서버 응답 지점까지)
	 *  => request 객체와 데이터 저장 용도로 동시 사용 불가
	 *    (일반적인 데이터 저장 시 HttpServletRequest 객체 대신 Model 객체 사용)
	 * 
	 * */
	
	// 1번방법) 매핑 메서드 파라미터를 통해 필요한 HttpServletRequest 객체 주입 받기
//	@GetMapping("push")
//	public String push(HttpServletRequest request) {
//		// 매핑 과정에서 push() 메서드가 호출될 때 자동으로 객체가 주입됨
//		// => JSP 서블릿 클래스(= 컨트롤러) 정의 시 doGet(), doPost() 메서드가
//		//    request, response 객체를 전달받는 것과 형태가 동일함
//		// --------------------------------------------------
//		// request 객체의 setAttribute() 메서드 활용하여
//		// "msg" 속성명으로 "Hello, World! - request 객체" 문자열 저장
//		request.setAttribute("msg", "Hello, World! - request 객체");
//		
//		TestVO test = new TestVO("제목", "내용");
//		request.setAttribute("test", test);
//		
//		return "test2/push";
//	}
	// --------------------------------------------------
	// 2번방법) 매핑 메서드 파라미터를 통해 Model 타입 객체를 자동 주입받아 데이터 저장
	@GetMapping("push")
	public String push(Model model) {
		
		model.addAttribute("msg", "Hello, World! - model 객체");
		model.addAttribute("test", new TestVO("제목", "내용"));
		
		return "test2/push";
	}
	
	
	
	
	
	
	
	
	
	
}
