package com.itwillbs.mvc_board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.mvc_board.service.MemberService;
import com.itwillbs.mvc_board.vo.MemberVO;

// @RestController // 해당 클래스의 모든 메서드에 @ResponseBody 가 적용됨
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("MemberJoin")
	public String memberJoinForm() {
		return "member/member_join_form";
	}
	
	@PostMapping("MemberJoin")
	public String memberJoin(MemberVO member) {
//		System.out.println(member);
		
		int insertCnt = memberService.registMember(member);
		System.out.println("insert 결과: " + insertCnt);
		return "redirect:/MemberJoinSuccess";
	}
	
	// 회원가입 완료 뷰페이지(member_join_success.jsp) 포워딩
	@GetMapping("MemberJoinSuccess")
	public String memberJoinSuccess() {
		return "member/member_join_success";
	}
	
	@GetMapping("MemberLogin")
	public String memberLogin() {
		return "member/member_login_form";
	}
	
	@PostMapping("MemberLogin")
	public String memberLogin(MemberVO member, HttpSession session, Model model) {
		
		MemberVO dbMember = memberService.getMember(member);
		System.out.println("!@#!@#");
		System.out.println(dbMember);
		
		if(dbMember == null) { // 로그인 실패
			
			model.addAttribute("msg", "로그인 실패!");
			
			return "result/fail";
			
		} else { // 로그인 성공
			session.setAttribute("sId", member.getId());
			// 세션 타이머 설정(ex. 금융권 사이트의 경우 10분(=600초))
			session.setMaxInactiveInterval(600);
			// 메인페이지로 리다이렉트
			return "redirect:/";
		}
	}
	
	@GetMapping("MemberLogout")
	public String memberLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// 회원 상세정보 조회
	@GetMapping("MemberInfo")
	public String memberInfo(HttpSession session, Model model) {
		// 세션 아이디 가져와서 로그인 여부 판별
		// => 만약, 미 로그인(세션 객체의 sId 속성값이 null)일 경우
		//    "접근 권한이 없습니다!" 메시지를 msg 속성에 저장 후 fail.jsp 포워딩
		String id = (String)session.getAttribute("sId");
		System.out.println(id);
		
		if (id == null) {
			model.addAttribute("msg", "접근 권한이 없습니다!");
			model.addAttribute("url", "MemberLogin");
			return "result/fail";
		}
		
		// [ 로그인 상태일 경우 ]
		MemberVO member = memberService.getMemberInfo(id);
		model.addAttribute("member", member);
		
		return "member/member_modify_form";
	}
	
	@PostMapping("MemberModify")
//	public String memberModify(MemberVO member, String oldPasswd) {
	public String memberModify(
			@RequestParam Map<String, String> map, 
			String hobby,
			HttpSession session,
			Model model) {
		
		System.out.println("!@#!@#");
		map.put("hobby", hobby); // 기존 hobby 덮어씌우기
		System.out.println(map);
		
		String id = (String)session.getAttribute("sId");
		if (id == null) {
			model.addAttribute("msg", "접근 권한이 없습니다!");
			model.addAttribute("url", "MemberLogin");
			return "result/fail";
		}
		
		MemberVO dbMember = memberService.getMemberInfo(id);
		// 화면에서 입력한 비밀번호(기존 비밀번호)와 DB비밀번호가 일치하면 수정!
		if (!dbMember.getPasswd().equals(map.get("oldPasswd"))) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			return "result/fail";
		}
		// ------------------------------------------------------------
		int updateCnt = memberService.modifyMember(map);
		
		if (updateCnt > 0) {
			return "redirect:/MemberInfo";
		} else {
			model.addAttribute("msg", "회원정보 수정 실패!");
			return "result/fail";
		}
	}
	
	@GetMapping("MemberWithdraw")
	public String memberWithdraw(HttpSession session, Model model) {
		String id = (String)session.getAttribute("sId");
		if(id == null) {
			model.addAttribute("msg", "로그인 필수!");
			model.addAttribute("url", "MemberLogin");
			return "result/fail";
		}
		return "member/member_withdraw_form";
	}
	
	@PostMapping("MemberWithdraw")
	public String memberWithdraw(
			@RequestParam Map<String, String> map, 
			HttpSession session, 
			Model model) {
		
		String id = (String)session.getAttribute("sId");
		
		MemberVO dbMember = memberService.getMemberInfo(id);
		// 화면에서 입력한 비밀번호(기존 비밀번호)와 DB비밀번호가 일치하면 수정!
		if (!dbMember.getPasswd().equals(map.get("passwd"))) {
			model.addAttribute("msg", "비밀번호가 틀립니다.");
			return "result/fail";
		}
		// 비밀번호 일치 시 map에 session에 id 추가
		map.put("id", id);
		
		int updateCnt = memberService.updateStatus(map);
		
		if(updateCnt > 0) {
			session.invalidate(); // session 초기화
			model.addAttribute("msg", "탈퇴 성공");
			model.addAttribute("url", "./"); // main 페이지로 이동
		} else {
			model.addAttribute("msg", "탈퇴 실패!");
		}
		return "result/fail";
		
		
	}
	
	
	@ResponseBody // 리턴되는 문자열이 데이터가 되도록 변경
	@GetMapping("checkId")
	public Map<String, String> checkId(@RequestParam Map<String, String> param) {
//		System.out.println("Ajax 호출됨!!!");
//		System.out.println(member);
//		System.out.println(param);
		
		// 자바스크립트 success에서 데이터를 사용하려면 json 형태인것이 편리하다.
		// 이때, 가장 비슷한 Map을 리턴하더라도 자동 json으로 파싱이 불가능
		// pom.xml에 jackson 라이브러리를 추가하여 편리하게 json으로 파싱 가능
		// (부트에서는 디폴트로 jackson 추가되어있음)
		int cnt = memberService.checkId(param);
		Map<String, String> resultMap = new HashMap<String, String>();
		if (cnt == 0) {
			resultMap.put("msg", "사용한 가능한 아이디");
			resultMap.put("color", "blue");
		} else {
			resultMap.put("msg", "아이디 중복!");
			resultMap.put("color", "red");
		}
		
		return resultMap;
	}
	
	
	
	
	
	
	
}
