package com.itwillbs.mvc_board.service;

import java.io.FileInputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.mvc_board.mapper.MemberMapper;
import com.itwillbs.mvc_board.vo.MailAuthInfo;
import com.itwillbs.mvc_board.vo.MemberVO;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper mapper;
	
	public int registMember(MemberVO member) {
		return mapper.registMember(member);
	}
	
	public int checkId(Map<String, String> param) {
		return mapper.checkId(param);
	}
	
	public MemberVO getMember(MemberVO member) {
		return mapper.getMember(member);
	}
	
	public MemberVO getMemberInfo(String id) {
		return mapper.getMemberInfo(id);
	}
	
	public int modifyMember(Map<String, String> map) {
		return mapper.modifyMember(map);
	}
	
	public int updateStatus(Map<String, String> map) {
		return mapper.updateStatus(map);
	}
	
	// ==============================================
	// 이메일 인증 정보 등록 요청
	public void registMailAuthInfo(MailAuthInfo mailAuthInfo) {
		mapper.mergeMailInfo(mailAuthInfo);
		
//		MailAuthInfo dbMailAuthInfo = mapper.getMailAuthInfo();
//		if (dbMailAuthInfo == null) {
//			mapper.inserMailAuth(mailAuthInfo);
//		} else {
//			mapper.updateMailAuth(mailAuthInfo);
//		}
	}
	
	// 메일 인증 처리 요청
	// => 메일 인증 처리 과정에서 UPDATE & DELETE 작업을 차례대로 수행하는데
	//    이때, 두 작업을 하나의 트랜젝션으로 묶어 처리하기 위해 @Transactional 어노테이션 사용
	//    (개발자가 별도로 commit 또는 rollback 작업을 지시하지 않아도 자동으로 처리)
	// => 주의! root-context.xml 파일에 마이바티스 설정 항목에 트랜잭션 설정 추가 필요
	@Transactional
	public boolean requestEmailAuth(MailAuthInfo mailAuthInfo) {
		
		boolean isAuthSuccess = false;
		MailAuthInfo dbMailAuthInfo = mapper.selectMailAuthInfo(mailAuthInfo);
		
		if (dbMailAuthInfo != null) { // 이메일에 대한 인증코드가 존재할 경우
			// 하이퍼링크를 통해 전달받은 인증코드와 DB에서 조회된 인증코드 문자열 비교
			if (mailAuthInfo.getAuth_code().equals(dbMailAuthInfo.getAuth_code())) {
				// 1. Member 테이블의 mail_auth_status를 'Y'로 변경
				mapper.updateMailAuthStatus(mailAuthInfo);
				
				// rollback 처리 확인
				if (true) {
					throw new RuntimeException();
				}
				
				// 2. MAIL_AUTH_INFO 테이블에 인증이 완료된 Row 삭제
				mapper.deleteMailAuthInfo(mailAuthInfo);
				
				isAuthSuccess = true;
			}
		}
		
		return isAuthSuccess;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
