package com.itwillbs.mvc_board2.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwillbs.mvc_board2.service.MailService;
import com.itwillbs.mvc_board2.service.MemberService;
import com.itwillbs.mvc_board2.vo.MailAuthInfo;
import com.itwillbs.mvc_board2.vo.MemberVO;

// @RestController // 해당 클래스의 모든 메서드에 @ResponseBody 가 적용됨
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private MailService mailService;
	
	@GetMapping("MemberJoin")
	public String memberJoinForm() {
		return "member/member_join_form";
	}
	
	@PostMapping("MemberJoin")
	public String memberJoin(
			MemberVO member, 
			BCryptPasswordEncoder passwordEncoder,
			Model model) {

		/*
		 * [ BCryptPasswordEncoder 클래스를 활용한 패스워드 단방향 암호화 ]
		 * => spring-security-web 또는 spring-security-crypto 라이브러리 활용
		 * */
		String securedPass = passwordEncoder.encode(member.getPasswd());
		System.out.println("평문: " + member.getPasswd());
		System.out.println("암호화: " + securedPass);
		// => 단, 매번 생성되는 암호문은 솔팅에 의해 (Salt 값에 의해) 항상 달라진다!
		
		// 암호화된 패스워드로 덮어쓰기
		member.setPasswd(securedPass);
		
		int insertCnt = memberService.registMember(member);
		
		if (insertCnt > 0) {
			
			// ----- 인증 메일 발송 작업 추가 -----
			MailAuthInfo mailAuthInfo = mailService.sendAuthMail(member);
			// 메일 인증정보 등록
			memberService.registMailAuthInfo(mailAuthInfo);
			 
			return "redirect:/MemberJoinSuccess";
		} else {
			model.addAttribute("msg", "회원가입 실패!");
			return "result/fail";
		}
		
		
		
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
	public String memberLogin(
			MemberVO member,
			String rememberId,
			HttpSession session, 
			Model model,
			BCryptPasswordEncoder passwordEncoder,
			HttpServletResponse res) {
		
//		MemberVO dbMember = memberService.getMember(member); // id, pass 1건
		
		// 패스워드가 암호화되어 DB에 insert 되어있기 때문에 id만 판별하여 1건 select
		MemberVO dbMember = memberService.getMemberInfo(member.getId());
		
		/*
		 * [ BCryptPasswordEncoder 객체를 활용한 패스워드 비교 ]
		 * - 입력받은 패스워드(=평문)와 DB에 저장된 패스워드(=암호문) 간의
		 *   직접적인 문자열 비교 시 무조건 두 문자열은 다름
		 * - 일반적인 해싱의 경우 새 패스워드도 해싱을 통해 암호문으로 변환하여 비교하면 되지만
		 *   현재, BCryptPasswordEncoder 객체를 통해 기존 패스워드를 암호화했기 때문에
		 *   솔팅값에 의해 두 암호는 서로 다른 문자열이 되어
		 *   DB에서 WHERE 절로 두 패스워드 비교 불가!
		 * - BCryptPasswordEncoder 객체의 matches() 메서드를 활용하여 비교 필수!
		 *   (내부적으로 암호문으로부터 솔팅값을 추출하여 평문을 암호화하여 암호문끼리 비교)   
		 * 
		 * */
		// 객체명.matches(평문, 암호문) 호출 시 boolean 타입 결과 리턴
//		boolean b = passwordEncoder.matches(member.getPasswd(), dbMember.getPasswd());
//		System.out.println("!@#!@#");
//		System.out.println(b);
		
		// 로그인을 위한 회원 상세정보 조회했을 때 처리 작업
		// 1) 로그인 실패(아이디 또는 패스워드 틀렷을 경우) 판별
		// 2) 로그인 불가능한 회원 상태(휴면(생략) or 탈퇴) 판별
		// 3) 위 모든 조건이 false일 경우 로그인 성공 처리
		if (dbMember == null) {
			model.addAttribute("msg", "아이디가 없습니다");
			return "result/fail";
		} else if (!passwordEncoder.matches(member.getPasswd(), dbMember.getPasswd())) {
			model.addAttribute("msg", "비밀번호가 틀립니다.");
			return "result/fail";
		} else if (dbMember.getMember_status() == 3){
			model.addAttribute("msg", "탈퇴한 회원입니다.");
			return "result/fail";
		} 
		// TODO
//		else if (!dbMember.getMail_auth_status().equals("Y")) {
//			model.addAttribute("msg", "이메일 인증 후 로그인 가능합니다!");
//			return "result/fail";
//		}
		else {
			
//			if (rememberId != null) { // 체크했을때
//				// 1. javax.servlet.http.Cookie 객체 생성
//				Cookie cookie = new Cookie("rememberId", member.getId());
//				// 2. 쿠키 유효기간(만료기간) 설정(초 단위)
//				cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
//				// 3. 클라이언트측으로 쿠키 정보를 전송하기 위해
//				//   응답 정보를 관리하는 HttpServletResponse 객체의 addCookie() 호출
//				res.addCookie(cookie);
//			} else { // 미체크시
//				// 기존 쿠키 중 "rememberId" 라는 이름의 쿠기 삭제
//				// => 쿠키는 삭제의 개념을 MaxAge 값(만료시간)을 0으로 설정 후 전송하여 처리
//				// => 이때, 쿠키 이름은 반드시 삭제해야할 쿠키의 이름을 정확하게 설정
//				Cookie cookie = new Cookie("rememberId", null);
//				
//				// 쿠키의 유효기간을 반드시 0으로 설정
//				// => 이 쿠키를 수신한 클라이언트는 해당 쿠키를 즉시 삭제
//				cookie.setMaxAge(0);
//				
//				res.addCookie(cookie);
//			}
			
			// ----- 쿠키 생성 코드 중복 제거 -----
			int age = (rememberId == null) ? 0 : (60 * 60 * 24 * 30);
			Cookie cookie = new Cookie("rememberId", member.getId());
			cookie.setMaxAge(age);
			res.addCookie(cookie);
			
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
	
	// =======================================
	// [ 이메일 인증 처리 비지니스 로직 ("MemberEmailAuth" - GET) ]
	@GetMapping("MemberEmailAuth")
	public String memberEmailAuth(
			MailAuthInfo mailAuthInfo, Model model) {
		System.out.println("mailAuthInfo: " + mailAuthInfo);
		
		// requestEmailAuth() 메서드 호출하여 인증처리 요청
		// => 파라미터: MailAuthInfo 객체, 리턴타입: boolean
		boolean isAuthSuccess = memberService.requestEmailAuth(mailAuthInfo);
		
		if (!isAuthSuccess) {
			model.addAttribute("msg", "메일 인증 실패!");
		} else {
			model.addAttribute("msg", "메일 인증 성공!\\n로그인 페이지로 이동합니다.");
			model.addAttribute("url", "MemberLogin");
		}
		return "result/fail";
	}
	
	
	
	
	
}
