<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<hr>
<div id="footer_area">
	<img src="resources/images/logo.png">
	<!-- 
	주의! 클라이언트가 실행하는 HTML 태그 상에서 상대경로로 루트(/) 지정 시
	프로젝트 상의 루트가 아닌 서바상의 루트를 요청하게 되므로 실제 요청 주소는
	다음과 같다 http://localhost:8080/resources/images/logo.png
	=> 현재 프로젝트 컨텍스트 루트가 test2 이므로 이 경로 생략 시 존재하지 않는
		경로로 404에러 발생!
	=> 프로젝트 컨텍스트 루트를 실제 루트(/)로 설정시에는 정상적으로 작동
		(아직 배우지 않음)
	
	
	resources 앞에 / 붙이면 localhos:8080/resources로
	인식 되기 때문에 이미지 깨짐
	 -->
	 <h4>/test2로 했을때</h4>
	<img src="/test2/resources/images/logo.png">
	<h4>pageContext로 했을때</h4>
	<img src="${pageContext.request.contextPath }/resources/images/logo.png">
	<!-- pageContext -> test2 동적으로 바꿀 수 있나봄 어디서? 서버.xml에서-->
</div>