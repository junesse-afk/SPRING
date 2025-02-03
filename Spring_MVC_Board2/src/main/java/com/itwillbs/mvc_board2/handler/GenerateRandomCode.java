package com.itwillbs.mvc_board2.handler;

import java.util.Random;

// 특정 길이의 난수 생성에 사용할 GenerateRandomCode 클래스 정의

public class GenerateRandomCode {
	
	public static String getRandomCode(int length) {
		// [ 난수 생성 방법 ]
		// 1. java.util.Random 클래스 활용
		// (또는 java.lang.Math 클래스의 random() 메서드)
		Random r = new Random();
//		int rNum = r.nextInt(10000);	// 0 ~ 9999
//		return String.valueOf(rNum);
		
		// 파라미터로 전달받은 length 값을 자릿수로 활용
		// 10 ^ length 형태로 연산을 통해 출력할 자릿수 지정
//		(int)Math.pow(10, 4) -> 10000
		
		int rNum = r.nextInt((int)Math.pow(10, length));
		return String.valueOf(rNum);
	}
}
