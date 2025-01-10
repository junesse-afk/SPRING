package com.itwillbs.mvc_board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.mvc_board.vo.BoardVO;

@Mapper
public interface BoardMapper {
	public int registBoard(BoardVO board);
	public List<BoardVO> getBoardList();
	
}
