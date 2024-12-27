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
		<h1>상품 등록 폼</h1>
		<form action="registProduct" method="post">
			<input type="text" name="product_id" placeholder="상품번호"><br>
			<input type="text" name="product_name" placeholder="상품명"><br>
			<input type="text" name="product_price" placeholder="가격"><br>
			<input type="text" name="product_qty" placeholder="수량"><br>
			<input type="file" name="product_img"><br>
			<input type="submit" value="등록">
			<%-- 상품정보 등록 완료 후 "상품목록" productList로 이동 --%>
		</form>
	</div>
</body>
</html>