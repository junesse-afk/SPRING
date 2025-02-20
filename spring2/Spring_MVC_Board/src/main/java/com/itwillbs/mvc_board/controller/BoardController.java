package com.itwillbs.mvc_board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.mvc_board.aop.LoginCheck;
import com.itwillbs.mvc_board.aop.LoginCheck.MemberRole;
import com.itwillbs.mvc_board.service.BoardService;
import com.itwillbs.mvc_board.vo.BoardVO;
import com.itwillbs.mvc_board.vo.PageInfo;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

	// 업로드 경로(이클립스 프로젝트 상의 경로)
	String virtualPath = "/resources/upload";
	
	@LoginCheck(memberRole = MemberRole.ADMIN)
	@GetMapping("BoardWrite")
	public String boardWrite(HttpSession session, Model model) {
		
//		String id = (String)session.getAttribute("sId");
//		if(id == null) {
//			model.addAttribute("msg", "접근 권한이 없습니다!");
//			model.addAttribute("url", "MemberLogin");
//			return "result/fail";
//		}
		
		return "board/board_write_form";
	}
	
	
	// 파일 업로드 처리를 위해 뷰페이지 폼 태그에 "multipart/form-data" 추가하고
	// servlet-context.xml 파일에 파일 업로드 관련 설정 추가 필요
	@PostMapping("BoardWrite")
	public String boardWrite(
			BoardVO board, 
			HttpServletRequest req,
			HttpSession session,
			Model model) {
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
		
		// =========================================================
		// [ 파일 업로드 처리 ]
		// 실제 파일 업로드 처리를 수행하기 위해 프로젝트 상의 가상의 업로드 경로 생성 필요(upload)
		// => 외부에서 업로드 파일에 접근(다운로드)가능하도록 webapp/resources 경로에 생성
		// => 단, 실페 파일이 업로드 되는 위치는 별도의 경로로 관리됨
		//   (즉, 이클립스 프로젝트 상에서 관리하는 경로와 실제 톰캣의 경로가 달라짐)
		// => 가상의 경로 예시: D:\Shared\BACKEND\SPRING\workspace_spring\Spring_MVC_Board\src\main\webapp\resources\ upload
		
		
		// 1. 가상의 경로에 대한 서버(톰캣)상의 실제 경로 알아내기 (= 톰캣이 관리하는 실제 경로)
		// => 이클립스 프로젝트 상에서 업로드 폴더 생성 후 파일 업로드 수행 시
		//    이클립스에 연결된 톰캣이 관리하는 폴더에 업로드 폴더가 생성되기 때문
		// => reqest 객체 또는 session 객체의 getServletContext().getRealPath() 활용
		String realPath = session.getServletContext().getRealPath(virtualPath);
		System.out.println("실제 업로드 경로: " + realPath);
		// D:\Shared\BACKEND\SPRING\workspace_spring\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Spring_MVC_Board\resources\ upload
		
		// 2. 업로드 경로 관리
		// 업로드 파일에 대한 관리 용이성을 증대시키기 위해 서브 디렉토리 활용하여 분산 관리
		String subDir = createDirectories(realPath);
		realPath += "/" + subDir;
		
		// 3. 업로드 되는 실제 파일 처리
		// 실제 파일은 BoardVO 객체의 MultipartFile 타입 객체로 관리됨 (멤버변수명 fileX)
		MultipartFile mFile1 = board.getFile1();
		MultipartFile mFile2 = board.getFile2();
		MultipartFile mFile3 = board.getFile3();
//		System.out.println("원본파일명1: " + mFile1.getOriginalFilename());
//		System.out.println("원본파일명2: " + mFile2.getOriginalFilename());
//		System.out.println("원본파일명3: " + mFile3.getOriginalFilename());

		/*
		 * [ 파일명 중복 방지 대책 ] 
		 * - 서로 다른 파일이 동일한 파일명을 갖는 경우 같은 디렉토리에 업로드 불가!
		 * - 파일명 앞에 날짜 및 시간 관련 값 또는 난수값을 결합하여 중복 방지 처리 필수!
		 *   => 만약, 난수 사용 시 숫자만으로 이루어진 난수보자 문자와 함께 결합된 난수가 더 효율적
		 *   => 또한, 날자 및 시간 관련 정보를 long 타입으로 변환하여 난수처럼 사용해도 됨
		 * - 난수 생성 시 기본 난수 생성 라이브러리(SecureRandom 등)를 활용하거나
		 *   java.util.UUID 클래스 활용하여 난수 생성 또는 별도의 라이브러리 활용 가능
		 *   => UUID: 현재 시스템(서버)에서 랜덤ID 값을 추출하여 제공하는 클래스
		 *   	(Universally Unique IDentifier: 범용 고유 식별자)
		 *      => 32자리의 16진수로 구성됨(8자리-4자리-4자리-4자리-12자리)  
		 * */	
		// 1) UUID 클래스의 randomUUID() 메서드 활용
//		String uuid = UUID.randomUUID().toString();
//		System.out.println("uuid: " + uuid);
		// da8f53e5-dd89-4ca7-810d-14af10f91a2f
		
		// 2. System 클래스의 currenTimeMills() 메서드 활용
//		System.out.println("currentTimeMillis: " + System.currentTimeMillis());
		// 1738720018097
		
		// 임시)
		board.setBoard_file("");
		board.setBoard_file1("");
		board.setBoard_file2("");
		board.setBoard_file3("");
		
		String fileName1 = "";
		String fileName2 = "";
		String fileName3 = "";
		
		String origin1 = mFile1.getOriginalFilename();
		String origin2 = mFile2.getOriginalFilename();
		String origin3 = mFile3.getOriginalFilename();
		// 업로드 파일명이 널스트링이 아닐 경우 판별하여 파일명 저장
		if (!origin1.equals("")) {
			fileName1 = UUID.randomUUID().toString() + "_" + origin1;
			board.setBoard_file1(subDir + "/" + fileName1);
		}
		if (!origin2.equals("")) {
			fileName2 = UUID.randomUUID().toString() + "_" + origin2;
			board.setBoard_file2(subDir + "/" + fileName2);
		}
		if (!origin3.equals("")) {
			fileName3 = UUID.randomUUID().toString() + "_" + origin3;
			board.setBoard_file3(subDir + "/" + fileName3);
		}
		
//		System.out.println("DB에 저장될 파일명1: " + board.getBoard_file1());
//		System.out.println("DB에 저장될 파일명2: " + board.getBoard_file2());
//		System.out.println("DB에 저장될 파일명3: " + board.getBoard_file3());
		
		// =========================================================		
		int insertCnt = boardService.registBoard(board);
		
		// 작업 성공 시 "BoardList" 서블릿 주소로 리다이렉트
		// 실패 시 "fail.jsp" 페이지로 포워딩(msg 속성값: "글쓰기 실패!")
		if (insertCnt > 0) {
			
			try {
				if (!mFile1.getOriginalFilename().equals("")) {
					mFile1.transferTo(new File(realPath, fileName1));
				}
				if (!mFile2.getOriginalFilename().equals("")) {
					mFile2.transferTo(new File(realPath, fileName2));
				}
				if (!mFile3.getOriginalFilename().equals("")) {
					mFile3.transferTo(new File(realPath, fileName3));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
		
		
//		if(true) {
//			throw new RuntimeException();
//		}
		
		
		
		
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
		
		// 파일명 처리를 위해 사용자 정의 메서드 addFileListToModel() 메서드 호출
		// => 뷰페이지에서 파일 목록의 효율적 처리를 위해 별도의 가공 작업 수행
		addFileListToModel(board, model);
		
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
		addFileListToModel(board, model);
		
		return "board/board_modify_form";
	}
	
	@PostMapping("BoardModify")
	public String boardModify(
			BoardVO board, 
			@RequestParam(defaultValue = "1") int pageNum,
			HttpSession session,
			Model model) {
		
		System.out.println("!@#!@#");
		System.out.println("board: " + board);
		
		
		// 실제 경로 .meta/xxx/xxx/xxx/upload
		// 경로 생성 (서브디렉토리 포함) 2025/02/01
		// 기존 realPath 에 subDir 경로 결합
		String realPath = session.getServletContext().getRealPath(virtualPath);
		String subDir = createDirectories(realPath);
		realPath += "/" + subDir;
		
		// 파일명 중복 방지 대첵 수행 /xxx/xxx/upload/2025/02/01/uuid_실제파일명
		List<String> fileNames = duplicateFileNames(board, subDir);
		
		int updateCnt = boardService.modifyBoard(board);
		
		if (updateCnt > 0) { // 성공
			
			// 실제 파일 업로드(임시경로 -> 실제경로) 처리를 위해 completeUpload() 메서드 호출
			completeUpload(realPath, board, fileNames);
			
			return "redirect:/BoardDetail?board_num=" + board.getBoard_num() + "&pageNum=" + pageNum;
		} else { // 실패
			model.addAttribute("msg", "글 수정 실패!");
			return "result/fail";
		}
	}
	
	// [ 게시물 내의 파일 삭제 비지니스 로직 - AJAX 요청]
	// AJAX 요청에 대한 응답 데이터를 생성하여 직접 전송하기 위해서는
	// 매핑 메서드에 @ResponseBody 어노테이션 지정 필수!
	@ResponseBody
	@PostMapping("BoardDeleteFile")
	public Map<String, Object> boardDeleteFile(
			@RequestParam Map<String, String> map,
			HttpSession session) {
//		System.out.println("!@#!@#");
//		System.out.println(map);
		
		Map<String, Object> result = new HashMap();
		result.put("result", false);
		
		int deleteCnt = boardService.removeBoardFile(map);
		
		// DB에서 해당 파일명 삭제 성공 시 실제 업로드 된 파일 삭제 처리
		if (deleteCnt > 0) {
			// 실제 업로드 경로 알아내기
			String realPath = session.getServletContext().getRealPath(virtualPath);
			// D:\Shared\BACKEND\SPRING\workspace_spring\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Spring_MVC_Board\resources\ upload
			
			// 업로드 경로와 파일명(서브디렉토리) 결합하여 Path 객체 생성
			Path path = Paths.get(realPath, map.get("file"));
			
			// File 클래스의 deleteIfExists() 메서드 호출하여
			// 해당 파일이 실제 서버상에 존재할 경우에만 삭제 처리
			try {
				Files.deleteIfExists(path);
				result.put("result", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	//[답글 작성 폼 처리]
	
	@GetMapping("BoardReply")	
	public String boardReplyForm(int board_num, Model model) {
		
		//조회수 증가되지 않도록 두번째 파라미터 false 전달
		BoardVO board = boardService.getBoard(board_num, false);
		model.addAttribute("board", board);
	 
		return "board/board_reply_form";
	}
	
	//[답글 작성 비즈니스 로직]
	//=> 새글 작성 처리 방법과 거의 동일함
	@PostMapping("BoardReply")
	public String boardReply(BoardVO board, HttpServletRequest req) {
		
		
		String ip = req.getRemoteAddr();
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		board.setBoard_writer_ip(ip);
		
		board.setBoard_file("");
		board.setBoard_file1("");
		board.setBoard_file2("");
		board.setBoard_file3("");
		
		//-------------------
		int insert = boardService.registReplyBoard(board);
		
		return "redirect:/BoardList";
	}
	
	// ===========================================
	// ===========================================
	// ===========================================
	// ============ 유틸리티 메서드 ===================
	// ===========================================
	
	// 뷰페이지에서 파일 목록의 효율적 처리를 위해 별도의 가공 작업 수행하는 메서드
	// => 파일 정보가 저장된 BoardVO
	private void addFileListToModel(BoardVO board, Model model) {
		// 1. List 객체 생성 후
		//   BoardVO 객체의 실제 업로드 파일명을 모두 List에 추가
		List<String> fileList = new ArrayList();
		// 2025/02/05/2272f03c-3032-4a47-9bf2-0d3e0e5e289b_1.jpg
		fileList.add(board.getBoard_file1());
		fileList.add(board.getBoard_file2());
		fileList.add(board.getBoard_file3());
		
		// 2. List 객체 생성 후
		//   실제 파일명을 가공하여 원본 파일명을 추출하여 List 객체에 추가
		List<String> originalFileList = new ArrayList();
		for (String file : fileList) {
			// 2025/02/05/2272f03c-3032-4a47-9bf2-0d3e0e5e289b_1.jpg
			if (!file.equals("")) {
				file = file.split("_")[1];
			}
			originalFileList.add(file);
		}
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("originalFileList", originalFileList);
		// => Model 객체를 참조타입 변수 그대로 전달받았으므로
		//   별도로 리턴하지 않아도 변경된 Model 객체 그대로 공유됨
	}
	
	// 파일 업로드 시점에 맞는 날짜별 서브디렉토리 생성
	private String createDirectories(String realPath) {
		// Date 클래스 또는 LocalXXX 클래스 활용
		// 실제 날자 및 시간 관련 정보 생성 시에는 LocalXXX 클래스를 사용하고
		// 기존에 있는 라이브러리를 사용할 때 Date를 요구하는 경우에 Date 타입 활용
		
		// 1. LocalXXX 클래스 활용
		// => 날짜 정보: LocalDate, 시간정보: LocalTime, 날짜 및 시간: LocalDateTime
		LocalDate today = LocalDate.now(); // 현재 시스템의 날짜 정보 생성
		// 2025-02-05
		
		// 2. 날짜 포멧을 디렉토리 형식에 맞게 변경(ex. 2025-01-06 => 2025/01/06)
		String datePattern = "yyyy/MM/dd";
		
		// 3. 지정한 포멧을 적용하여 날짜 형식 변경
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
		String subDir = today.format(dtf);
		
		// 4. 기존 실제 업로드 경로에 서브디렉토리 결합
		realPath += "/" + subDir;
		
		try {
			// 5. 해당 디렉토리를 실제 경로상에 생성
			// 5-1) java.nio.file.Paths 클래스의 get() 메서드 호출하여 Path 객체 리턴받기
			Path path = Paths.get(realPath);
			
			// 5-2) Files 클래스의 static 메서드 createDirectories() 호출하여 실제 경로 생성
			// => 파라미터로 Path 타입 객체 전달
			// => 이때, 경로 상에서 생성되지 않은 모든 디렉토리를 생성해준다!
			//   (만약, 서브디렉토리 상의 최종 디렉토리 1개만 생성 시 createDirectory() 메서드 사용가능)
			Files.createDirectories(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return subDir;
	}
	
	// BoardVO 객체에 저장된 파일들에 대한 파일명 중복처리를 수행하는 메서드
	public List<String> duplicateFileNames(BoardVO board, String subDir) {
		MultipartFile mFile1 = board.getFile1();
		MultipartFile mFile2 = board.getFile2();
		MultipartFile mFile3 = board.getFile3();

		board.setBoard_file("");
		board.setBoard_file1("");
		board.setBoard_file2("");
		board.setBoard_file3("");

		String fileName1 = "";
		String fileName2 = "";
		String fileName3 = "";
		
		String origin1 = mFile1.getOriginalFilename();
		String origin2 = mFile2.getOriginalFilename();
		String origin3 = mFile3.getOriginalFilename();
		
		if (!origin1.equals("")) {
			fileName1 = UUID.randomUUID().toString() + "_" + origin1;
			board.setBoard_file1(subDir + "/" + fileName1);
		}
		if (!origin2.equals("")) {
			fileName2 = UUID.randomUUID().toString() + "_" + origin2;
			board.setBoard_file2(subDir + "/" + fileName2);
		}
		if (!origin3.equals("")) {
			fileName3 = UUID.randomUUID().toString() + "_" + origin3;
			board.setBoard_file3(subDir + "/" + fileName3);
		}
		
		// 중복 처리 완료된 파일명들을 하나의 List 객체에 추가 후 리턴
		List<String> fileNames = new ArrayList<String>();
		fileNames.add(fileName1);
		fileNames.add(fileName2);
		fileNames.add(fileName3);
		
		return fileNames;
	}
	
	public void completeUpload(String realPath, BoardVO board, List<String> fileNames) {
		
		List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		fileList.add(board.getFile1());
		fileList.add(board.getFile2());
		fileList.add(board.getFile3());
		
		try {
			
			for (int i = 0; i < fileNames.size(); i++) {
				MultipartFile mFile = fileList.get(i);
				String fileName = fileNames.get(i);
				
				if (!mFile.getOriginalFilename().equals("")) {
					mFile.transferTo(new File(realPath, fileName));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
