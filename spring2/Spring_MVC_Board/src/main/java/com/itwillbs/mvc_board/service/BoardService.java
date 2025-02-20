package com.itwillbs.mvc_board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itwillbs.mvc_board.mapper.BoardMapper;
import com.itwillbs.mvc_board.vo.BoardVO;

@Service
public class BoardService {
	
	@Autowired
	BoardMapper mapper; // mybatis
	
	public int registBoard(BoardVO board) {
		return mapper.registBoard(board); 
	}
	
	public List<BoardVO> getBoardList(int startRow, int listLimit) {
		
//		//트랜잭션 확인용
//		if(true) {
//			throw new RuntimeException();
//		}
//		
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
	
	public int removeBoardFile(Map<String, String> map) {
		return mapper.removeBoardFile(map);
	}
	
	
	public int registReplyBoard(BoardVO board) {
		//기존 답글들의 순번(seq) 조정을 위해 updateBoardReSeq() 메서드 호출
		mapper.updateBoardReSeq(board);
		
		//답글 등록 작업 insertReplyBoard() 메서드 호출
		return mapper.insertReplyBoard(board);
	}
	
}
