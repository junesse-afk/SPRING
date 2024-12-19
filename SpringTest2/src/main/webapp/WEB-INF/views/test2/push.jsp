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
		<h1>push.jsp</h1>
		<%-- 디스패치 방식 포워딩으로 전달된 request 객체의 msg 속성값 출력 --%>
		<h3>
			msg 속성값: <%=request.getAttribute("msg") %><br>
			msg 속성값: ${requestScope.msg }<br>
			msg 속성값: ${msg} <%-- requestScope 객체명 생략 가능 --%>
							<%-- 전 영역 내에 속성명이 중복되지 않을 경우에만! --%>
		</h3>
		<%-- request 객체의 test 속성값 가져와서 객체 내의 멤버변수(subject, content) 값 출력 --%>
		<h3>
			test 속성값의 subject: ${test.subject }<br>
			test 속성값의 content: ${test.content }
		</h3>
		
		
	</article>
	
	
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>		
	</footer>

</body>
</html>