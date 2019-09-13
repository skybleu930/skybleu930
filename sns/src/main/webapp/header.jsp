<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@page isELIgnored="false"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="css/sns.css" rel="stylesheet">
<link href="css/all.css" rel="stylesheet">
<title>Insert title here</title>
<script type="text/javascript">
	function login_form(form) {
		form.action = "login.do";
		form.method = "get"
		form.submit();
	}

	function join_form(form) {
		form.action = "joinForm.do";
		form.submit();
	}
	
	function message_list() {
		var windowW = 400;  // 창의 가로 길이
	    var windowH = 600;  // 창의 세로 길이
	    var left = ((window.screen.width / 2) - (windowW / 2));
	    var top = ((window.screen.height / 2) - (windowH / 2));
		var url="messageList.do";
		window.open(url, "popup_win", "left=" + left + ", top=" + top +
					", width="+ windowW +", height="+ windowH);
	}
	
	function notice_list() {
		var windowW = 400;  // 창의 가로 길이
	    var windowH = 600;  // 창의 세로 길이
	    var left = ((window.screen.width / 2) - (windowW / 2));
	    var top = ((window.screen.height / 2) - (windowH / 2));
		var url="noticeGood.do";
		window.open(url, "popup_win", "left=" + left + ", top=" + top +
				", width="+ windowW +", height="+ windowH);
	}
	
	function go_search() {
		document.header_form.action = "main.do"
		document.header_form.submit();	
	}
</script>
</head>
<body>
	<header>
		<c:choose>
			<c:when test="${empty sessionScope.loginUser}">
				<nav id="nav1">
					<label id="logo">
						<a href="index.do">SNS LOGO</a>
					</label>
					<form name="header_form" id="header_form" method="post">
						<c:if test="${empty loginform}">
							<input type="button" class="button1" value="로그인" onclick="login_form(this.form)">
						</c:if>
						<c:if test="${empty joinform}">
							<input type="button" class="button2" value="회원가입" onclick="join_form(this.form)">
						</c:if>
					</form>
				</nav>
			</c:when>
			<c:otherwise>
				<nav id="nav1">
					<label id="logo">
						<a href="main.do">SNS LOGO</a>
					</label>
				 	<form name="header_form2" id="header_form2" method="post"> 
					 	<div id="search_div">
							<input type="text" id="search" size="10" name="search"  placeholder=" 검색" onkeypress="go_search()">
						</div>	
						<div id="icon_div">
							<!-- 닉네임  -->
							<a href="myBoard.do"><span>${loginUser.nickname}</span></a>
							<!-- 쪽지 -->
							<a href="#" onclick="message_list(); return false;">
								<i class="fas fa-envelope"></i>
								<c:if test="${messageSize != 0}">
									<label id="message_size">${messageSize}</label>
								</c:if>
							</a>
							<!-- 알림 -->
							<a href="#" onclick="notice_list(); return false;">
								<i class="fas fa-bell"></i>
								<c:if test="${noticeSize != 0}">
									<label id="message_size">${noticeSize}</label>
								</c:if>
							</a>
							<!-- 기타 메뉴 -->
							<!-- <a href="SnsServlet?command=logout"><i class="fas fa-ellipsis-h"></i></a> -->
							<a href="logout.do"><i class="fas fa-sign-out-alt"></i></a>
					 	</div>
				 	</form> 
				</nav>
			</c:otherwise>
		</c:choose>
	</header>
</body>
</html>