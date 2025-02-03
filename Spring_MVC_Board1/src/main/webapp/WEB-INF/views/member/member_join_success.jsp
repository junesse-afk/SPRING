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
		<h1>회원 가입 완료</h1>
		<h3>
			<input type="button" value="홈으로" onclick="location.href='./'">
			<input type="button" value="로그인" onclick="location.href='MemberLogin'">
		</h3>
	</article>
	
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp" />	
	</footer>
</body>
</html>