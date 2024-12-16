package com.itwillbs.test2.vo;

//xxxVO (VO = Value Object, 값을 저장하는 용도의 객체)
// = xxxVO = xxxBean

public class TestVO {

	private String subject;
	private String content;
	
	public TestVO() {	
	}
	
	public TestVO (String subject, String content){
		this.subject=subject;
		this.content=content;
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
