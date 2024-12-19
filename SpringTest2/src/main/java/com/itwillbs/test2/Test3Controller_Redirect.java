package com.itwillbs.test2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test3Controller_Redirect {

	/*
	 * 스프링에서 리다이렉트 방식 포워딩을 수행하려면
	 * return 문에 "redirect:/리다이렉트주소" 형식으로 지정
	 * => 클라이언트로 전송되는 응답데이터로 리다이렉트 정보를 전송하고 클라이언트로부터 다시 새 요청 발생
	 * => 리다이렉트 시 request 객체 공유 불가(= request 객체를 통해 데이터 전송 불가 => 초기화 되기 때문)
	 *    따라서, 기본적으로 리다이렉트 시에는 URL 파라미터(?)를 통해 데이터 전달 가능
	 * */
	@GetMapping("redirect")
	public String redirect() {
		System.out.println("redirect");
		return "redirect:/redirectServlet";
	}
	
	@GetMapping("redirectServlet")
	public String redirectServlet() {
		System.out.println("redirectServlet");
		return "test2/redirect";
	}
	
	
	
	
	
	
}
