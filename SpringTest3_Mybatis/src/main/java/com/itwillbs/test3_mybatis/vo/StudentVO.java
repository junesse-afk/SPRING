package com.itwillbs.test3_mybatis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // @Getter @Setter @RequiredArgsConstructor @ToString 등
@AllArgsConstructor // 파라미터 생성자 추가(단, 기본생성자 필요에 따라 @NoArgsConstructor)
public class StudentVO {
	// 멤버변수 선언(=컬럼명)
	private int idx;
	private String name;
	
}
