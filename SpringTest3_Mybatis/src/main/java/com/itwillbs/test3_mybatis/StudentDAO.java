package com.itwillbs.test3_mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

	// insert
//	public int insert(int idx, String name) {
	public int insert(StudentDTO dto) {
		
		System.out.println("전달받은 데이터: " + dto);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int insertCnt = 0;
		try {
			con = JdbcUtil.getConnection();
			
			String sql = "INSERT INTO STUDENT VALUES (?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getIdx());
			pstmt.setString(2, dto.getName());
			
			System.out.println("pstmt = " + pstmt);
			insertCnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
		} finally {
			JdbcUtil.close(con);
			JdbcUtil.close(pstmt);
		}
		
		return insertCnt;
	} // insert() 메서드 끝
	
	public int update(StudentDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateCnt = 0;
		
		try {
			con = JdbcUtil.getConnection();
			
			String sql = "UPDATE STUDENT SET NAME = ? WHERE IDX = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getIdx());
			System.out.println("pstmt = " + pstmt);
			
			updateCnt = pstmt.executeUpdate();
			System.out.println("UPDATE 구문 실행 결과: " + updateCnt);
			
		} catch (SQLException e) {
			System.out.println("DB 연결 실패!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(con);
			JdbcUtil.close(pstmt);
		}
		
		return updateCnt;
	}
	
	// delete
	public int delete(int idx) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int deleteCnt = 0;
		
		try {
			con = JdbcUtil.getConnection();
			
			String sql = "DELETE FROM STUDENT WHERE IDX = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			System.out.println("pstmt = " + pstmt);
			
			deleteCnt = pstmt.executeUpdate();
			System.out.println("DELETE 구문 실행 결과: " + deleteCnt);
			
		} catch (SQLException e) {
			System.out.println("DB 연결 실패!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(con);
			JdbcUtil.close(pstmt);
		}
		
		return deleteCnt;
	} // delete() 메서드 끝
	
	public void select(int idx) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JdbcUtil.getConnection();
			
			// SQL 구문 작성 및 전달
			String sql = "SELECT NAME AS NM, IDX FROM STUDENT WHERE idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			System.out.println("pstmt = " + pstmt);
			
			// ----------------------------------------
			// SELECT 구문은 다른 작업들과 완전 달라짐
		 	rs = pstmt.executeQuery();
		 	// 조회 성공/실패 여부와 관계없이 ResultSet 객체는 생성됨(null 없음)
//		 	System.out.println(rs);
		 	
			/*
			 * SELECT 구문 실행 성공 시 조회된 테이블이 ResultSet 객체에 저장되므로
			 * ResultSet 객체의 next() 메서드 호출하여 커서를 다음 레코드(row)로 이동시킴
			 * => 이때, next() 메서드 리턴타입이 boolean 이므로 판별을 통해
			 * 다음 레코드(row) 존재 여부 확인 가능!
			 * 
			 * +-----+-------+
			 * | idx | name  | <-- 현재 커서 위치(항상 맨 처음 커서 위치는 첫번째 레코드보다 위)
			 * +-----+-------+
			 * |1 	 | 홍길동	 | <-- rs.next() 한 번 호출 시 커서 위치 (true 리턴)
			 * |2	 | 이순신	 | <-- rs.next() 두 번 호출 시 커서 위치 (true 리턴)
			 * |3	 | 강감찬	 | <-- rs.next() 세 번 호출 시 커서 위치 (true 리턴)
			 * +-----+-------+ <-- rx.next() 네 번 호출 시 커서 위치 (false 리턴)
			 * 
			 * */
//		 	System.out.println("첫번째 row가 존재하는가? " + rs.next());
//		 	System.out.println("컬럼 인덱스로 첫번째 row의 첫번째 컬럼에 접근: " + rs.getInt(1));
//		 	System.out.println("컬럼 인덱스로 첫번째 row의 두번째 컬럼에 접근: " + rs.getString(2));
//		 	
//		 	System.out.println("두번째 row가 존재하는가? " + rs.next());
//		 	System.out.println("컬럼 인덱스로 첫번째 row의 첫번째 컬럼에 접근: " + rs.getInt(1));
//		 	System.out.println("컬럼 인덱스로 첫번째 row의 두번째 컬럼에 접근: " + rs.getString(2));
		 	
//		 	if (rs.next()) { // 데이터 있음
//		 		System.out.println("데이터 있음!");
//		 		System.out.println("idx 컬럼 데이터: " + rs.getInt("IDX"));
//		 		System.out.println("name 컬럼 데이터: " + rs.getString("NM"));
//		 	} else { // 데이터 없음
//		 		System.out.println("데이터 없음!");
//		 	}
		 	
		 	// 조회 결과가 다중 row(복수개)일 수도 있는 경우: while문 활용
		 	// => if문 대신 while문을 사용하여 ["다음 row가 존재할 동안" 반복]
		 	while (rs.next()) {
		 		// 데이터가 존재할 동안 반복~~~
		 		System.out.println("idx 컬럼 데이터: " + rs.getInt("IDX"));
		 		System.out.println("name 컬럼 데이터: " + rs.getString("NM"));
		 	}
		 	
		} catch (SQLException e) {
			
		} finally {
			JdbcUtil.close(con);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
	}
	
	// 학생 번호를 전달받아 학생정보(StudentDTO)를 리턴하는 select2() 메서드 정의
	public StudentDTO select2 (int idx) {
		// DB 작업에 필요한 객체들 로컬 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StudentDTO student = null;
		
		con = JdbcUtil.getConnection();
		try {
			String sql = "SELECT * FROM STUDENT WHERE IDX = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int dbIdx = rs.getInt("IDX");
				String dbName = rs.getString("NAME");
				student = new StudentDTO(dbIdx, dbName);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
		} finally {
			JdbcUtil.close(con);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		
		return student;
	} // select2() 메서드 끝
	
	public List<StudentDTO> select3() {
		// DB 작업에 필요한 객체들 로컬 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<StudentDTO> studentList = null;
		
		con = JdbcUtil.getConnection();
		try {
		
			String sql = "SELECT * FROM STUDENT";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			studentList = new ArrayList<StudentDTO>();
			while (rs.next()) {
				int idx = rs.getInt("IDX");
				String name = rs.getString("NAME");
				StudentDTO student = new StudentDTO(); 
				student.setIdx(idx);
				student.setName(name);
				// => row한건에 대한 정보 student에 저장
				studentList.add(student); // list에 학생한명 정보 저장
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류!");
		}
		
		return studentList;
		
	}
	
	
	
	
	
	
	
	
	
	
}
