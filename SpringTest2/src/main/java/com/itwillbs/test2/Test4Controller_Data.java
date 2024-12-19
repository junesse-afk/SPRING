package com.itwillbs.test2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.test2.vo.PersonVO;

@Controller
public class Test4Controller_Data {

	@GetMapping("data")
	public String data() {
		return "test2/data";
	}
	// =======================================================
	// [ 컨트롤러에서 요청 처리 과정에서 요청 파라미터에 접근하는 방법 ]
	// 1) HttpServletRequest 객체 활용(기존 방법 = 스프링에서 사용 X)
	//   => 매핑 메서드 파라미터로 HttpServletRequest 타입 선언하여 객체 자동 주입 후
	//     매핑 메서드 내에서 request.getParameter() 메서드로 파라미터 데이터 접근
	
//	@GetMapping("data_process1")
//	public String data_process1(HttpServletRequest request) {
//		
//		String name = request.getParameter("name");
//		String age = request.getParameter("age");
//		String gender = request.getParameter("gender");
//		String hiddenValue = request.getParameter("hiddenValue");
//		
//		System.out.println("name 파라미터값: " + name);
//		System.out.println("age 파라미터값: " + age);
//		System.out.println("gender 파라미터값: " + gender);
//		System.out.println("hiddenValue 파라미터값: " + hiddenValue);
//		
//		return "test2/data_result1";
//	}
	// --------------------------------------------------------------
	// 2) 전달받은 파라미터명과 동일한 이름의 파라미터를 매핑 메서드에 선언
	// => 폼 등의 파라미터 데이터 전달 시 이름이 동일한 파라미터 변수에 자동으로 데이터가 저장됨
	//    (GET 방식 및 POST 방식 등 요청 메서드와 무관)
	// => int 타입 등의 파라미터 변수의 경우 해당 타입으로 형변환까지 자동으로 수행됨
	//    (단, 변환이 불가능한 데이터 전달 시에는 변환 과정에서 예외가 발생함!)
//	@GetMapping("data_process1")
//	public String data_process1(String name, int age, String gender, String hiddenValue) {
//		// 전달된 파라미터가 "메서드에 선언된 변수명과 동일할 경우" 자동으로 데이터가 저장됨
//		System.out.println("name 파라미터값: " + name);
//		System.out.println("age 파라미터값: " + age);
//		System.out.println("gender 파라미터값: " + gender);
//		System.out.println("hiddenValue 파라미터값: " + hiddenValue);
//		
//		return "test2/data_result1";
//	}
	// --------------------------------------------------------------
	// 3) 전달받은 파라미터명과 동일한 이름의 멤버변수를 갖는 VO(= DTO) 클래스를 정의하고
	//    매핑 메서드 파라미터로 해당 VO 클래스타입 변수 선언
//	@GetMapping("data_process1")
//	public String data_process1(PersonVO person, String hiddenValue) {
//		
//		// 전달된 파라미터가 "메서드에 선언된 변수명과 동일할 경우" 자동으로 데이터가 저장됨
//		System.out.println(person);
//		System.out.println("name 파라미터값: " + person.getName());
//		System.out.println("age 파라미터값: " + person.getAge());
//		System.out.println("gender 파라미터값: " + person.getGender());
//		
//		// VO 클래스에 없는 파라미터는 별도의 파라마터 변수 선언을 통해서도 전달받을 수 있다!
//		System.out.println("hiddenValue 파라미터값: " + hiddenValue);
//		
//		return "test2/data_result1";
//	}
	
	// --------------------------------------------------------------
	// 4) 자바 컬렉션 중 Map 타입 파라미터 선언하는 방법
	//  => Map 타입은 데이터를 키(key), 값(value) 한 쌍으로 데이터를 관리하는 자료구조
	//  => Map 타입 객체를 자동 주입받아 각 파라미터 데이터를 자동으로 저장 가능
	//	   (파라미터명을 key, 파라미터값을 value로 관리)
	//	   (VO 클래스를 정의하여 사용하는 경우에 비해 훨씬 유연한 데이터 관리가 가능 - 데이터 제한 없음)
	//	=> 단, Map 타입 파라미터를 매핑 메서드에 선언 시 단순 Map 객체만 자동 주입되고
	//    파라미터 데이터가 자동 저장되지 않는다! (파라미터를 저장할 key가 존재하지 않기 때문)
	//    따라서, Map 타입 파라미터 선언부 앞에 @RequestParam 어노테이션을 명시하여
	//    해당 Map 객체 자동 주입 용도가 파라미터 저장용임을 명시해야한다!
	@GetMapping("data_process1")
	public String data_process1(@RequestParam Map<String, String> map) {
		
		System.out.println(map);
		System.out.println(map.get("name"));
		System.out.println(map.get("age"));
		System.out.println(map.get("gender"));
		System.out.println(map.get("nickname"));
		System.out.println(map.get("hiddenValue"));
		
		return "test2/data_result1";
	}
	
	// ==================================================================
	// POST 방식 요청으로 전달받은 파라미터 처리도 동일한 방법으로 수행
	// => 주의! POST 방식 요청 파라미터 중 한글 파라미터 데이터가 존재할 경우
	//    Tomcat 9.x 버전 이하에서는 한글이 깨지는 현상이 발생함! (web.xml 에서 인코딩 필터 설정 필수!)
	@PostMapping("data_process2")
	public String data_process2(@RequestParam Map<String, String> map) {
		
		System.out.println(map);
		System.out.println(map.get("name"));
		System.out.println(map.get("age"));
		System.out.println(map.get("gender"));
		System.out.println(map.get("nickname"));
		System.out.println(map.get("hiddenValue"));
		
		return "test2/data_result1";
	}
	
	
	
	
	
	
	
	
	
	
	
}
