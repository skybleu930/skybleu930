<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@page isELIgnored="false"%>  
<%@ include file="../header.jsp" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<article id="join_confirm_arti">
		<form action="login.do" method="get">
				<p>
					<strong>${nickname}</strong>님 회원가입을 축하합니다.!!
					<br><br>확인버튼을 누르면 로그인 페이지로 이동합니다.
				</p>
				<input type="submit" class="button1" value="확인">
		</form>
	</article>
	<%@ include file="../footer.jsp" %>   
</body>
</html>