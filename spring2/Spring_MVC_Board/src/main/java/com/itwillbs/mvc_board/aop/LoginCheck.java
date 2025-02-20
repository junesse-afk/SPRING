package com.itwillbs.mvc_board.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//커스텀 어노테이션 작성을 위해 New - Annotation 메뉴를 클릭하여 어노테이션 생성
// => interface 키워드 앞에 @기호가 붙은 상태로 인터페이스 형태로 정의됨
// (상수 및 추상 메서드 포함할 수 있다!)

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {
//인터페이스 내부에 상수(enum도 포함) 및 추상메서드만 포함 가능
	public static enum MemberRole{
		ADMIN, USER
	}
	//public MemberRole memberRole();
	public MemberRole memberRole() default MemberRole.USER;
	// => 추상 메서드 리턴 타입으로 지정한 타입에 대한 값은
	// @LoginCheck 어노테이션 지정 시 @LoginCheck(memberRole = 값) 형태로 지정해야함
	// => 따라서, 이 추상메서드가 정의될 경우 MemberRole 이라는 enum도 함께 정의해야함
}
