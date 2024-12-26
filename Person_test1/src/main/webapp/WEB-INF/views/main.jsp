<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- 외부 CSS 파일 (resources/css/default.css) 연결하기 --%>
<%-- 클라이언트가 요청할 외부 자원 접근을 위한 경로 지정 시 컨텍스트 루트 기준이 아닌 서버 상의 루트 기준으로 탐색 --%>
</head>
<body>
<body class="body">
<main>
	<h1><a href="#"><img src="images/logo.png" class="img"></a></h1>
	<form action="#" id="form_id" method="post">
		<fieldset>
			<legend>로그인 양식</legend>
		<ul>
			<li><input type="text" placeholder="아이디" class="id"></li>
			<li><input type="password" placeholder="비밀번호" class="pw"></li>
			<li><button type="button" class="login" onclick="location.href='show.com'">로그인</button></li>
		</ul>
		</fieldset>
	</form>
		<ul>
		<li><button type="button" id="sign" onclick="location.href='join.com'">회원가입</button></li>
		</ul>
</main>

<footer>
<p class="ft">ⓒTABLEORDER, All rights reserved.</p>
</footer>

</body>
</html>