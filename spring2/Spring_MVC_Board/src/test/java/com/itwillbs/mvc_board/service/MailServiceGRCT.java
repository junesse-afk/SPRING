package com.itwillbs.mvc_board.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itwillbs.mvc_board.handler.GenerateRandomCode;

//JUnit 테스트를 수행할 클래스 정의
//@RunWith 어노테이션을 사용하여 테스트에 사용할 스프링 빈을 자동 주입하는 클래스 파일 지정
//=>spring-test 라이브러리 필수! (pom.xml에서 등록)
@RunWith(SpringJUnit4ClassRunner.class)

//현재 클래스와 동일한 위치에 root-context.xml 파일도 복사되어 있어야함
//@ContextConfiguration(locations = {"root-context.xml"})

//classpath:/ 경로는 src/main/resources 와 함께 src/test/resources도 포함
//@ContextConfiguration(locations = {"classpath:/config/root-context.xml"})

@ContextConfiguration(locations = {"file:src/main/resources/config/root-context.xml"})
public class MailServiceGRCT {
	
	//테스트를 수행할 메서드 상단에 @Test 어노테이션을 붙이면 테스트가 실행될 때 자동으로 호출됨
	@Test
	public void testSendAuthMail() {
		for(int i = 0; i<10; i++) {
			System.out.println(GenerateRandomCode.getRandomCode(5));
		}
	}

}
