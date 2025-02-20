<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
					<td colspan="3">${board.board_subject }</td>
				</tr>
				<tr>
					<th class="td_title">작성자</th>
					<td>${board.board_name }</td>
					<th class="td_title">작성일시</th>
					<td>${board.board_date }</td>
				</tr>
				<tr>
					<th class="td_title">작성자IP</th>
					<td>${board.board_writer_ip }</td>
					<th class="td_title">조회수</th>
					<td>${board.board_readcount }</td>
				</tr>
				<tr>
					<th class="td_title">첨부파일</th>
					<td colspan="3" id="board_file">
					<%--
					파일 다운로드 기능을 추가하려면 하이퍼링크에 download 속성 추가
					=> 하이퍼링크 경로는 업로드 경로 상의 파일명 지정
					=> 만약, 다운로드 버튼으로 변경하려면 버튼을 하이퍼링크로 감싸기
					=> download 속성값 지정 시 지정된 이름으로 다운로드 파일명이 변경됨
					 --%>
<%-- 						<a href="${pageContext.request.contextPath }/resources/upload/${board.board_file1}" download>${board.board_file1 }</a><hr> --%>
<%-- 						<a href="${pageContext.request.contextPath }/resources/upload/${board.board_file2}" download>${board.board_file2 }</a><hr> --%>
<%-- 						<a href="${pageContext.request.contextPath }/resources/upload/${board.board_file3}" download="aaa"> --%>
<!-- 							<input type="button" value="다운로드">	 -->
<!-- 						</a><hr>					 -->

					<c:forEach var="file" items="${fileList }" varStatus="status">
						<c:if test="${not empty file }">
							<div>
								${originalFileList[status.index] }
								<a href="${pageContext.request.contextPath }/resources/upload/${file}" download="${originalFileList[status.index] }">
									<input type="button" value="다운로드">	
								</a>
							</div>	
						</c:if>
					</c:forEach>


					</td>
				</tr>
			</table>
		</section>
		<section id="articleContentArea">
			${board.board_content }
		</section>
		<section id="commandCell">
		
			<%-- 답글, 수정, 삭제 버튼 모두 로그인 한 사용자에게만 표시 --%>
			<c:if test="${not empty sessionScope.sId}">
				<input type="button" value="답글" onclick="location.href='BoardReply?board_num=${param.board_num}'">
				<%-- 수정, 삭제 버튼은 작성자가 세션아이디와 동일할 경우에만 표시 --%>
				<c:if test="${sessionScope.sId eq board.board_name or sessionScope.sId eq 'admin'}">
					<input type="button" value="수정" onclick="requestModify()">
					<input type="button" value="삭제" onclick="confirmDelete()">
				</c:if>
			</c:if>
			<%-- 목록 버튼 항상 표시 --%>
			<input type="button" value="목록" onclick="location.href='BoardList?pageNum=${param.pageNum}'">
		</section>
	</article>
	<footer>
		<!-- 회사 소개 영역(inc/bottom.jsp) 페이지 삽입 -->
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
	</footer>
	
	<script type="text/javascript">
		function confirmDelete(){
			if(confirm("삭제하시겠습니까?")){
				location.href = "BoardDelete?board_num=${board.board_num}";
			}
		}
		
		function requestModify(){
			location.href = 'BoardModify?board_num=${board.board_num}&pageNum=${param.pageNum}';
		}
	
	</script>
	
	
	
	
	
	
	
	
</body>
</html>





















