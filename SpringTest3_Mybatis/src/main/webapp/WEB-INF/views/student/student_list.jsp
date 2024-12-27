<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h1>학생 목록 조회</h1>
		<table>
			<tr>
				<th>번호</th>
				<th>이름</th>
			</tr>
			<%-- foreach --%>
			<c:forEach var="student" items="${studentList }">
				<tr>
					<td>${student.idx }</td>
					<td>${student.name }</td>
				</tr>
			</c:forEach>
		</table>
	</div>



</body>
</html>