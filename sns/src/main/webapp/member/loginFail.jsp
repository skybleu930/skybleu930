<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.onload = function () { 
		alert('아이디 또는 비밀번호를 잘못 입력하였습니다.');
		var form = document.getElementById('loginFail_form');
		form.action = "loginForm.do"
		form.submit();		
	};
</script>
</head>
<body>
	<form id="loginFail_form" method="post"></form>
</body>
</html>