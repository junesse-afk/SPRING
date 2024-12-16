package com.itwillbs.test2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



// 컨트롤러 역할을 수행할 클래스 Test1Controller_Main 클래스 정의
// => @Controller 어노테이션 선언
@Controller
public class Test1Controller_Main {

//		@RequestMapping(value= "/main", method = RequestMethod.GET)
//		public String requestMain() {
//			return "main";
//			
//			//만약 POST 방식으 "main" 서블릿 주소 요청 시 오류 발생!
//			// 일치하는 (=매핑된) 메서드가 없기 때문에
//			// HTTP 405 - 허용되지 않는 메서드(Request method POST not supported)
//		}
		
//		@RequestMapping(value = "/main", method = RequestMethod.POST)
//		public String requestMain() {
//			return "main";
//			
//			//주의 POST방식만 매핑시 GET 방식 요청에 대해서 일치하는 메서드가 
//		}
	
		// 메소드 생략시 get, post 방식 모두 매핑 가능
		// value 속성도 String []타입이므로 복수개의 문자열을 활용하여 복수개의 경로 매핑도 가능
		// 또한 기본 속성이 value 속성이므로 속성이 1개일 경우 value 속성명도 생략가능
		//@RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
		@RequestMapping("/main") // 루드를 의미하는 맨앞의 "/" 기호도 생략가능
		public String requestMain() {
			return "main";
		}
		
		//"test1" (GET) 매핑 시 test/test1.jsp 페이지 포워딩
//		@RequestMapping(value = "/test1", method = RequestMethod.GET)
//		public String requestTest() {
//			return "test/test1";
//		}
		
		// 서블릿 주소 매핑에 사용되는 @RequestMapping 어노테이션 대신
		// 스프링 4.3버전부터는 다른 어노테이션도 제공됨
		// get방식 요청 매핑 전용 어노테이션 : @GetMapping
		// post방식 요청 매핑 전용 어노테이션 : @postMapping
		// @RequestMapping 어노테이션의 method 속성을 어노테이션 이름 자체에 포함하고 있음
		
		@GetMapping("/test1")
		public String test1_post() {
			return "test/test1";
		}
		
		
		//"test2" (POST) 매핑 시 test/test2.jsp 페이지 포워딩
		// 밑에다가 파일 위치 잡아줘야함
//		@RequestMapping(value = "/test2", method = RequestMethod.POST)
//		public String requestTest2() {
//			return "test/test2";
//		}
		
		@PostMapping("/test2")
		public String test2_post() {
			return "test/test2";
		}
		
		
		


}
