package com.itwillbs.test3_mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.test3_mybatis.StudentDAO;
import com.itwillbs.test3_mybatis.StudentDTO;
import com.itwillbs.test3_mybatis.mapper.StudentMapper;
import com.itwillbs.test3_mybatis.vo.StudentVO;

/*
 * MVC 패턴에서 서비스 역할을 수행할 클래스 정의 시 @Service 어노테이션 지정
 * => @Service 어노테이션이 적용된 클래스도 스프링이 관리하는 빈으로 등록됨
 * => 컨트롤러 클래스에서 객체 생성없이 자동 주입(DI) 받을 수 있다! 
 * 
 * */
@Service
public class StudentService {
	
	/*
	 * [ Service 클래스 ]
	 * DB작업을 수행하기 위한 마이바티스 Mapper 객체 메서드를 호출하여 SQL 구문 실행 요청
	 * Mapper 역할을 수행하는 XXXMapper 인터페이스는 추상메서드만 존재하므로
	 * 인스턴스 생성이 불가능하며, 스프링의 DI를 통해 필요한 객체를 주입받아 사용해야함
	 * => XXXMapper 인터페이스 정의 후 Service 클래스에서 @Autowired 어노테이션 활용하여 DI 적용
	 * => 이때, Mapper 인터페이스 내에 @Mapper 어노테이션은 생략해도 됨 (단순 마커 역할)
	 * => 대신, MyBatis가 해당 Mapper 객체(스프링 빈) 관리하도록 root-context.xml 파일에서 설정 필수!
	 * 	 <mybatis-spring:scan base-package="com.itwillbs.test3_mybatis"/>
	 * 	 (이때, base-package 속성값은 프로젝트 기본 패키지 지정(해당 패키지 내의 모든 클래스 탐색) )
	 * */
	@Autowired
	StudentMapper mapper;
	
	// 학생 정보 등록 요청을 수행할 registStudent() 메서드
	public int registStudent(StudentVO student) {
		System.out.println("StudentService - registStudent() 메서드 호출됨!");
		System.out.println("StudentVO: " + student);
		
		// 기존 JSP 에서 사용하던 jdbcUtil, StudentDTO, StudentDAO 사용
//		StudentDTO dto = new StudentDTO(10, "김길동");
//		StudentDAO dao = new StudentDAO();
//		return dao.insert(dto);
		
		return mapper.registStudent(student);
	}
	
	public StudentVO getStudentInfo(String idx) {
		return mapper.getStudentInfo(idx);
	}
	
	public List<StudentVO> getStudentList() {
		return mapper.getStudentList();
	}
	
	
	
	
	
	
	
	

}
