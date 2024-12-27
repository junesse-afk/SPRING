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
		<h1>학생 상세 정보</h1>
		<h3>
			번호: ${student.idx }<br>
			이름: ${student.name } <br>
			<input type="button" value="홈으로" onclick="location.href='./'">
			<input type="button" value="학생목록조회" onclick="location.href='studentList'">
		</h3>
	</div>
</body>
</html>