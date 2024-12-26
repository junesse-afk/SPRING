<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="regist" id="join_fm" method="post">
	<fieldset>
		<legend>회원가입</legend>
		<ul>
			<li><input type="text" id="tid" placeholder="아이디"></li>
			<li><span id="idresult"></span>
			<li><input type="password" id="tpw" placeholder="비밀번호"></li>
			<li><input type="password" id="tpw" placeholder="비밀번호 확인"></li>
			<li><input type="text" id="tname" placeholder="이름"></li>
			<li><input type="tel" id="tphone" placeholder="전화번호"></li>
			<li><input type="text" id="tadd" placeholder="주소"></li>
			<li><input type="text" id="tadd" placeholder="주소1"></li>
			<li><input type="text" id="tadd" placeholder="주소2"></li>
			<li><input type="date" id="tdate"></li>
			<li><input type="text" placeholder="사업자등록증"></li>
			<li><button type="submit" id="join">회원가입</button></li>
			<li><button type="button" onclick="history.back()">돌아가기</button><li>
		</ul>
	</fieldset>
	</form>
</body>
</html>