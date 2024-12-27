package com.itwillbs.test3_mybatis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data	// @Getter @Setter @RequiredArgsConstructor @ToString 등
@AllArgsConstructor	// 파라미터 생성자 추가(단, 기본생성자 필요에따라 @NoArgsConstructor)
public class StudentVO {
	private int idx;
	private String name;
}
