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
		<h1>상품 수정 폼</h1>
		<form action="productModify" method="post">
			<input type="text" name="product_id" placeholder="상품번호" value="${product.product_id }"><br>
			<input type="text" name="product_name" placeholder="상품명" value="${product.product_name}"><br>
			<input type="text" name="product_price" placeholder="가격" value="${product.product_price}"><br>
			<input type="text" name="product_qty" placeholder="수량" value="${product.product_qty}"><br>
			<input type="file" name="product_img"><br>(현재 업로드 파일: ${product.product_img})
			<input type="submit" value="등록"> <%-- 수정 완료 후 "상품상세" productInfo로 이동 --%>
		</form>
	</div>
</body>
</html>