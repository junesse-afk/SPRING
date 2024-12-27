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

	<div align="center">
		<h1>main.jsp</h1>
		<hr>
		<h3><a href="registStudent">학생정보등록</a></h3>
		<hr>
		<form action="studentInfo" method="get">
			<input type="text" name="idx" placeholder="학생번호입력">
			<input type="submit" value="학생정보조회">
		</form>
		<hr>
		<h3><a href="registProduct">상품정보등록</a></h3>
		<%-- 상품(Product) --%>
		<form action="productInfo" method="get">
			<input type="text" name="product_id" placeholder="상품번호입력">
			<input type="submit" value="상품정보조회">
		</form>
		<hr>
		<h3><a href="productList">상품목록조회</a></h3>
	</div>
	
</body>
</html>