package com.itwillbs.test2.vo;

public class PersonVO_Backup {
	private String name;
	private int age;
	private String gender;
	
	/*
	 * 스프링에 의해 객체가 자동 주입될 때 파라미터 데이터를 저장하기 위해 객체 생성되는 과정에서
	 * 기본 생성자가 있으면 "무조건" 기본 생성자를 호출하고, Setter 메서드를 통해 데이터 저장
	 * 만약, 기본 생성자 없이 파라미터 생성자만 존재할 경우 파라미터 생성자를 호출하고(데이터 저장)
	 * Setter 메서드를 통해 다시 데이터를 저장
	 * */
	public PersonVO_Backup() {
		System.out.println("기본 생성자 호출됨!!");
		System.out.println("name: " + name);
		System.out.println("age: " + age);
		System.out.println("gender: " + gender);
	}
	public PersonVO_Backup(String name, int age, String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		System.out.println("파라미터(String int String) 생성자 호출됨!!");
		System.out.println("name: " + name);
		System.out.println("age: " + age);
		System.out.println("gender: " + gender);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("setName() 호출됨!");
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		System.out.println("setAge() 호출됨!");
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		System.out.println("setGender() 호출됨!");
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "PersonVO [name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}
	
}
