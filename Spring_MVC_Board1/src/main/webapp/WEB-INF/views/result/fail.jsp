<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	// 컨트롤러에서 Model 객체를 통해 저장한 msg 속성값을 alert() 함수를 통해 출력
	alert("${msg}");
	
	if ('${url}' == '') {
		// 이전 페이지로 돌아가기
		history.back(); 	
	} else {
		// 지정된 페이지로 이동
		location.href = '${url}';
	}
	
	
	
</script>