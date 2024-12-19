package com.itwillbs.test2.vo;

// XXXVO (VO = Value Object, 값을 저장하는 용도의 객체)
// = XXXDTO = XXXBean
public class TestVO {
	private String subject;
	private String content;
	
	// 생성자(기본, 파라미터), Getter/Setter, toString() 정의
	public TestVO() {}
	public TestVO(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "TestVO [subject=" + subject + ", content=" + content + "]";
	}
}
