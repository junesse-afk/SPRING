package com.itwillbs.mvc_board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.itwillbs.mvc_board.service.MemberService;
import com.itwillbs.mvc_board.vo.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("MemberJoin")
	public String MemberJoinForm() {
		return "member/member_join_form";
	}
	
	@PostMapping("MemberJoin")
	public String MemberJoin(MemberVO member) {
		String email = member.getEmail1() + "@" + member.getEmail2();
		member.setEmail(email);
		
		System.out.println(member);
		
		int insertCnt = memberService.registMember(member);
		System.out.println("insert 결과: " + insertCnt);
		return "";
	}
}
