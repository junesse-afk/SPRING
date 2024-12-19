package com.itwillbs.test2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Test3Controller_Redirect2 {

	/*
	 * [ 리다이렉트 방식 포워딩 시 데이터 전송 방법 ]
	 * 1) HttpServletRequest 객체를 통해 속성을 저장하는 형태로 데이터 전송하는 경우(불가)
	 * 	=> 리다이렉트 과정에서 새로운 요청이 발생하므로 새로운 요청에 따른 새 request 객체가 생성되어
	 *     이전 요청 정보를 가진 request 객체가 사라지고 새로운 요청 정보만 남기 때문에
	 *     이전 request 객체의 데이터에는 접근이 불가능하다!
	 * */
	
//	@PostMapping("redirect2")
//	public String redirec2(HttpServletRequest request) {
//		
//		// request 객체에 "msg" 속성명으로 "Hello, World! - request" 문자열 저장
//		request.setAttribute("msg", "Hello, World! - request");
//		
//		return "redirect:/redirectServlet2";
//	}
//	
//	@GetMapping("redirectServlet2")
//	public String redirectServlet2(HttpServletRequest request) {
//		
//		// request 객체에 저장된 "msg" 속성값을 콘솔에 출력
//		System.out.println("msg 속성값: " + request.getAttribute("msg"));
//		// => redirect2() 메서드에서 사용된 request 객체는
//		//    현재 redirectServlet2() 메서드의 request 객체와 다른 객체다!
//		//    (새로운 요청에 의해 새 request 객체가 생성되었기 때문)
//		
//		return "test2/redirect2";
//	}
	
	// ============================================================
//	@PostMapping("redirect2")
//	public String redirec2() {
//		
//		String name = "hong";
//		int age = 20;
//		
//		return "redirect:/redirectServlet2?name=" + name + "&age=" + age;
//		// => 요청 URL: http://~~/redirectServlet2?name=hong&age=20
//	}
//	
//	@GetMapping("redirectServlet2")
//	public String redirectServlet2(HttpServletRequest request) {
//		
//		String name = request.getParameter("name");
//		String age = request.getParameter("age");
//		
//		System.out.println("name 파라미터값: " + name);
//		System.out.println("age 파라미터값: " + age);
//		// => 새 요청 발생 시 URL 파라미터를 통해 전달된 데이터는 새 request 객체가 관리하므로
//		//    요청을 받는 서버측에서 request 객체를 통해 파라미터에 접근 가능하다!
//		
//		return "test2/redirect2";
//	}
	// ============================================================
	// 스프링 전용 데이터 공유 객체인 Model 타입 객체를 사용하여 데이터를 전송하는 경우
	// => Model 객체에 데이터(속성)저장 후 리다이렉트 시 자동으로 URL 파라미터 형식으로 변환됨
	@PostMapping("redirect2")
	public String redirec2(Model model) {
		
		String name = "hong";
		int age = 20;
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return "redirect:/redirectServlet2";
	}
	
	@GetMapping("redirectServlet2")
	public String redirectServlet2(HttpServletRequest request) {
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		System.out.println("name 파라미터값: " + name);
		System.out.println("age 파라미터값: " + age);
		// ----------------------------------------------------------
		System.out.println("name 속성값: " + request.getAttribute("name"));
		System.out.println("age 속성값: " + request.getAttribute("age"));
		
		return "test2/redirect2";
	}
	
	
	
	
}
