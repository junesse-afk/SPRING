package com.itwillbs.mvc_board2.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.mvc_board2.vo.MailAuthInfo;
import com.itwillbs.mvc_board2.vo.MemberVO;

@Mapper
public interface MemberMapper {
	
	int registMember(MemberVO member);
	int checkId(Map<String, String> param);
	MemberVO getMember(MemberVO member);
	MemberVO getMemberInfo(String id);
	int modifyMember(Map<String, String> map);
	int updateStatus(Map<String, String> map);
	// 메일 인증 등록 및 수정
	void mergeMailInfo(MailAuthInfo mailAuthInfo);
	// 메일 인증 정보 조회
	MailAuthInfo selectMailAuthInfo(MailAuthInfo mailAuthInfo);
	// Member 테이블의 mail_auth_status 'Y'로 변경
	void updateMailAuthStatus(MailAuthInfo mailAuthInfo);
	// MAIL_AUTH_INFO 테이블 Row 삭제
	void deleteMailAuthInfo(MailAuthInfo mailAuthInfo);
	
}
