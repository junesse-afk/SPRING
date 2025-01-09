<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/resources/css/default.css" rel="stylesheet" type="text/css">
</head>
<body>

	<header>
		<jsp:include page="/WEB-INF/views/inc/top.jsp" />
	</header>

	<article>
		<h1>회원 정보 수정</h1>
		<form action="MemberModify" name="joinForm" method="post">
			<table border="1">
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" id="name" value="${member.name }" pattern="^[가-힣]{2,6}$" title="한글 2~6글자"></td>
				</tr>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="id" id="id" value="${member.id }" placeholder="4~16자 영문자,숫자,_ 조합" readonly>
						<span id="checkIdResult"></span>
					</td>
				</tr>
				<tr>
					<th>기존 비밀번호</th>
					<td>
						<input type="password" name="oldPasswd" id="oldPasswd" placeholder="8 ~ 16글자 사이 입력">
						<span id="checkPasswdResult"></span>
					</td>
				</tr>
				<tr>
					<th>새 비밀번호</th>
					<td>
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
						<input type="text" name="post_code" id="post_code" value="${member.post_code }" size="6" readonly=>
						<input type="button" value="주소검색" id="btnSearchAddress"><br>
						<input type="text" name="address1" id="address1" value="${member.address1 }" size="30" readonly placeholder="기본주소"><br>
						<input type="text" name="address2" id="address2" value="${member.address2 }" size="30" placeholder="상세주소">
					</td>
				</tr>
				<tr>
					<th>E-Mail</th>
					<td>
						<%-- 
						이메일 주소(email) 분리(기준문자열: @) 후 각각의 텍스트 박스에 표시
						JSTL - functions 라이브러리의 split() 함수 필요
						1) 이메일 주소를 분리하여 별도의 변수에 저장
						2) 분리된 각 배열의 문자열을 각각 텍스트 박스에 출력
						 --%>
						
						<c:set var="arrEmail" value="${fn:split(member.email, '@')}"></c:set>
												
						<input type="text" id="email1" name="email1" value="${arrEmail[0] }" size="10"> @
						<input type="text" id="email2" name="email2" value="${arrEmail[1] }" size="10">
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
							<option value="개발자" <c:if test="${member.job eq '개발자' }">selected</c:if>> 개발자</option>
							<option value="DB엔지니어" <c:if test="${member.job eq 'DB엔지니어' }">selected</c:if>>DB엔지니어</option>
							<option value="관리자" <c:if test="${member.job eq '관리자' }">selected</c:if>>관리자</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<input type="radio" name="gender" value="M" <c:if test="${member.gender eq 'M' }">checked</c:if>>남
						<input type="radio" name="gender" value="F" <c:if test="${member.gender eq 'F' }">checked</c:if>>여
					</td>
				</tr>
				<tr>
					<th>취미</th>
					<td>
						<input type="checkbox" name="hobby" value="여행" <c:if test="${fn:contains(member.hobby, '여행')}">checked</c:if> >여행
						<input type="checkbox" name="hobby" value="독서" <c:if test="${fn:contains(member.hobby, '독서')}">checked</c:if>>독서
						<input type="checkbox" name="hobby" value="게임" <c:if test="${fn:contains(member.hobby, '게임')}">checked</c:if>>게임
						<input type="checkbox" id="checkAllHobby">전체선택
					</td>
				</tr>
				<tr>
					<th>가입동기</th>
					<td>
						<textarea rows="5" cols="40" name="motivation">${member.motivation }</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="정보수정">
						<input type="button" value="회원탈퇴" onclick="location.href='MemberWithdraw'">
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