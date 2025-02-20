package com.itwillbs.mvc_board.handler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 실제 인터셉터 기능을 처리할 클래스 정의 => HandlerInterceptor 인터페이스 구현체로 정의
// => servlet-context.xml에 스프링 빈으로 등록 필수!
public class LoginIntercepter implements HandlerInterceptor{

	// HandlerInterceptor 인터페이스의 추상메서드 오버라이딩
	// -preHandle(), postHandle(), afterCompletion() 메서드 중 필요한 메서드 오버라이딩
	// Controller에 특정 메서드의 return문을 만나지 않으면 실행되지 않음
	// => 예외(Exception) 등으로 인해 return문이 실행되지 않을 경우 호출x
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(">???????????< preHandle()");

		//---------------------------------------------
		// 세션 객체를 통해 세션 아이디가 존재하지 않을 겨웅 fail.jsp 페이지로 포워딩 처리하고
		// 아니면, 기존 요청대로 글쓰기 폼 페이지로 포워딩 처리 (BoardController에 이미 기술되어 있음)
		//---------------------------------------------
		// HttpSession 객체 얻기
		// 주의! 현재 오버라이딩 된 메서드에 HttpSession 타입 파라미터 추가가 불가능하므로
		// HttpServletRequest 객체로부터 얻어야함!
		HttpSession session = request.getSession();
		
		//HttpSession 객체에서 로그인 세션 아이디(sId) 꺼내서 null값인지 판별
		String id = (String)session.getAttribute("sId");
		
		if (id == null) {
			
			//result/fail.jsp 펭지로 전달할 파라미터 값을 Model 객체에 저장했었지만
			//현재 메서드 내에서는 Model 객체가 없고 대신 HttpServletRequest 객체가 존재하므로
			//request 객체에 직접 데이터 저장해도 동일함!
			//=>model.addAttribute() 대신 request.setAtrribute() 메서드 사용
			
			request.setAttribute("msg", "로그인 필수!\\n 로그인 페이지로 이동합니다! interceptor");
			request.setAttribute("url", "MemberLogin");
//			return "result/fail"
					//기존 컨트롤러에서 뷰페이지로 포워딩을 위해서는
					//메서드 리턴타입 String 지정하고 return 문에 뷰페이지명을 기술하여 포워딩 수행했으나
					//인터셉터의 preHandle() 메서드는 Controller로 요청이 전달되기 전이며
					//컨트롤러의 메서드처럼 ViewSolver 를 통해 뷰페이지 포워딩 처리 설정이 불가능하다!
			//--------------------------------------------------------------------------
			// 인터셉터 내의 preHandle() 메서드 리턴타입이 boolean인 이유는
			// preHandle() 메서드에서 작업 처리 후 컨트롤러로 요청을 전달할지 여부를 결정하는 용도
			// => true 리턴 시 요청이 컨트롤러로 전달되고, false 리턴 시 컨트롤러로 전달되지 않음
			//--------------------------------------------------------------------------
			
//			RequestDispatcher dis = request.getRequestDispatcher("이동할페이지");
//			dis.forward(request, response);
			
			request.getRequestDispatcher("/WEB-INF/views/result/fail.jsp").forward(request, response);
			// 주의! 이동할 뷰페이지 지정시 ViewResolver의 prefix, suffix 값을 활용하지 못하드
			// 뷰페이지 경로를 webapp 디렉토리 뒤의 경로를 모두 기술!
			
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println(">???????????< postHandle()");
	}
	// 요청이 모두 완료된 후 예외 상관없이 실행됨
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println(">???????????< afterCompletion()");
	}

	
}
