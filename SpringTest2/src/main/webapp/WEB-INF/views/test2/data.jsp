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
		<h1>data.jsp</h1>
		<form action="data_process1" method="get">
			<input type="hidden" name="hiddenValue" value="Hidden Value">
			<input type="text" name="name" placeholder="이름"><br>
<!-- 			<input type="text" name="nickname" placeholder="별명"><br> -->
			<input type="text" name="age" placeholder="나이"><br>
			<input type="radio" name="gender" value="M">남
			<input type="radio" name="gender" value="F">여<br>
			<input type="submit" value="data_process1 서블릿 요청(GET)">
		</form>
		<hr>
		<form action="data_process2" method="post">
			<input type="hidden" name="hiddenValue" value="Hidden Value">
			<input type="text" name="name" placeholder="이름"><br>
<!-- 			<input type="text" name="nickname" placeholder="별명"><br> -->
			<input type="text" name="age" placeholder="나이"><br>
			<input type="radio" name="gender" value="M">남
			<input type="radio" name="gender" value="F">여<br>
			<input type="submit" value="data_process2 서블릿 요청(POST)">
		</form>
		<hr>
		<form action="data_process3" method="get">
			<input type="hidden" name="board_num" value="10">
			<input type="hidden" name="pageNum" value="3">
			<input type="submit" value="data_process3 서블릿 요청(GET)">
		</form>
	</article>
	
	
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp"></jsp:include>		
	</footer>

</body>
</html>