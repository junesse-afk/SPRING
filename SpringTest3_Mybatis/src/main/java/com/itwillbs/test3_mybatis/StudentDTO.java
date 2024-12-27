package com.itwillbs.test3_mybatis;

// STUDENT 테이블에 대응하는 StudentDTO 클래스 정의
public class StudentDTO {
	// 멤버변수 선언 (= 컬럼명)
	private int idx;
	private String name;
	
	public StudentDTO() {}
	public StudentDTO(int idx, String name) {
		this.idx = idx;
		this.name = name;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "StudentDTO [idx=" + idx + ", name=" + name + "]";
	}
	
}
