<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="top_menu">
	<h4>
		<a href="${pageContext.request.contextPath }">HOME</a>
		
		<c:choose>
			<c:when test="${empty sessionScope.sId}">
				<a href="">로그인</a>
				<a href="">회원가입</a>
			</c:when>
			<c:otherwise>
				<a href="">${sessionScope.sId}</a>님 |
				<a href="javascript:void(0)" onclick="logout()">로그아웃</a>
			</c:otherwise>
		</c:choose>
	</h4>
</div>
<div id="main_menu">
	<a href="${pageContext.request.contextPath }">홈</a>
	<a href="main">메인페이지</a>
	<a href="push">데이터전달</a>
	<a href="redirect">리다이렉트</a>
	<a href="data">데이터처리</a>
	<a href="mav">Model&amp;View</a>
	<a href="component">컴포넌트</a>
</div>
<hr>











