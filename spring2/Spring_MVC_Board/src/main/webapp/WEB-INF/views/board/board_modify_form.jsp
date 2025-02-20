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
	#writeForm {
		width: 500px;
		min-height: 550px;
		margin: auto;
		border: 1px solid gray
	}
	
	#writeForm > table {
		margin: auto;
		width: 500px;
	}
	
	.write_td_left {
		width: 150px;
		text-align: center;
	}
	
	.write_td_right {
		width: 300px;
	}
	
	#commandCell {
		text-align: center;
		margin-top: 10px;
		padding: 10px;
		border-top: 1px solid gray;
	}
	
	.img_btn {
		width: 15px;
		height: 15px;
		vertical-align: middle;
	}
	
</style>
</head>
<body>
	<header>
		<%-- inc/top.jsp 페이지 삽입(jsp:include 액션태그 사용 시 / 경로는 webapp 가리킴) --%>
		<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
	</header>
	<!-- 게시판 등록 -->
	<article id="writeForm">
		<h1>게시판 글 수정</h1>
		
		<form action="BoardModify" name="writeForm" method="post" enctype="multipart/form-data">
		
			<%-- 입력받지 않은 글번호, 페이지번호 파라미터를 hidden 속성으로 포함시키기 --%>
			<input type="hidden" name="board_num" value="${param.board_num }">
			<input type="hidden" name="pageNum" value="${param.pageNum }"> 
		
			<table>
				<tr>
					<td class="write_td_left"><label for="board_name">글쓴이</label></td>
					<td class="write_td_right"><input type="text" name="board_name" value="${sessionScope.sId }" required="required" /></td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td class="write_td_left"><label for="board_pass">비밀번호</label></td> -->
<!-- 					<td class="write_td_right"> -->
<!-- 						<input type="password" name="board_pass" required="required" /> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				<tr>
					<td class="write_td_left"><label for="board_subject">제목</label></td>
					<td class="write_td_right"><input type="text" id="board_subject" name="board_subject" value="${board.board_subject }" required="required" /></td>
				</tr>
				<tr>
					<td class="write_td_left"><label for="board_content">내용</label></td>
					<td class="write_td_right">
						<textarea id="board_content" name="board_content" rows="15" cols="40" required="required">${board.board_content }</textarea>
					</td>
				</tr>
				
				<tr>
					<td class="write_td_left"><label for="board_file">첨부파일</label></td>
					<td class="write_td_right">
					
					<%--
					첨부파일 존재 여부 판별하여 [파일선택 버튼] or [파일명과 삭제버튼] 표시
					forEach 태그 활용하여 fileList 객체 요소 갯수만큼 반복
					=> 반복요소 인덱스 값 또는 순서번호 확인을 위해서 varStatus 속성활용
					=> 반복 인덱스: varStatus속성명.index (0 부터 시작)
					   반복 순서번호: varStatus속성명.count (1 부터 시작)
					 --%>
						<c:forEach var="file" items="${fileList }" varStatus="status">
							<div id="fileDiv${status.count }" class="board_file">
							
							<%-- 파일명 존재 시 원본 파일명을 표시하고, 아니면 파일선택 버튼 표시 --%>		
							<c:choose>
								<c:when test="${not empty file }">
									<%-- 원본 파일명 접근 시 status.index 값 활용 TODO --%>
									<span>
										${originalFileList[status.index] }	
										<%-- 파일 삭제 힐크 생성 (파일 개별 삭제용) --%>
										<%-- 하이퍼링크 클릭 시 deleteFile() 함수 호출(글번호, 실제파일명, 카운트번호) 전달 --%>
										<a href="javascript:deleteFile(${board.board_num }, '${file }', ${status.count })"><img src="${pageContext.request.contextPath }/resources/images/delete-icon.png" class="img_btn" /></a>
									</span>
									<input type="file" name="file${status.count }" hidden> 		
								</c:when>
								<c:otherwise>
									<%-- 
									파일선택 항목 name 속성값을 다르게 부여하기 위해
									file문자열과 함께 반복문 count 속성값을 결합하여 사용
									 --%>
									<input type="file" name="file${status.count }">
								</c:otherwise>
							</c:choose>
							
							</div>
						</c:forEach>
					</td>
				</tr>
				
				
				
			</table>
			<section id="commandCell">
				<input type="submit" value="등록">&nbsp;&nbsp;
				<input type="reset" value="다시쓰기">&nbsp;&nbsp;
				<input type="button" value="취소" onclick="history.back()">
			</section>
		</form>
	</article>
	<footer>
		<!-- 회사 소개 영역(inc/bottom.jsp) 페이지 삽입 -->
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
	</footer>
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.1.js"></script>
	<script type="text/javascript">
	let isAjaxProcessing = false;
	$('form').on('submit', function(){
		if (isAjaxProcessing) {
			return false;
		}
		return true;
	});
	// ----- AJAX 활용하여 특정 파일 삭제 -----
	function deleteFile(board_num, file, index){
		console.log(board_num + ", " + file + ", " + index);
		// confirm() 메서드 활용하여 "삭제하시겠습니까?" 확인 후 확인 버튼 클릭 시
		// AJAX를 통해 "BoardDeleteFile" 서블릿 주소 요청(POST)
		if (!confirm('삭제하시겠습니까?')) return;
		isAjaxProcessing = true;
		$.ajax({
			type: "POST",
			url: "BoardDeleteFile",
			data: {
				board_num: board_num,
				file: file,
				index: index
			}
		}).done(function(res){
			
			isAjaxProcessing = false;
			
			if (!res.result) { // 파일 삭제 성공
				return;
			}
// 			location.reload();
			
			// 파일명과 delete.png 삭제
			let fileElem = $("input[name=file" + index + "]");		
			$(fileElem).parent().find('span').remove();
			$(fileElem).prop("hidden", false);
			
		}).fail(function(result){
		})
		
		
	}
	</script>
</body>
</html>








