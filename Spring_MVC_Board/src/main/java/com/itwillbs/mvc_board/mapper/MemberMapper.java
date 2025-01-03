package com.itwillbs.mvc_board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.mvc_board.vo.MemberVO;

@Mapper
public interface MemberMapper {
	
	int registMember(MemberVO member);
	
}
