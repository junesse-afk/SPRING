package com.itwillbs.test1;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * 컨트롤러 역할(= 서블릿 클래스)을 수행할 스프링 클래스는 @Controller 어노테이션을 적용하여 정의
 * => @Controller 어노테이션이 적용된 클래스는 스프링이 관리하는 스프링 컨텍스트(Context)영역에
 *    스프링 빈(Bean)이라는 형태로 관리됨(별도의 서블릿 관련 설정 불필요하며, 모두 자동으로 설정 이루어짐)
 * => 해당 클래스 자체에 대한 서블릿 주소(URL)를 매핑하거나
 *    클래스 내의 각각의 메서드를 통해 개별적인 매핑 수행도 가능함   
 * */

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/*
	 * @RequestMapping 어노테이션을 적용하여 요청 URL에 대한 매핑 수행
	 * - 기본 문법: @RequestMapping(value = "매핑URL", method = RequestMethod.XXX)
	 * 			 public 리턴타입 메서드명([파라미터...]){}
	 *   => value 속성은 매핑에 사용될 매핑 URL을 문자열로 지정하고
	 *   	method 속성은 RequestMethod라는 enum 타입의 값으로 GET, POST, PUT, DELETE 등의 요청 메서드 형식 지정
	 *   => 어노테이션 아래에 선언된 메서드는 해당 매핑 주소가 요청되면 자동으로 호출됨
	 *      이때, 메서드명은 무관하며, 스프링에 의해 자동으로 호출되기 위한 메서드 형태
	 *      또한, 메서드 리턴타입은 변다른 지시 사항이 없을 경우 String 타입으로 지정
	 *      (메서드 내에서 return 문을 통해 포워딩 할 경로를 문자열로 지정하는 용도로 사용)
	 * */
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
//		return "home"; // webapp/WEB-INF/views/home.jsp 페이지로 포워딩(디스패치 방식)
		/*
		 * DispatcherServlet 객체가 관리하는 servlet-context.xml 파일에 기술된
		 * <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		 * 태그 부분의 "prefix" 항목의 value 속성값과 return 문 뒤에 기술된 문자열과
		 * "suffix" 항목의 value 속성값을 하나의 문자열로 결합하여
		 * 디스패치 방식으로 포워딩할 경로로 사용하게됨
		 * */
		
		return "index"; 
		
	} // home() 메서드 끝
	
	// ================================================================
	// "/test" 서블릿 주소가 GET 방식으로 요청될 경우 수행할 매핑(핸들러) 메서드 test() 정의
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		System.out.println("/test 요청!!!");
//		return "test";
		// 주의! /WEB-INF/views/test.jsp 페이지가 지정되므로 404에러 발생!
		// views 밑에 서브디렉토리(폴더)가 존재할 경우 해당 디렉토리명까지 모두 포함해야한다!
		return "test/test";
	}
	
	// ============================================================
	// "/BoardWrite" 서블릿 주소(GET)와 매핑되는 boardWrite() 메서드 정의
	@RequestMapping(value = "/BoardWrite", method = RequestMethod.GET)
	public String boardWrite() {
		System.out.println("/BoardWrite 요청!!!");
		return "redirect:/BoardList";
	}
	
	@RequestMapping(value = "/BoardList", method = RequestMethod.GET)
	public String boardList() {
		System.out.println("/BoardList 요청!!!");
		return "Board/BoardList";
	}
	
	
	
	
}
