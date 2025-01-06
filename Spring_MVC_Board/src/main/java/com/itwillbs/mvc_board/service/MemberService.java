package com.itwillbs.mvc_board.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.mvc_board.mapper.MemberMapper;
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
}
