package com.itwillbs.test2.vo;

import org.springframework.stereotype.Component;

@Component
public class Test6Component {
	
	public Test6Component() {
		System.out.println("test6COMP 기본생성자 호출됨");
	}
	
	public void componentMethod() {
		System.out.println("compMethod() 메서드 호출됨");
	}

}
