package com.itwillbs.mvc_board2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.mvc_board2.service.BoardService;
import com.itwillbs.mvc_board2.vo.BoardVO;
import com.itwillbs.mvc_board2.vo.PageInfo;

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
	public String boardList(
			@RequestParam(defaultValue = "1") int pageNum,
			Model model) {
		
		// ---------------------------------------------------
		// [ 페이징 처리 ]
		// 1. 페이징 처리를 위해 조회 목록 갯수 조절에 사용할 변수 선언
		// int pageNum = 1; // 화면에서 1 클릭했다고 가정
		int listLimit = 10;	// 한페이지 당 표시할 게시물 수
		int startRow = (pageNum - 1) * listLimit;

		// 2. 실제 뷰페이지에서 페이징 처리를 수행하는데 필요한 계산 작업
		// 1) 전체 게시물 갯수
		int listCount = boardService.getBoardListCount();
		
		// 2) 한 페이지에서 표시할 목록(페이지당 페이지 번호) 갯수 설정
		int pageListLimit = 3;	// 페이지 당 페이지 번호 갯수를 3으로 설정 (1 2 3 or 4 5 6)
		
		// 3) 최대 페이지번호 계산
		int maxPage = listCount / listLimit + (listCount % listLimit > 0 ? 1 : 0);
		// 단, 최대 페이지번호가 0일 경우(DB에 글이 한건도 없을 경우) 기본값 1
		if (maxPage == 0) {
			maxPage = 1;
		}
		
		// 4) 현재 페이지에서 보여줄 시작 페이지 번호 계산
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		// 5) 현재 페이지에서 보여줄 마지막 페이지 번호 계산
		int endPage = startPage + pageListLimit - 1;
		// 6) 단, 마지막 페이지 번호(endPage) 값이 최대 페이지 번호(maxPage) 보다 클 경우
		//    마지막 페이지 번호를 최대 페이지 번호로 교체
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 전달받은 페이지번호가 1보다 작거나 최대 페이지번호보다 클 경우
		// "fail.jsp" 페이지 포워딩을 통해 "해당 페이지는 존재하지 않습니다!" 출력
		// 1페이지 목록으로 이동하도록 처리
		if (pageNum < 1 || pageNum > maxPage) {
			model.addAttribute("msg", "해당 페이지는 존재하지 않습니다!");
			model.addAttribute("url", "BoardList");
			return "result/fail";
		}
		
		
		// --------------------------------------------------
		// 3. 페이징 정보를 관리하는 PageInfo 객체 생성 및 계산결과 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage, pageNum);
		model.addAttribute("pageInfo", pageInfo);
		
		List<BoardVO> boardList = boardService.getBoardList(startRow, listLimit);
		
		model.addAttribute("boardList", boardList);
		return "board/board_list";
	}
	
	// [ 글 상세조회 비지니스 로직 ]
	@GetMapping("BoardDetail")
	public String boardDetail(int board_num, Model model) {
		
		BoardVO board = boardService.getBoard(board_num, true);
		
		if (board == null) {
			model.addAttribute("msg", "존재하지 않는 게시물입니다");
			return "result/fail";
		}
		
		model.addAttribute("board", board);
		
		return "board/board_detail";
	}
	
	@GetMapping("BoardDelete")
	public String boardDelete(BoardVO board,
			HttpSession session,
			Model model) {
		
		String id = (String)session.getAttribute("sId");
		if(id == null) {
			model.addAttribute("msg", "접근 권한이 없습니다!");
			model.addAttribute("url", "MemberLogin");
			return "result/fail";
		}
		
		// -----------------------------------------------
		BoardVO dbBoard = boardService.getBoard(board.getBoard_num(), false);
		
		// 조회 결과가 없거나, 관리자가 아니고 세션 아이디와 작성자가 일치하지 않을 경우
		// "잘못된 접근입니다!" 메세지로 fail.jsp 페이지 포워딩 처리
		if (dbBoard == null || 
			(!id.equals("admin") && !id.equals(dbBoard.getBoard_name()))) {
			
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "result/fail";
		}
		
		// ---------------------------------------------
		// BoardService - removeBoard() 메서드 호출하여 글 삭제 요청
		// => 파라미터: BoardVO 객체, 리턴타입: int(deleteCnt)
		int deleteCnt = boardService.removeBoard(board);
		
		if (deleteCnt > 0) {
			return "redirect:/BoardList";
		} else {
			return "result/fail";
		}
		
		 
	}
	
	// ====================================================
	// [ 글 수정 폼 처리 ]
	@GetMapping("BoardModify")
	public String boardModify(
			int board_num, 
			HttpSession session,
			Model model) {
		
		String id = (String)session.getAttribute("sId");
		if(id == null) {
			model.addAttribute("msg", "접근 권한이 없습니다!");
			model.addAttribute("url", "MemberLogin");
			return "result/fail";
		}
		
		// 조회수 증가되지 않도록 두번째 파라미터 false 전달
		BoardVO board = boardService.getBoard(board_num, false);
		
		// 조회결과가 없거나, 관리자가 아니고 세션 아이디와 작성자가 일치하지 않을 경우
		// "잘못된 접근입니다!" 메시지로 fail.jsp 페이지 포워딩 처리
		if (board == null || (!id.equals("admin") && !id.equals(board.getBoard_name()))) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "result/fail";
		}
		
		model.addAttribute("board", board);
		
		return "board/board_modify_form";
	}
	
	@PostMapping("BoardModify")
	public String boardModify(
			BoardVO board, 
			@RequestParam(defaultValue = "1") int pageNum,
			Model model) {
		
		int updateCnt = boardService.modifyBoard(board);
		
		if (updateCnt > 0) { // 성공
			return "redirect:/BoardDetail?board_num=" + board.getBoard_num() + "&pageNum=" + pageNum;
		} else { // 실패
			model.addAttribute("msg", "글 수정 실패!");
			return "result/fail";
		}
	}
	
	
	
	
	
	
	
}
