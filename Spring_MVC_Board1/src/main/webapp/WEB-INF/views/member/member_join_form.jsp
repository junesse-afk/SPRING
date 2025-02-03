<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/resources/css/default.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.7.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/member_join_form.js"></script>
</head>
<body>
	<header>
		<jsp:include page="/WEB-INF/views/inc/top.jsp" />
	</header>

	<article>
		<h1>회원 가입</h1>
		<form action="MemberJoin" name="joinForm" method="post">
		<table border="1">
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" id="name" pattern="^[가-힣]{2,6}$" title="한글 2~6글자"></td>
				</tr>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="id" id="id" placeholder="4~16자 영문자,숫자,_ 조합">
						<span id="checkIdResult"></span>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<!-- 
						비밀번호 입력란 change 이벤트 핸들링
						=> 비밀번호가 영문자, 숫자, 특수문자(!@#$%) 조합 8글자 ~ 16글자 사이
						=> 조합 대상 외의 문자가 포함되거나 길이가 부적합할 경우
							"영문자, 숫자, 특수문자(!@#$%) 조합 8 ~ 16글자 필수!" 메세지를 span영역에 출력
						=> 아니면 복잡도 검사를 체크하여 "안전", "보통", "위험", "사용불가" 4단계 메세지 출력	
						 -->
						<input type="password" name="passwd" id="passwd" placeholder="8 ~ 16글자 사이 입력">
						<span id="checkPasswdResult"></span>
					</td>
				</tr>
				<tr>
					<th>비밀번호확인</th>
					<td>
						<input type="password" name="passwd2" id="passwd2">
						<span id="checkPasswd2Result"></span>
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>
						<input type="text" name="post_code" id="post_code" size="6">
						<input type="button" value="주소검색" id="btnSearchAddress"><br>
						<input type="text" name="address1" id="address1" size="30" placeholder="기본주소"><br>
						<input type="text" name="address2" id="address2" size="30" placeholder="상세주소">
					</td>
				</tr>
				<tr>
					<th>E-Mail</th>
					<td>
						<input type="text" id="email1" name="email1" size="10"> @
						<input type="text" id="email2" name="email2" size="10">
						<select name="emailDomain">
							<option value="">직접입력</option>
							<option value="naver.com">naver.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="nate.com">nate.com</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>직업</th>
					<td>
						<select name="job">
							<option value="">항목을 선택하세요</option>
							<option value="개발자">개발자</option>
							<option value="DB엔지니어">DB엔지니어</option>
							<option value="관리자">관리자</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="gender" value="M">남
						<input type="radio" name="gender" value="F">여
					</td>
				</tr>
				<tr>
					<th>취미</th>
					<td>
						<input type="checkbox" name="hobby" value="여행">여행
						<input type="checkbox" name="hobby" value="독서">독서
						<input type="checkbox" name="hobby" value="게임">게임
						<input type="checkbox" id="checkAllHobby">전체선택
					</td>
				</tr>
				<tr>
					<th>가입동기</th>
					<td>
						<textarea rows="5" cols="40" name="motivation"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="가입">
						<input type="reset" value="초기화">
						<input type="button" value="돌아가기" onclick="histoty.back()">
					</td>
				</tr>
			</table>
		</form>
	</article>
	<footer>
		<jsp:include page="/WEB-INF/views/inc/bottom.jsp" />	
	</footer>
	<!-- ==================================================== -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
		let btnSearchAddress = document.querySelector('#btnSearchAddress');
		btnSearchAddress.onclick = function(){
			
			new daum.Postcode({
				
				// 주소 검색 창에서 주소 검색 후 검색된 주소를 사용자가 클릭 시
				// oncomplete 이벤트에 의해 이벤트 뒤의 익명함수가 자동으로 호출됨
				// 사용자가 클릭한 주소 정보가 익명함수 파라미터 data 로 전달됨
				// => 주의! 이 익명함수는 개발자가 호출하는 것이 아니라
				//    API에 의해 자동으로 호출됨
				//    (어떤 동작 수행 후 자동으로 호출되는 함수를 콜백(callback) 함수라고 함)    
				
		        oncomplete: function(data) {
					document.querySelector('#post_code').value = data.zonecode
					
					let addr = data.address;
					if (data.buildingName != "") {
						addr += " (" + data.buildingName + ")";
					}
					document.querySelector('#address1').value = addr;
					document.querySelector('#address2').focus();
		        }
		    }).open();
		}
	
	    
	</script>
</body>
</html>