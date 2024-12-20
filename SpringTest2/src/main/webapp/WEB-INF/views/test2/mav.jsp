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
	<h1>MAV.jsp</h1>
	<h3>PersonVo 객체의 name : ${person.name }</h3>
	<h3>PersonVo 객체의 name : ${person.age }</h3>
	<h3>PersonVo 객체의 name : ${person.gender }</h3>
	<hr>
	<h3>TestVo 객체의 subject : ${test.subject }</h3>
	<h3>TestVo 객체의 subject : ${test.content }</h3>
	</article>
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>		
	</footer>

</body>
</html>