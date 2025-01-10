package com.itwillbs.mvc_board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.itwillbs.mvc_board.service.BoardService;
import com.itwillbs.mvc_board.vo.BoardVO;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@GetMapping("BoardWrite")
	public String boardWrite(HttpSession session, Model model) {
		
		String id = (String)session.getAttribute("sId");
		if(id == null) {
			model.addAttribute("msg", "접근 권한이 없습니다!");
			model.addAttribute("url", "MemberLogin");
			return "result/fail";
		}
		
		return "board/board_write_form";
	}
	
	@PostMapping("BoardWrite")
	public String boardWrite(BoardVO board, HttpServletRequest req, Model model) {
		
		// 임시)
		board.setBoard_file("");
		board.setBoard_file1("");
		board.setBoard_file2("");
		board.setBoard_file3("");
		
		// ---------------------------------------
		// 게시물 작성자(클라이언트)의 IP 주소 정보 가져와서 BoardVO 객체에 저장
		// => 요청 정보에 IP 주소가 포함되므로 HttpServletRequest 객체 필요
//		System.out.println("!@#!@#");
//		System.out.println(req.getRemoteAddr());
		// 임시) localhost로 서버 접속 시 자신의 IP주소가 IPv6 형태 (0:0:0:0:0:0:0:1)
		//     로 표시되므로 IPv4 주소 형태 (127.0.0.1) 변환하여 저장 (실제로는 불필요)
		String ip = req.getRemoteAddr();
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		board.setBoard_writer_ip(ip);
		
		int insertCnt = boardService.registBoard(board);
		
		// 작업 성공 시 "BoardList" 서블릿 주소로 리다이렉트
		// 실패 시 "fail.jsp" 페이지로 포워딩(msg 속성값: "글쓰기 실패!")
		if (insertCnt > 0) {
			return "redirect:/BoardList"; 
		} else {
			model.addAttribute("msg", "글쓰기 실패!");
			return "result/fail";
		}
	}
	
	@GetMapping("BoardList")
	public String boardList(Model model) {
		List<BoardVO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		return "board/board_list";
	}
	
	
	
	
}
