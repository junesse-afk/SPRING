package com.itwillbs.person_tableorder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableOrderController {

	@GetMapping("regist")
	public String registForm() {
		return "member/member";
	}
	
	@GetMapping("show")
	public String showList() {
		return "show/show";
	}
}
