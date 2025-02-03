<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
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
		<h1>로그인</h1>
		<%-- EL을 통해 쿠키 접근 문법: ${cookie.쿠키명.value} --%>
		<form action="MemberLogin" method="post" id="loginForm">
			<input type="text" name="id" value="${cookie.rememberId.value }" placeholder="아이디"><br>
			<input type="password" name="passwd" placeholder="패스워드"><br>
			<input type="checkbox" name="rememberId"
				<c:if test="${not empty cookie.rememberId.value }">checked</c:if>>아이디 기억하기<br>
			<input type="submit" value="로그인">
		</form>
	</article>
	
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp" />
	</footer>

</body>
</html>