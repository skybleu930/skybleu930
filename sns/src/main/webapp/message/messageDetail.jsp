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

#message_wrap {
	text-align: center;
	width: 80%;
	margin-left: auto;
	margin-right: auto;
}

#message_wrap label {
	font-size: 15px;
	margin-left: 1%;
}

#input_wrap{
	margin-top: 10px;	
}

#send_ok {
	display: block;
	margin-bottom: 20px;
} 

#input_wrap2 {
	  margin-top: 20%;
}

.message_content_div {
	text-align: left;
	margin-bottom: 10px;
}
.message_content {
	text-align: left;
	width: 80%;
	display: inline-block;
	font-size: 15px;
}

.content_label{
	display: inline-block;
	vertical-align: top;
	width: 15%;
}

.button1 {
	background-color:  #db4455;
	color: white;
	border: none;
	width: 100px;
	height: 30px;
	font-weight: bold;
	font-size: 15px;
	border-radius: 5px;
}

.button2 {
	background-color:  #ffffff;
	border : solid 2px;
	border-color : #db4455;
	color: #db4455;
	width: 100px;
	height: 30px;
	font-weight: bold;
	font-size: 15px;
	border-radius: 5px;
}

.profile_img {
	vertical-align: middle;
	width: 40px;
	height: 40px;
	border-radius: 100%;
	border: 1px dotted;
	border-color: #868e96; 
}
#dear_nick {
	display: block;
	margin-top: 10px;
	margin-bottom: 10px;
	font-size: 18px;
}
#dear_nick strong {
	font-size: 20px;
}

#area_wrap textarea {
  width: 100%;
}

.remessage_check{
 	margin-top: 10px;
	padding-top: 10%;
	border: 1px solid;
 	height: 100px;
 	text-align: center;
}

.remessage_check span {
 	text-decoration: underline;
}


</style>
<script type="text/javascript">
function message_list() {
    document.md_form.action ="messageList.do";
    document.md_form.submit();
}

function send_message_list() {
	document.md_form.action ="sendMessageList.do";
    document.md_form.submit();
}

function send_message_detail(mseq) {
	document.md_form.action="sendMessageDetail.do?mseq=" + mseq;
	document.md_form.submit();
}
function message_detail(mseq) {
	document.md_form.action="messageDetail.do?mseq=" + mseq;
	document.md_form.submit();
}
</script>
</head>
<body>
	<div id="message_wrap">
		<form name="md_form" action="reMessageSend.do" method="post">
			<c:choose>
				<c:when test="${not empty message}">
					<img class="profile_img" src="image/member_image/${messageInfo.image}">
					<span id="dear_nick">
						<strong>@${message.fromNick}</strong>님이 보낸 쪽지
					</span>
					<div class="message_content_div">
						<label>받은 날짜 : ${message.indate}</label><br>
						<label class="content_label">내 용 : </label>
						<div class="message_content">
							<span>${message.message}</span>
						</div>
					</div>
				 	<div id="area_wrap">
				 		<c:choose>
				 			<c:when test="${message.remseq != 0}">
				 				<div class="remessage_check">
					 				<strong style="color: #db4455;">답장 완료</strong><br>
					 				<a href="#" onclick="send_message_detail('${message.remseq}')">
					 					<span>보낸 쪽지 보기</span>
					 				</a>
				 				</div>
				 			</c:when>
				 			<c:otherwise>
				 				<textarea name="message" rows="8" cols="40"></textarea>
				 			</c:otherwise>
				 		</c:choose>
				 	</div>
				 	<div id="input_wrap">
				 		<input type="button" class="button2" value="목록" onclick="message_list()">
				 		<c:if test="${message.mrep == '1'}">
				 			<input type="submit" class="button1" value="답장">
				 		</c:if>
				 		<input type="hidden" name="dearNick" value="${message.fromNick}">
				 		<input type="hidden" name="mseq" value="${message.mseq}">
				 	</div>
				 </c:when>
				<c:when test="${not empty sendMessage}">
					<img class="profile_img" src="image/member_image/${sendMessageInfo.image}">
					<span id="dear_nick">
						<strong>@${sendMessage.dearNick}</strong>님에게 보낸 쪽지 
					</span>
					<div class="message_content_div">
						<label>보낸 날짜 : ${sendMessage.indate}</label><br>
						<label class="content_label">내 용 : </label>
						<div class="message_content">
							<span>${sendMessage.message}</span>
						</div>
						<div class="remessage_check">
			 				<strong style="color: blue;">전송 완료</strong><br>
			 				<c:if test="${connectMseq != 0}">
				 				<a href="#" onclick="message_detail('${connectMseq}')">
				 					<span>받은 쪽지 보기</span>
				 				</a>
			 				</c:if>
			 				<c:if test="${connectMseq == 0}">
				 				<label>연관된 쪽지 없음</label>
			 				</c:if>
		 				</div>
					</div>
				 	<div id="input_wrap">
				 		<input type="button" class="button2" value="목록" onclick="send_message_list()">
				 	</div>
			 	</c:when>
			 	<c:otherwise>
			 		<div id="input_wrap2">
			 			<span id="send_ok">쪽지를 성공적으로 보냈습니다.</span>
				 		<input type="button" class="button1" value="확인" onclick="message_list()">
				 	</div>
			 	</c:otherwise>
		 	</c:choose>
	 	</form>
 	</div>
</body>
</html>