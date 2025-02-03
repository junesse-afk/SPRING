$(function(){
	
	// 비밀번호, 비밀번호 확인 입력값 체크 결과를 저장할 변수 선언
	let checkPasswd = false;
	let checkPasswd2 = false;
	
	// ------ Events --------
	// TODO
//	$('#id').on('change', checkId);	// id 변경 이벤트
//	$('#id').on('keyup', duplicateId);
//	
//	$('#passwd').on('change', checkPass); // passwd 확인 이벤트
//	$('#passwd2').on('change', checkPass2); // passwd 확인 이벤트
	$('form').on('submit', checkSubmit); // 최종 가입 버튼 클릭 이벤트
	
	
	
//	$('#passwd2').on('change', function(){
//		console.log('익명함수로 이벤트 연결!!!');
//		let passwd = $('#passwd').val();
//		let passwd2 = $('#passwd2').val();
//		
//		if (passwd == passwd2) {
//			// '비밀번호 일치', blue
//			$('#checkPasswd2Result').text('비밀번호 일치').css('color', 'blue');
//		} else {
//			// '비밀번호 불일치', red
//			$('#checkPasswd2Result').text('비밀번호 불일치').css('color', 'red')
//		}
//	});
	// ----------------------
		
	// ------- functions --------
	function checkId(){
		/*
		[ 자바스크립트에서 정규표현식 활용 방법 ]
		1. 정규표현식 패턴 문자열을 갖는 객체 생성
			1) let 변수명 = new RegExp("패턴문자열");
			2) let 변구명 = /패턴문자열/;
		2. 변수명.exec("검사할 문자열") 형태로 유효성 검사
			=> 검사 결과를 판별하여 적합할 경우 대상 문자열 리턴, 부적합할 경우 null리턴
			   (null 값이 리턴됐을 경우 패턴이 일치하지 않는 문자열이라는 의미)	
			=> 자바스크립트의 if문에 boolean 타입이 아닌 다른 타입의 값이 전달될 경우
			   값이 존재하기만 하면 무조건 true, null 또는 널스트링이면 false로 취급
		*/
		let id = $('#id').val();
		// 규칙: 영문자 또는 숫자만 첫글자에 올 수 있고
		//     두번째 글자부터 영문자, 숫자, 특수문자 _만 사용하여 4 ~ 16글자
		let regex = /^[A-Za-z0-9][A-Za-z0-9_]{3,15}$/;
		
		console.log("검사결과: " + regex.exec(id));
		
		if (regex.exec(id)) {
//			$('#checkIdResult').text('사용한 가능한 아이디');
//			$('#checkIdResult').css('color', 'blue');
			duplicateId();
		} else {
			$('#checkIdResult').text('사용한 불가능한 아이디');
			$('#checkIdResult').css('color', 'red');
		}
		
	} // checkId() 끝

	// ----- ID 중복 체크 (Ajax) -----
	/*
	AJAX(Asynchonous Javascript And Xml, 비동기식 자바스크립트&XML)
	- 웹 페이지(브라우저)의 갱신(Refresh)없이도 서버로 요청을 수행하고
	  요청에 대한 응답을 처리할 수 있는 자바스크립트(jQuery) 라이브러리
	- 데이터베이스 등의 작업 요청 시 요청된 응답이 돌아올 때까지 기다리지 않고
	  다른 작업을 수행하면서 요청에 대한 응답이 돌아오면 해당 응답을 처리하는 기술
	- 주로, 아이디 및 패스워드 검증 등의 실시간 정보 조회 작업에 활용
	- jQuery 의 라이브러리로 제공
	
	< 기본 문법 >
	$.ajax({
		type: xxx,	// Ajax로 요청 시 사용할 메서드 (= 요청방식, GET or POST) 
		url: xxx,	// Ajax로 요청할 요청 주소(URL)
		data: xxx,	// 요청(전송할) 데이터 지정
		dataType xxx, // 응답 데이터에 대한 타입 지정(생략 text) 
		success: function(res){} // 요청에 대한 처리 성공시 처리할 함수(= 콜백함수)
		error: function(xhr, textStatus, errorThrown){}
		// 요청에 대한 처리 실패 시 처리할 함수 정의
	})
	
	*/ 
	function duplicateId() {
		$.ajax({
			type: "GET",
			url: "checkId",
			data: {
				id : $('#id').val()
			},
			success: function(res){
				$('#checkIdResult').text(res.msg);
				$('#checkIdResult').css('color', res.color);
			},
			error: function(xhr, textStatus, errorThrown){
			}
		})
	}
	// ----------------------------
	
	function checkPass() {
		let passwd = $('#passwd').val();
		
		let msg; // 판별 결과로 출력할 메세지
		let color;	// 판별 결과로 출력할 메세지의 색상
		
		// 패스워드 조합 및 길이 규칙: 영문자, 숫자, 특수문자(!@#$%) 8 ~ 16자
		let lengthRegex = /^[A-Za-z0-9!@#$%]{8,16}$/;
		
		if (lengthRegex.exec(passwd)) {
			
			let count = 0;	// 카운트 변수 선언
			let engUpperRegex = /[A-Z]/;
			let engLowerRegex = /[a-z]/;
//			let numRegex = /[0-9]/;
			let numRegex = /[\d]/;
			let specRegex = /[!@#$%]/;
			
			if (engUpperRegex.exec(passwd)) count++;
			if (engLowerRegex.exec(passwd)) count++;
			if (numRegex.exec(passwd)) 		count++;
			if (specRegex.exec(passwd)) 	count++;
			
			console.log(count);
			// 카운트 변수값에 따른 복잡도 검사 결과 출력
			// => 4가지 조합: 안전(파란색)
			// => 3가지 조합: 보통(초록색)
			// => 2가지 조합: 위험(주황색)
			// => 1가지 조합: 사용불가(빨간색)
			switch(count){
				case 4:
					msg = '안전';
					color = 'blue'; 
					checkPasswd = true;
					break;
				case 3: 
					msg = '보통';
					color = 'green';
					checkPasswd = true; 
					break;
				case 2: 
					msg = '위험';
					color = 'orange'; 
					checkPasswd = true;
					break;
				case 1: 
					msg = '사용불가';
					color = 'red'; 
					checkPasswd = false;
					break;
			}
			
		} else { // 일치하지 않을 경우
			msg = "영문자, 숫자, 특수문자(!@#$%) 8~16 필수!";
			color = "RED";
			checkPasswd = false;
		}
		
		$("#checkPasswdResult").text(msg).css('color', color);
		
		checkPass2(); // 비밀번호 확인 강제 호출!
		// 만약, 익명함수로 checkPass2에 이벤트를 연결해 둔 경우
		// 함수명이 없으므로 강제로 함수를 실행시킬 방법이 없음
		// 이때, 이벤트 트리거를 활용하여 특정 요소에 대한 이벤트 발생을 강제로 제어 가능
		// => $(선택자).trigger("이벤트명")
//		$('#passwd2').trigger('change');
		
	} // checkPass() 끝
	
	function checkPass2() { // 비밀번호 확인
		let passwd = $('#passwd').val();
		let passwd2 = $('#passwd2').val();
		
		if (passwd == passwd2) {
			// '비밀번호 일치', blue
			$('#checkPasswd2Result').text('비밀번호 일치').css('color', 'blue');
			checkPasswd2 = true;
		} else {
			// '비밀번호 불일치', red
			$('#checkPasswd2Result').text('비밀번호 불일치').css('color', 'red')
			checkPasswd2 = false;
		}
		
	} // chechPass2() 끝
	
	function checkSubmit() {
		
		console.log('submit 버튼 이벤트!!');
		
		console.log("비밀번호: " + checkPasswd);
		console.log("비밀번호확인: " + checkPasswd2);
		
		// TODO
//		if (!checkPasswd) {
//			alert("비밀번호를 확인하세요!");
//			$("#passwd").focus();
//			return false;
//		} 
//		
//		if (!checkPasswd2) {
//			alert("비밀번호 확인란을 확인하세요!");
//			$("#passwd2").focus();
//			return false;	
//		}
		
		let email = $('#email1').val() + '@' + $('#email2').val();
		$('input[name=email]').remove();		
		$('form').prepend('<input type="hidden" name="email" value="' + email +'">');
		
		return true;
	}
	
	
	
	
	
	// -----------------------------------------
	
		
});

