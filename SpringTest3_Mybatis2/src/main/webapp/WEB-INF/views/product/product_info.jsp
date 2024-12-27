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
		<h1>상품 상세 정보</h1>
		<h3>
		<table border="1">
		<tr>
			<th>상품번호</th>
			<th>상품명</th>
			<th>가격</th>
			<th>수량</th>
			<th>파일명</th>
		</tr>
		<tr>
			<td>${product.product_id }</td>
			<td>${product.product_name }</td>
			<td>${product.product_price }</td>
			<td>${product.product_qty }</td>
			<td>${product.product_img }</td>
		</tr>
		</table>
			<input type="button" value="홈으로" onclick="location.href='./'">
			<input type="button" value="상품목록조회" onclick="location.href='productList'">
			<input type="button" value="상품삭제" onclick="confirmDelete(${product.product_id})">
			<input type="button" value="상품정보수정" onclick="location.href='productModify?product_id=${product.product_id}'">
		</h3>
	</div>
	
	<script type="text/javascript">
	function confirmDelete(product_id) {
		if(confirm("삭제하시겠습니까?")) {
			location.href = 'productDelete?product_id=' + product_id;
			// => 삭제 완료 후 "상품 목록" productList로 이동
		}
	}
	</script>
	
	
</body>
</html>