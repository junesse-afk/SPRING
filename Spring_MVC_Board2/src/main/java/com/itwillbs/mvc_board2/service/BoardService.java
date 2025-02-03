package com.itwillbs.mvc_board2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.mvc_board2.mapper.BoardMapper;
import com.itwillbs.mvc_board2.vo.BoardVO;

@Service
public class BoardService {
	
//	@Autowired
	BoardMapper mapper;
	
	public BoardService(BoardMapper mapper) {
		this.mapper = mapper;
	}

	public int registBoard(BoardVO board) {
		return mapper.registBoard(board);
	}
	
	public List<BoardVO> getBoardList(int startRow, int listLimit) {
		return mapper.getBoardList(startRow, listLimit);
	}
	
	public int getBoardListCount() {
		return mapper.getBoardListCount();
	}
	
	// 게시물 상세정보 조회 요청
	public BoardVO getBoard(int board_num, boolean isIncrease) {
		if (isIncrease) {
			mapper.updateReadCount(board_num);
		}
		return mapper.getBoard(board_num);
	}
	
	public int removeBoard(BoardVO board) {
		return mapper.removeBoard(board);
	}
	
	public int modifyBoard(BoardVO board) {
		return mapper.modifyBoard(board);
	}
	
	
	
	
	
	
}
