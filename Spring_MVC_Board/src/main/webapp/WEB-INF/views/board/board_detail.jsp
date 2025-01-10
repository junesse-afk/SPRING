<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<!-- 외부 CSS 파일(css/default.css) 연결하기 -->
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
<style type="text/css">
	#articleForm {
		width: 500px;
		height: 100%;
		margin: auto;
	}
	
	#articleForm table {
		border: 1px solid black;
		margin: auto;
		width: 500px;
	}
	
	th, td {
 		border: 1px solid black;
	}
	
	th {
		width: 70px;
	}
	
	td {
		text-align: center;
	}
	
	#basicInfoArea {
		height: auto;
		text-align: center;
	}
	
	#board_file {
		height: auto;
		font-size: 12px;
	}
	
	#board_file div {
		margin: 3px;
	}
	
	#articleContentArea {
		background: orange;
		margin-top: 10px;
		min-height: 200px;
		text-align: center;
		overflow: auto;
		white-space: pre-line;
	}
	
	#commandCell {
		text-align: center;
		margin-top: 10px;
		padding: 10px;
		border-top: 1px solid gray;
	}
	
</style>
</head>
<body>
	<header>
		<%-- inc/top.jsp 페이지 삽입(jsp:include 액션태그 사용 시 / 경로는 webapp 가리킴) --%>
		<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
	</header>
	<!-- 게시판 등록 -->
	<article id="articleForm">
		<h1>글 상세내용 보기</h1>
		<section id="basicInfoArea">
			<table>
				<tr>
					<th class="td_title">제목</th>
					<td colspan="3"></td>
				</tr>
				<tr>
					<th class="td_title">작성자</th>
					<td></td>
					<th class="td_title">작성일시</th>
					<td></td>
				</tr>
				<tr>
					<th class="td_title">작성자IP</th>
					<td></td>
					<th class="td_title">조회수</th>
					<td></td>
				</tr>
				<tr>
					<th class="td_title">첨부파일</th>
					<td colspan="3" id="board_file"></td>
				</tr>
			</table>
		</section>
		<section id="articleContentArea">
			
		</section>
		<section id="commandCell">
			<%-- 목록 버튼 항상 표시 --%>
			<input type="button" value="목록" onclick="">
		</section>
	</article>
	<footer>
		<!-- 회사 소개 영역(inc/bottom.jsp) 페이지 삽입 -->
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
	</footer>
</body>
</html>





















