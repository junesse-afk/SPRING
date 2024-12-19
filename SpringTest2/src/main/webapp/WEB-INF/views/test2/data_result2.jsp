<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<header>
		<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
	</header>

	<article>
		<h1>data_result2.jsp</h1>
		<h3>
			<%-- Model 객체를 통해 전달받은 속성(board_num, pageNum)값 출력 => ${속성명} --%>
			board_num 속성값: ${board_num }<br>
			pageNum 속성값: ${pageNum }
		</h3>
		<h3>
			<%-- Model 객체를 통해 전달받은 속성이 객체 타입일 경우 => ${속성명.객체접근방식} --%>
			<%-- ex) 일반 객체일 경우 Getter 메서드 호출 대신 ${속성명.멤버변수명} 지정 시 자동 Getter 호출 --%>
			<%-- ex) Map 객체일 경우 get() 메서드 호출 대신 ${속성명.Map객체의Key} --%>
			객체의 board_num값: ${map.board_num }<br>
			객체의 pageNum값: ${map.pageNum }
		</h3>
	</article>
	
	
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>		
	</footer>

</body>
</html>