<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 날짜 표시 형식을 변경하기 위해 JSTL - fmt 라이브러리 추가 --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<!-- 외부 CSS 파일(css/default.css) 연결하기 -->
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
<style type="text/css">
	#listForm {
		width: 1024px;
		max-height: 610px;
		margin: auto;
	}
	
	#listForm > table {
		margin: auto;
		width: 1024px;
	}
	
	#tr_top {
		background: orange;
		text-align: center;
	}
	
	table td {
		text-align: center;
	}
	
	/* 제목은 좌측 정렬 및 여백 설정 */
	#listForm .board_subject {
		text-align: left;
		padding-left: 20px;
	}
	#listForm .board_subject:hover {
		background-color: #FFC19E
	}
	
	#pageList {
		margin: auto;
		width: 1024px;
		text-align: center;
	}
	
	#emptyArea {
		margin: auto;
		width: 1024px;
		text-align: center;
	}
	
	#commandArea {
		margin: auto;
		width: 1024px;
		text-align: right;
	}
	
	/* 하이퍼링크 밑줄 제거 */
	a {
		text-decoration: none;
	}
</style>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.1.js"></script>
</head>
<body>
	<header>
		<%-- inc/top.jsp 페이지 삽입(jsp:include 액션태그 사용 시 / 경로는 webapp 가리킴) --%>
		<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
	</header>
	<article>
		<!-- 게시판 리스트 -->
		<h1>게시판 글 목록</h1>
		<section id="commandArea">
			<input type="button" value="글쓰기" onclick="location.href='BoardWrite'" />
		</section>
		<section id="listForm">
			<table>
				<tr id="tr_top">
					<td width="100px">번호</td>
					<td>제목</td>
					<td width="150px">작성자</td>
					<td width="150px">날짜</td>
					<td width="100px">조회수</td>
				</tr>
				
		<c:choose>
			<c:when test="${empty boardList }">
				<tr><td colspan="5">게시물이 존재하지 않습니다.</td></tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="board" items="${boardList }">
					<tr>
						<td class="board_num">${board.board_num }</td>
						<td class="board_subject">${board.board_subject }</td>
						<td>${board.board_name }</td>
						<td>
						<%--
						JSTL - format(fmt) 라이브러리를 활용하여 날짜 및 시간 형식 변경
						1) <fmt:formatDate>: Date 타입의 날짜의 날짜 형식 변경
							=> <fmt:formatDate value="${날짜 및 시간 객체}" pattern="표현패턴">
							=> 자바의 SimpleDateFormat 등의 포멧팅 클래스와 동일한 역할 수행
						2) <fmt:parseDate>: String 객체의 날짜 형식 변경
						---------------------------------------------------------------
						[ 날짜 및 시간 형식을 지정하는 패턴 문자 ]
						y: 연도 (yy: 연도 2자리, yyyy: 연도 4자리)
						M: 월 (MM: 월 2자리)
						d: 일 (dd: 일 2자리)
						H: 시 (HH: 24시간제, hh: 12시간제)
						m: 분 (mm: 분 2자리)
						s: 초 (ss: 초 2자리)
						 --%>
							<fmt:formatDate value="${board.board_date}" pattern="yy-MM-dd HH:mm"/>
						</td>
						<td>${board.board_readcount }</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
			</table>
		</section>
		
		<section id="pageList">
		
			<%-- [이전] 버튼 클릭 시 현재 페이지의 이전 페이지 요청(2페이지 일 경우 1페이지 요청) --%>
			<input type="button" value="이전" onclick="location.href='BoardList?pageNum=${pageInfo.pageNum - 1}'" 
				<c:if test="${pageInfo.pageNum eq 1}">disabled</c:if> >
		
			<%-- 계산괸 페이지 번호가 저장된 PageInfo 객체(pageInfo)를 통해 페이지 번호 반복 출력 --%>
			<c:forEach var="i" begin="${pageInfo.startPage }" end="${pageInfo.endPage }">
			<%-- 각 페이지번호 마가 하이퍼링크 설정 --%>
			<%-- 단, 현재 페이지(i값과 pageNum 동일)는 하이퍼링크 없이 굵게 표시 --%>
				<c:choose>
					<c:when test="${i eq pageInfo.pageNum }">
						<strong>${i }</strong>
					</c:when>
					<c:otherwise>
						<a href="BoardList?pageNum=${i }">${i }</a>		
					</c:otherwise>
				</c:choose>
			</c:forEach>
		
			<%-- [다음] 버튼 클릭 시 현재 페이지의 다음 페이지 요청 (2페이지일 경우 3페이지 요청) --%>
			<%-- 현재 목록의 페이지번호 + 1 값을 파라미터로 전달 --%>
			<%-- 단, 현재 페이지가 최대 페이지 번호와 동일할 경우 비활성화(disabled) --%>
			<input type="button" value="다음" onclick="location.href='BoardList?pageNum=${pageInfo.pageNum + 1}'" 
				<c:if test="${pageInfo.pageNum eq pageInfo.maxPage}">disabled</c:if> >
		</section>
		
	</article>
	
	<script type="text/javascript">
	// 게시물 제목열 클릭 이벤트 핸들링
	$(".board_subject").on("click", function(event){
		
		// [ 클릭된 제목에 해당하는 게시물의 글 번호 가져오기 ]
		// 1) 클릭 대상 요소의 부모 탐색하여 해당 부모의 자식들 중
		//    클래스 선택자가 "board_num"인 요소 탐색
// 		let parent = $(event.target).parent();
// 		let board_num = $(parent).find(".board_num").text();
		
		// 2) 클릭 대상 요소의 형재 노드 탐색
		let board_num = $(event.target).siblings(".board_num").text();
		
		// "BoardDetail" 서블릿 주소 요청
		// => 파라미터: 글번호(위에서 탐색한 번호), 페이지 번호(pageInfo 객체의 pageNum 속성값)
		location.href = "BoardDetail?board_num=" + board_num + "&pageNum=${pageInfo.pageNum}";
	});
	
	</script>
	
	
</body>
</html>













