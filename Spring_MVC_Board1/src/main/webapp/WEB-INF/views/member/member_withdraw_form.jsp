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
	
	<article>
		<h1>회원 탈퇴</h1>
		<form action="MemberWithdraw" method="post" id="loginForm">
			<input type="password" name="passwd" placeholder="패스워드" required><br>
			<input type="submit" value="로그인">
		</form>
	</article>
	
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp" />
	</footer>

</body>
</html>