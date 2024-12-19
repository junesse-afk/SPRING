package com.itwillbs.test2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

//Lombok 사용 시 클래스 선언부 상단에 어노테이션을 통해 클래스 내의 요소 자동 생성 가능(순서 무관)
//--------------------------------------------------------------------------
//@NoArgsConstructor // 기본 생성자(파라미터가 하나도 없는)를 자동으로 정의하는 어노테이션
//@AllArgsConstructor // 모든 파라미터를 전달받는 파라미터 생성자를 자동으로 정의하는 어노테이션
//@RequiredArgsConstructor // 특정 파라미터를 전달받는 파라미터 생성자를 자동으로 정의하는 어노테이션
//=> 단, 필수로 전달받을 파라미터에 @NonNull 어노테이션을 지정해야하며
// @NonNull 어노테이션이 지정된 멤버변수가 하나도 없을 경우 기본 생성자와 동일하기 때문에
// @NoArgsContructor 와 충돌이 일어난다! 
//@Getter // 모든 멤버변수에 대한 Getter 메서드를 자동으로 정의하는 어노테이션
//@Setter // 모든 멤버변수에 대한 Setter 메서드를 자동으로 정의하는 어노테이션
//@ToString // toString() 메서드를 자동으로 정의하는 어노테이션
//---------------------------------------------------------------------------
@Data	// @Getter @Setter @ToString @RequiredArgsConstructor @EqualsAndHashCode 포함하는 어노테이션
@AllArgsConstructor
public class PersonVO {
	private String name;
	private int age;
	private String gender;
}
