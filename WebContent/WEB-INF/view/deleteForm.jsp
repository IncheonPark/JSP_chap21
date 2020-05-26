<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
</head>
<body>
	<form action="delete.do" method="post">
		<p>
			<input type="hidden" name="no" value="${delReq.articleNumber }" readonly>게시물을 지우시겠습니까?
		</p>
		<input type="submit" value="글 지우기">
	</form>
</body>
</html>