<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>JSPWriter : 버퍼관리</h1>
1. 버퍼 크기 : <%= out.getBufferSize() %>
2. 남아있는 버퍼 : <%= out.getRemaining() %>
3. 사용중인 버퍼 : <%= out.getBufferSize() - out.getRemaining() %>
4. 사용처 : 복잡한 HTML이 있는 경우에 사용
</body>
</html>