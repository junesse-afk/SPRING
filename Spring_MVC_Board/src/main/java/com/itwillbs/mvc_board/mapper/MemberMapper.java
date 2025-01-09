package com.itwillbs.mvc_board.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.mvc_board.vo.MemberVO;

@Mapper
public interface MemberMapper {
	
	int registMember(MemberVO member);
	int checkId(Map<String, String> param);
	MemberVO getMember(MemberVO member);
	MemberVO getMemberInfo(String id);
	int modifyMember(Map<String, String> map);
	int updateStatus(Map<String, String> map);
}
