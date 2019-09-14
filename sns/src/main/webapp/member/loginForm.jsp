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
<script type="text/javascript">
function go_back(form) {
	form.action = "index.do";
	form.submit();	
}
function go_login(form) {
	form.action = "login.do";
	form.submit();	
}
</script>
</head>
<body>
	<article id="login_arti">
		<h2>로그인</h2>
		<form method="post">
			<table class="table1">	
				<tr>
					<td><input class="input_text" type="text" size="30" name="email" placeholder="아이디 입력"
						value="${email}"/></td>
				</tr>
				<tr>
					<td><input class="input_text" type="password" size="30" name="pw" placeholder="비밀번호 입력"
						value="${pw}"/></td>
				</tr>
			</table>
			<p>
				<input type="button" class="button2" value="취소" onclick="go_back(this.form)">
				<input type="button" class="button1" value="로그인 " onclick="go_login(this.form)">
			</p>
		<!-- 	<table class="table2" border="1px" style="width: 20%; text-align: center;">	
				<tr>
					<th colspan="3">테스트 아이디</th>
				</tr>
				<tr>
					<th>ID</th><th>PW</th><th>닉네임</th>
				</tr>
				<tr>
					<td>aa</td><td>aa</td><td>임꺽정</td>
				</tr>
				<tr>
					<td>bb</td><td>bb</td><td>홍길동</td>
				</tr>
				<tr>
					<td>cc</td><td>cc</td><td>cc</td>
				</tr>
				<tr>
					<td>dd</td><td>dd</td><td>dd</td>
				</tr>
			</table> -->
		</form>
	</article>
	<%@ include file="../footer.jsp" %>   
</body>
</html>