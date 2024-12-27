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
		<h1>상품 목록</h1>
		
		<form action="productList" method="get">
			<input type="text" name="product_id" placeholder="상품번호">
			<input type="text" name="product_name" placeholder="상품명">
			<input type="submit" value="조회">
		</form>
		
		<table>
			<tr>
				<th>상품번호</th>
				<th>상품명</th>
				<th>가격</th>
				<th>수량</th>
				<th></th> <%-- 아래쪽 td에 "상세 정보" 버튼 --%>
			</tr>
			<%-- foreach --%>
			<c:forEach var="product" items="${productList }">
			<tr>
				<td>${product.product_id }</td>
				<td>${product.product_name }</td>
				<td>${product.product_price }</td>
				<td>${product.product_qty }</td>
				<td>
					<input type="button" value="상세정보" onclick="location.href='productInfo?product_id=${product.product_id }'">
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>



</body>
</html>