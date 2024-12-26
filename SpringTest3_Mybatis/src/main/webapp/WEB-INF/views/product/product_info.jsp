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
			상품번호: <br>
			상품명:  <br>
			가격:  <br>
			수량:  <br>
			파일명: 
			<input type="button" value="홈으로" onclick="location.href='./'">
			<input type="button" value="상품목록조회" onclick="location.href='studentList'">
			<input type="button" value="상품삭제" onclick="confirmDelete()">
			<input type="button" value="상품정보수정" onclick="location.href='productModify?product_id='">
		</h3>
	</div>
	
	<script type="text/javascript">
	function confirmDelete(product_id) {
		if(confirm("삭제하시겠습니까?")) {
			location.href = 'productDelete?product_id=' + product_id;
		}
	}
	</script>
	
	
</body>
</html>