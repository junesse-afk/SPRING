<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/resources/css/default.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/inc/top.jsp" />
	</header>
	
	<article align="center">
		<h1>로그인</h1>
		<form action="MemberLogin" method="post" id="loginForm">
			<input type="text" name="id" placeholder="아이디"><br>
			<input type="password" name="passwd" placeholder="패스워드"><br>
			<input type="checkbox" name="rememberId">아이디 기억하기<br>
			<input type="submit" value="로그인">
		</form>
	</article>
	
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp" />
	</footer>

</body>
</html>