package com.itwillbs.mvc_board.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itwillbs.mvc_board.vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/config/root-context.xml"})
public class MemberSendAuthMailTest {

	@Autowired
	private MailService mailService;
	
	@Test
	public void test() {
		MemberVO vo = new MemberVO();
		vo.setEmail("dongwon_cha@naver.com");
		mailService.sendAuthMail(vo);
	}

}
