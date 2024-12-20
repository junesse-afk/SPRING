package com.itwillbs.test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwillbs.test2.vo.Test6Component;

@Controller
public class Test6Controller_Component {
	
	/*
	 *  A클래스가 B클래스에 의존적일 때 B 클래스에 대한 인스턴스를 직접 생성하지 않고.
	 *  의존성 자동 주입(DI = Dependency Injection) 기능을 통해 인스턴스를 전달받아 사용
	 *  
	 * [ 의존성 주입을 받는 방법 3가지(+1) ]
	 * 
	 * 0) 매핑 메서드 파라미터로 주입받을 클래스 타입 매개변수를 선언하는 방법
	 * ---------------------------------------------
	 * 만약, 멤버변수 형태로 주입받을 클래스 타입 변수를 선언하여 메서드마다 공유하는 경우
	 * => 해당 클래스 정의 시 @Component, @Service 등의 어노테이션을 통해
	 *    스프링 빈으로 등록 필수!
	 * => 어노테이션 미지정 시 자동 주입 불가능한 상태로 오류 발생함
	 * 1) 생성자를 통해 의존성 객체를 주입받는 방법
	 * 2) Setter 메서드를 통해 의존성 객체를 주입받는 방법
	 * 3) 멤버변수 선언 시 의존성 객체를 주입받는 방법
	 * */
	
	//자동 주입(DI) 기능을 활용하지 않고 Test6Component 인스턴스 활용
//	@GetMapping("component")
//	public String component() {
//		
//	Test6Component comp = new Test6Component(); //자동주입(DI) 아님!
//	comp.componentMethod();
//	
//	/*
//	 *  [ 콘솔 출력 순서 ]
//	 *  1. component()
//	 *  2. 기본생성자
//	 *  3. componentMethod()
//	 * 
//	 */
//		return "test2/component";
//	}
	
	//0번방법) 매핑 메서드 파라미터로 주입받을 클래스 타입 매개변수를 선언하는 방법
//	@GetMapping("component")
//	public String component(Test6Component component) {
//		System.out.println("component() 메서드 호출됨");
//		component.componentMethod();
//		/*
//		 *  [ 콘솔 출력 순서 ]
//		 *  1. 기본생성자
//		 *  2. component()
//		 *  3. componentMethod()
//		 * 
//		 */	
//		return "test2/component";
//	}
	
	//1번방법) 생성자를 통해 의존성 객체를 주입받는 방법
	//1) 의존성을 제공하는 B클래스 타입의 멤버변수 선언
//	private Test6Component comp;
//	//=> 서버가 시작될 때 현재 컨트롤러 클래스 인스턴스가 생성되고
//	//   그 시점에 Test6Component 멤버변수 생성을 필요로 하므로
//	//   어노테이션이 붙은 스프링 빈에서 해당 클래스를 찾아 객체를 생성 후 자동 주입
//	
//	
//	//2) 선언된 멤버변수를 파라미터로 전달받는 생성자 정의
//	public Test6Controller_Component(Test6Component comp) {
//		System.out.println("Test6Controller_Component() 생성자 호출됨");
//		//=> 주의! 이 출력문은 매핑 메서드 호출 시점이 아닌 서버가 시작될 때 출력되므로
//		//   DispatcherServlet 로그 메세지 근처에서 확인!
//		this.comp=comp;
//	
//	}
	
	//2번방법) Setter 메서드를 통해 의존성 객체를 주입받는 방법
//	private Test6Component comp;
//	
//	//생성자와 달리 Setter 메서드는 필수 호출 요소가 아니므로
//	//@Autowired 어노테이션을 지정하여 객체가 자동으로 로딩되도록 해야한다!
//	@Autowired
//	public void setComp(Test6Component comp) {
//		System.out.println("SetComponent() 메서드 호출됨!");
//		this.comp=comp;
//	
//	}
	
	//***3번방법) 멤버변수 선언 시 의존성 객체를 주입받는 방법 (멤버변수에 @Autowired)
	//=> 멤버변수 자체에 @Autowired 어노테이션 필수!
	// => 스프링 4.3버전 이전 또는 스프링이 아닌 자바에서는 @Inject 어노테이션 사용
	//    (대신, 별도의 라이브러리(javax.inject) 추가 필요하지만,
	//    스프링은 MVC는 기본적으로 추가되어 있음)
	
	@Autowired
	private Test6Component comp;
	
	@GetMapping("component")
	public String component() {
		// => 매핑 메서드 내의 코드 실행 전 먼저 스프링에 의해 객체가 자동 주입
		// => 즉, 메서드 호출 시점에 이미 인스턴스가 생성되어 주입되어있음
				
		
		System.out.println("component() 메서드 호출됨!");
		comp.componentMethod();
		return "test2/component";
	}
	
}
