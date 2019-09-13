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
#message_wrap {
	text-align: center;
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
</style>
<script type="text/javascript">

</script>
</head>
<body>
	<div id="message_wrap">
		<form action="messageSend.do" method="post">
			<c:choose>
				<c:when test="${sendOk != 'ok'}">
					<img class="profile_img" src="image/member_image/${profileImg}">
					<span id="dear_nick">
						<strong>@${dearNick}</strong>님에게 보내는 쪽지
						<input type="hidden" name="dearNick" value="${dearNick}">
					</span>
				 	<div id="area_wrap">
				 		<textarea name="message" rows="8" cols="40"></textarea>
				 	</div>
				 	<div id="input_wrap">
				 		<input type="submit" class="button1" value="보내기">
				 	</div>
			 	</c:when>
			 	<c:otherwise>
			 		<div id="input_wrap2">
			 			<span id="send_ok">쪽지를 성공적으로 보냈습니다.</span>
				 		<input type="button" class="button1" value="확인" onclick="self.close()">
				 	</div>
			 	</c:otherwise>
		 	</c:choose>
	 	</form>
 	</div>
</body>
</html>