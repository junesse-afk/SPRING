<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- 외부 CSS파일 연결하기 --%>
<%--<link rel="stylesheet" href="resources/css/default.css">  --%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/default.css">
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/inc/top.jsp"></jsp:include>
	</header>
	<article>
	<h1>index.jsp</h1>
		<form action="main" method="get">
			<input type="submit" value="메인페이지(GET)">
		</form>
		<form action="main" method="post">
			<input type="submit" value="메인페이지(POST)">
		</form>
	</article>
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>
	</footer>
</body>
</html>