<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@page isELIgnored="false"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
*{
	font-family: 'Nanum Gothic', sans-serif;
}

a {
	color: black;
	text-decoration: none; outline: none
}

a:hover, a:active {
	text-decoration: none; 
}

#message_list_div {
	text-align: center;
	margin-bottom: 30px;
}

#message_list_div span {
	font-size: 20px;
	margin-right: 5%;
	margin-left: 5%
}

#message_list_div label {
	font-size: 18px;
	font-weight: bold;
}

.infoN {
	text-align: center;
}

.message_list_row {
	margin-left: 5%;
	margin-bottom: 5%;
}

.message_list_row strong {
	font-size: 17px;
}

.message_list_row img {
	width: 13%;
	height: auto;
	border-radius: 100%;
	border: 1px dotted;
	border-color: #868e96; 
	vertical-align: top;
}

.message_subject_list {
	display: inline-block;
	width: 70%;
}

.circle {
	display: inline-block;
	width: 10px;
	height: 10px;
	background-color: #db4455;
	border-radius: 50%;
	right: 0;
}
.circle2 {
	display: inline-block;
	width: 10px;
	height: 10px;
	background-color: green;
	border-radius: 50%;
	right: 0;
}
</style>
<script type="text/javascript">
function my_board() {
	opener.header_form.action = "myBoard.do";
	opener.header_form.submit(); 
	self.close();
}

function member_board(nickname) {
	opener.header_form.action = "memberBoard.do?nickname=" + nickname;
	opener.header_form.submit(); 
	self.close();
}

function message_detail(mseq) {
	var windowW = 400;  // 창의 가로 길이
    var windowH = 300;  // 창의 세로 길이
    var left = ((window.screen.width / 2) - (windowW / 2));
    var top = ((window.screen.height / 2) - (windowH / 2));
	var url="messageDetail.do?mseq=" + mseq;
	window.open(url, "popup_win", "left=" + left + ", top=" + top +
			", width="+ windowW +", height="+ windowH);
}
function send_message_detail(mseq) {
	var windowW = 400;  // 창의 가로 길이
    var windowH = 300;  // 창의 세로 길이
    var left = ((window.screen.width / 2) - (windowW / 2));
    var top = ((window.screen.height / 2) - (windowH / 2));
	var url="sendMessageDetail.do?mseq=" + mseq;
	window.open(url, "popup_win", "left=" + left + ", top=" + top +
			", width="+ windowW +", height="+ windowH);
}
</script>
</head>
<body>
<form name="ml_form" method="post">
	<div id="message_list_div">
		<c:if test="${messageList.size() >= 0}"><!-- 받은 메세지 리스트 일때 실행 구문  -->
			<span style="font-weight: bold;">받은 메세지</span>
			<a href="sendMessageList.do">
				<span style="color: #868e96;">보낸 메세지</span>
			</a>
			<br><br>
			<label>받은 메세지 리스트</label>
		</c:if>
		<c:if test="${sendMessageList.size() >= 0}"> <!-- 보낸 메세지 리스트 일때 실행 구문  -->
			<a href="messageList.do">
				<span style="color: #868e96;">받은 메세지</span>
			</a>	
			<span style="font-weight: bold;">보낸 메세지</span>
			<br><br>
			<label>보낸 메세지 리스트</label>
		</c:if>
	</div>
	<c:choose>
		<c:when test="${messageListInfo.size() > 0}"> <!-- 받은 메세지 리스트 일때 실행 구문  -->
			<c:forEach items="${messageListInfo}" var="messageInfo" varStatus="status">
			 	<div class="message_list_row">
			 		<c:choose>
				 		<c:when test="${loginUser.nickname == messageInfo.nickname}">
					 		<a href="#" onclick="my_board()">
						 		<img alt="받은 메세지 프로필" src="image/member_image/${messageInfo.image}">
						 	</a>
						</c:when>
				 		<c:otherwise>
				 			<a href="#" onclick="member_board('${messageInfo.nickname}')">
						 		<img alt="받은 메세지 프로필" src="image/member_image/${messageInfo.image}">
						 	</a>
						</c:otherwise>
					</c:choose>	
					<div class="message_subject_list">
						<a href="#" onclick="message_detail('${messageList.get(status.index).mseq}')">
						 		<span><strong>${messageInfo.nickname}</strong>님의 메세지</span>
						 		<br>${messageList.get(status.index).indate}
				 		</a>
			 		</div>
			 		<c:if test="${messageList.get(status.index).mrep == '2'}">
			 			<div class="circle"></div>
			 		</c:if>
			 		<c:if test="${messageList.get(status.index).mrep == '1'}">
			 			<div class="circle2"></div>
			 		</c:if>
			 	</div>
			</c:forEach>
		</c:when>
		<c:when test="${sendMessageListInfo.size() > 0}"> <!-- 보낸 메세지 리스트 일때 실행 구문  -->
			<c:forEach items="${sendMessageListInfo}" var="sendMessageInfo" varStatus="status">
			 	<div class="message_list_row">
			 		<a href="#" href="#" onclick="member_board('${sendMessageInfo.nickname}')">
			 			<img alt="보낸 메세지 프로필" src="image/member_image/${sendMessageInfo.image}">
			 		</a>
			 		<div class="message_subject_list">
				 		<a href="#" onclick="send_message_detail('${sendMessageList.get(status.index).mseq}')">
					 		<span><strong>${sendMessageInfo.nickname}</strong>님에게 보낸 메세지</span>
					 		<br>${sendMessageList.get(status.index).indate}
				 		</a>
			 		</div>
			 	</div>
			</c:forEach>
		</c:when>
		<c:otherwise> <!-- 메세지 리스트에 아무 정보가 없을 때  -->
		 	<div class="follow_list_row infoN">
		 		<span style="color: #868e96;">등록된 정보가 없습니다.</span>
		 	</div>
		</c:otherwise>
	</c:choose>
	</form>
</body>
</html>