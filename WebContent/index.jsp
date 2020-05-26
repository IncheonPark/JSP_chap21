<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원제 게시판 예제 홈 화면</title>
</head>
<body>
	<h3>여기는 홈 화면입니다.</h3>
	<!-- jstl 코어 테스트 사용
	<c:if test="${!empty authUser }">
		${authUser.name }님 안녕하세요.
		<a href="logout.do">[로그아웃 하기]</a>
		<a href="changePwd.do">[암호 변경 하기]</a>
	</c:if>
	<c:if test="${empty authUser }">
		<a href="join.do">[회원 가입 하기]</a>
		<a href="login.do">[로그인 하기]</a>
	</c:if> 
	-->
	
	<!-- 유저 커스텀 태그 사용 -->
	<u:isLogin>
		UCT: ${authUser.name }님 안녕하세요.
		<a href="logout.do">[로그아웃 하기]</a>
		<a href="changePwd.do">[암호 변경 하기]</a>
		<br/>
		<a href = "${pageContext.request.contextPath }/article/write.do">[게시글 작성 하기]</a>
		<a href = "${pageContext.request.contextPath }/article/list.do">[게시글 목록 보기]</a>
	</u:isLogin>
	<u:notLogin>
		UCT: <a href="join.do">[회원 가입 하기]</a>
		<a href="login.do">[로그인 하기]</a>
		<a href = "${pageContext.request.contextPath }/article/list.do">[게시글 목록 보기]</a>
	</u:notLogin>
	<br/>
</body>
</html>