package com.itwillbs.test3_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.test3_mybatis.vo.StudentVO;

/*
 * 마이바티스 연동 시 DAO 클래스 대신 활용 가능한 Mapper 인터페이스 정의
 * => 마이바티스를 통해 Mapper XML 파일과 연결되어 자동으로 XML 파일의 SQL구문 실행
 * => 주의! 인터페이스 내에 정의하는 추상메서드의 이름은 Mapper XML 파의 각 태그에 지정된
 *   id 속성값과 동일하게 정의해야한다! (마이바티스에 의해 자동으로 일치하는 태그의 SQL 구문 실행)
 * => Mapper 인터페이스에 대한 객체 자동 주입 기능을 활용하기 위해 @Mapper 어노테이션 적용하여
 *   스프링 빈으로 등록 후 Service 클래스에서 활용 가능 (@Mapper 어노테이션 생략 가능 - 단순 마커용)
 * */
@Mapper
public interface StudentMapper {

	// 학생 정보 등록
	int registStudent(StudentVO student);
	// => StudentMapper.xml 파일의 id 속성값이 "registStudent"인 태그 찾아서 SQL구문 실행
	
	// 학생 상세정보 조회
	StudentVO getStudentInfo(String idx);
	
	// 학생 정보 조회
	List<StudentVO> getStudentList();
}
