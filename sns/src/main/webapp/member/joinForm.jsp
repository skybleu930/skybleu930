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
/* var re = /^[a-zA-Z0-9]{4,12}$/; */ // 아이디와 패스워드가 적합한지 검사할 정규식

/* function idOnblur(form) {
	if(!new RegExp(re).test(form.id.value)) {
		alert("영어 및 숫자만 허용 (4자 부터 12자 이내)id");
		chek = false;
	}   
} */

/* function pwOnblur(form) {
	if(!new RegExp(re).test(form.pw.value)) {
		alert("영어 및 숫자만 허용 (4자 부터 12자 이내)pw");
		chek = false;
	}
}
function pw2Onblur(form) {
	if(!new RegExp(re).test(form.pw2.value)) {
		alert("입력한 비밀번호가 틀립니다.");
		
	} else if(form.pw.value != form.pw2.value) {
		alert("입력한 비밀번호가 틀립니다.");
	} 
} */

function go_back(form) {
	form.action = "index.do";
	form.submit();	
}

function go_join(form) {
	if(form.email.value == "") {
		alert("아직 입력되지 않은 기재란이 있습니다.");
	} else if(form.pw.value == "") {
		alert("아직 입력되지 않은 기재란이 있습니다.");
	} else if(form.pw2.value == "") {
		alert("아직 입력되지 않은 기재란이 있습니다.");
	} else if(form.name.value == "") {
		alert("아직 입력되지 않은 기재란이 있습니다.");
	} else if(form.nickname.value == "") {
		alert("아직 입력되지 않은 기재란이 있습니다.");
	} else if(form.phone.value == "") {
		alert("아직 입력되지 않은 기재란이 있습니다.");
	} else {
		form.action = "join.do";
		form.submit();
	}
}

function emailChecked() {
 	var email = document.getElementById('email');
 	var email_td = document.getElementById('email_td');
	
 	if(email.value == "") {
 		email_td.style = null; //스타일 초기화 
		email_td.innerHTML = "＊이메일을 입력해주세요.";
		document.join_form.hidden_email.value = "1";
	} 
		
 	document.join_form.action = "joinCheck.do";
	document.join_form.submit();
}	 

function nicknameChecked() {
 	var nickname = document.getElementById('nickname');
 	var nickname_td = document.getElementById('nickname_td');
 	
	if(nickname.value == "") {
		nickname_td.style = null; //스타일 초기화 
		nickname_td.innerHTML = "＊닉네임을 입력해주세요.";
		document.join_form.hidden_nickname.value = "1";
		
	} else {
		document.join_form.action = "joinCheck.do";
		document.join_form.submit();
	} 
}	
</script>
</head>
<body>
	<article id="join_arti">
		<h2>회원가입</h2>
		<form name="join_form" method="post">
			<table class="table1">	
				<tr>
					<td>
						<input class="input_text" type="text" size="30" id="email" 
							value="${email}" name="email" placeholder="이메일 입력" onblur="emailChecked()"/>
						<input type="hidden" name="hidden_email">
					</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${email_message >= 1}">
							<td id="email_td" style="color: red;">＊이미 사용중인 E-mail입니다.</td>
						</c:when>
						<c:when test="${email_message == 0}">
							<td id="email_td" style="color: green;">＊사용 가능한  E-mail입니다.</td>
						</c:when>
						<c:when test="${email_nullCheck == 'yes'}">
							<td id="email_td">＊이메일을 입력해주세요.</td>
						</c:when>
						<c:otherwise>
							<td id="email_td"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td><input class="input_text" type="password" size="30" name="pw" placeholder="비밀번호 입력"/></td>
				</tr>
				<tr>
					<td id="pw_td"></td>
				</tr>	
				<tr>
					<td><input class="input_text" type="password" size="30" name="pw2" placeholder="비밀번호 확인 입력"/></td>
				</tr>
				<tr>
					<td id="pw2_td"></td>
				</tr>	
				<tr>
					<td><input class="input_text" type="text" size="30" name="name" placeholder="이름 입력"/></td>
				</tr>
				<tr>
					<td id="name_td"></td>
				</tr>	
				<tr>
					<td>
						<input class="input_text" type="text" size="30" id="nickname"  
						name="nickname" value="${nickname}" placeholder="닉네임 입력" onblur="nicknameChecked()"/>
						<input type="hidden" name="hidden_nickname">
					</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${nickname_message >= 1}">
							<td id="nickname_td" style="color: red;">＊이미 사용중인 닉네임입니다.</td>
						</c:when>
						<c:when test="${nickname_message == 0}">
							<td id="nickname_td" style="color: green;">＊참 멋있는 닉네임입니다.!</td>
						</c:when>
						<c:when test="${nickname_nullCheck == 'yes'}">
							<td id="email_td">＊닉네임을 입력해주세요.</td>
						</c:when>
						<c:otherwise>
							<td id="nickname_td"></td>
						</c:otherwise>
					</c:choose>
				</tr>	
				<tr>
					<td><input class="input_text" type="text" size="30" name="phone" placeholder="휴대폰 번호 입력"/></td>
				</tr>
				<tr>
					<td id="phone_td"></td>
				</tr>	
			</table>
			<p>
				<input type="button" class="button2" value="취소" onclick="go_back(this.form)">
				<input type="button" class="button1" value="회원가입 " onclick="go_join(this.form)">
			</p>
		</form>
	</article>
	<%@ include file="../footer.jsp" %>   
</body>
</html>