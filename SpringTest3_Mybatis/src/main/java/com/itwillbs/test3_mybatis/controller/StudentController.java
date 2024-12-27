package com.itwillbs.test3_mybatis.controller;

import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.log.UserDataHelper.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.test3_mybatis.service.StudentService;
import com.itwillbs.test3_mybatis.vo.StudentVO;

@Controller
public class StudentController {

	@Autowired
	StudentService service;
	
	@GetMapping("registStudent")
	public String registStudentForm() {
		return "student/student_regist_form";
	}
	
	@PostMapping("registStudent")
	public String registStudent(StudentVO student) {
		// 메서드 파라미터를 활용한 경우 (변수명이 화면의 name 속성값과 같아야함)
//		System.out.println("번호: " + idx);
//		System.out.println("이름: " + name);
		
		// map 활용한 경우 (키값이 화면의 name 속성값과 일치)
//		System.out.println("번호: " + map.get("idx"));
//		System.out.println("이름: " + map.get("name"));

		// DTO(= VO) 활용한 경우 (클래스의 멤버변수와 화면의 name 속성값 일치)
		System.out.println("번호: " + student.getIdx());
		System.out.println("이름: " + student.getName());
		
		int registCount = service.registStudent(student);
		System.out.println("INSERT 작업결과: " + registCount);
		
		return "redirect:/registSuccess";
	}
	@GetMapping("registSuccess")
	public String registSuccess() {
		return "student/student_regist_success";
	}
	
	@GetMapping("studentInfo")
	public String studentInfo(String idx, Model model) {
		System.out.println("학생정보(idx): " + idx);
		
		StudentVO student = service.getStudentInfo(idx);
		System.out.println(student);
		model.addAttribute("student", student);
		
		return "student/student_info";
	}
	
	@GetMapping("studentList")
	public String studentList(Model model) {
		
		List<StudentVO> studentList = service.getStudentList();
		System.out.println(studentList);
		
		model.addAttribute("studentList", studentList);
		
		return "student/student_list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
