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
	form.action = "myBoard.do"
	form.submit();	
}

function go_modify(form) {
	/* if(form.email.value == "") {
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
	} else { */
		form.action = "memberModify.do"
		form.submit();
	/* } */
}

function nicknameChecked() {
 	var nickname = document.getElementById('nickname');
 	var nickname_td = document.getElementById('nickname_td');
 	
	if(nickname.value == "") {
		nickname_td.style = null; //스타일 초기화 
		nickname_td.innerHTML = "＊닉네임을 입력해주세요.";
		document.join_form.hidden_nickname.value = "1";
	}	
	
	document.join_form.action = "memberModifyFormCheck.do";
	document.join_form.submit();
	
}	
</script>
</head>
<body>
	<article id="modify_arti">
		<h2>회원 정보 수정</h2>
		<form name="join_form" method="post" enctype="multipart/form-data">
			<div id="profile_modify_div">
				<img id="profile_modify" src="image/member_image/${loginUser.image}"><br>
				<input type="file" name="upfile">
			</div>	
			<table class="table1">
				<tr>
					<td style="height: 40px;">
						${loginUser.email}
					</td>
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
					<td><input class="input_text" type="text" size="30" name="name" 
						value="${loginUser.name}"  placeholder="이름 입력"/></td>
				</tr>
				<tr>
					<td id="name_td"></td>
				</tr>	
				<tr>
					<td>
						<c:choose>
							<c:when test="${empty nickname}">
								<input class="input_text" type="text" size="30" id="nickname"  
								name="nickname" value="${loginUser.nickname}" placeholder="닉네임 입력"
								onblur="nicknameChecked()">
								<input type="hidden" name="hidden_nickname">
							</c:when>
							<c:otherwise>
								<input class="input_text" type="text" size="30" id="nickname"  
								name="nickname" value="${nickname}" placeholder="닉네임 입력" onblur="nicknameChecked()"/>
								<input type="hidden" name="hidden_nickname">
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${nickname_message == 1}">
							<td id="nickname_td" style="color: red;">＊이미 사용중인 닉네임입니다.</td>
						</c:when>
						<c:when test="${nickname_message == -1}">
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
					<td><input class="input_text" type="text" size="30" name="phone" 
						value="${loginUser.phone}" placeholder="휴대폰 번호 입력"/></td>
				</tr>
				<tr>
					<td id="phone_td"></td>
				</tr>	
			</table>
			<p>
				<input type="button" class="button2" value="취소" onclick="go_back(this.form)">
				<input type="button" class="button1" value="수정" onclick="go_modify(this.form)">
			</p>
		</form>
	</article>
	<%@ include file="../footer.jsp" %>   
</body>
</html>