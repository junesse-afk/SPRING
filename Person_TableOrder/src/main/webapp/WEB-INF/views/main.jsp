<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>TableOrder</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="body">
<main>
	<h1><a href="#"><img src="images/logo.png" class="img"></a></h1>
	<form action="#" id="form_id" method="post">
		<fieldset>
			<legend>로그인 양식</legend>
		<ul>
			<li><input type="text" placeholder="아이디" class="id"></li>
			<li><input type="password" placeholder="비밀번호" class="pw"></li>
			<li><button type="button" class="login" onclick="location.href='show'">로그인</button></li>
		</ul>
		</fieldset>
	</form>
		<ul>
		<li><button type="button" id="sign" onclick="location.href='regist'">회원가입</button></li>
		</ul>
</main>

<footer>
<p class="ft">ⓒTABLEORDER, All rights reserved.</p>
</footer>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>