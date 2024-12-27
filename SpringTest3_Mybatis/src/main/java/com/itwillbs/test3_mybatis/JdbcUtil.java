package com.itwillbs.test3_mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 데이터베이스 연동을 통한 DB작업 준비 및 작업 완료 후 자원 해제(= 자원 반환)작업을
// 공통으로 수행할 공통 메서드를 갖는 JdbcUtil 클래스 정의
// => DB 작업 준비 및 마무리 코드들의 모듈화(DB 작업 코드들의 중복 제거 수단)
public class JdbcUtil {
	
	public static Connection getConnection() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공!");
			
			String url = "jdbc:mysql://localhost:3306/STUDY_JSP";
			String user = "root";
			String passwd = "1234";
			con = DriverManager.getConnection(url, user, passwd);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패!");
		} catch (SQLException e) {
			System.out.println("DB 접속 실패!");
		}
		
		return con;
	} // getConnection() 끝
	
	// =============================================
	// 데이터베이스 자원 반환(해제)작업을 공통으로 수행할 close() 메서드 정의
	// => 클래스명만으로 호출 가능하도록 static 메서드로 선언
	// => 파라미터: Connection객체(con), PreparedStatement객체(pstmt)
	//    리턴타입: void
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}













