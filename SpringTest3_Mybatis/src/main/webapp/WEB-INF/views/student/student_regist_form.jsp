<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<div align="center">
		<h1>학생 정보 등록</h1>
		<form action="registStudent" method="post">
			<input type="text" name="idx" placeholder="번호"><br>
			<input type="text" name="name" placeholder="이름"><br>
			<input type="submit" value="등록">
		</form>
	</div>
</body>
</html>