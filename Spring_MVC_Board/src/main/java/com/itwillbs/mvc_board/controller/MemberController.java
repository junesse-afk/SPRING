package com.itwillbs.mvc_board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.mvc_board.service.MemberService;
import com.itwillbs.mvc_board.vo.MemberVO;

//@RestController //해당 클래스의 모든 메서드가 @ResponseBody가 적용됨
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
		//String email = member.getEmail1() + "@" + member.getEmail2();
		//member.setEmail(email);
		
		System.out.println(member);
		
		int insertCnt = memberService.registMember(member);
		System.out.println("insert 결과: " + insertCnt);
		return "";
	}
	
	@ResponseBody //리턴되는 문자열이 데이터가 되도록 변경
	@GetMapping("checkId")
	public Map<String, String> checkId(@RequestParam Map<String, String> param) {
		//System.out.println("Ajax 호출됨");
		//System.out.println(param);
		
		//자바스크립트 success에서 데이터를 사용하려면 json 형태인것이 편리하다.
		// 이떄, 가장 비슷한 Map을 리턴하더라도 자동 json으로 파싱이 불가능
		// pom.xml에 jackson 라이브러리를 추가하여 편리하게 json으로 파싱 가능
		// (부트에서는 디폴트로 jackson 추가되어있음)
		
		int cnt = memberService.checkId(param);
		Map<String, String> resultMap = new HashMap<String, String>();
		
		if (cnt == 0 ) {
			resultMap.put("msg", "사용 가능한 아이디!");
			resultMap.put("color", "blue");	
		} else {
			resultMap.put("msg", "아이디 중복!");
			resultMap.put("color", "red");
		}
		
		return resultMap;
		//로직이 들어갈 경우 service로 다 빼야함
	}
	
//	public List<Map<String, String>> checkId(MemberVO member) {
//		System.out.println("Ajax 호출됨");
//		System.out.println(member);
//		
//		//자바스크립트 success에서 데이터를 사용하려면 json 형태인것이 편리하다.
//		// 이떄, 가장 비슷한 Map을 리턴하더라도 자동 json으로 파싱이 불가능
//		// pom.xml에 jackson 라이브러리를 추가하여 편리하게 json으로 파싱 가능
//		// (부트에서는 디폴트로 jackson 추가되어있음)
//		
//		List<Map<String, String>> list = new ArrayList();
//		
//		Map<String, String> resultMap = new HashMap<String, String>();
//		resultMap.put("msg", "아이디 중복!");
//		resultMap.put("color", "red");
//		
//		list.add(resultMap);
//		list.add(resultMap);
//		
//		return list;
//	}
	
}