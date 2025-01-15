package com.itwillbs.mvc_board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itwillbs.mvc_board.mapper.BoardMapper;
import com.itwillbs.mvc_board.vo.BoardVO;

@Service
public class BoardService {
	
	@Autowired
	BoardMapper mapper;
	
	public int registBoard(BoardVO board) {
		return mapper.registBoard(board);
	}
	
	public List<BoardVO> getBoardList(int startRow, int listLimit) {
		return mapper.getBoardList(startRow, listLimit);
	}
	
	public int getBoardListCount() {
		return mapper.getBoardListCount();
	}
	
	public BoardVO getBoard(int board_num) {
		return mapper.getBoard(board_num);
	}
	
}
