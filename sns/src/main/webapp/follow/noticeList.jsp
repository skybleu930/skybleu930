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

#notice_list_div {
	text-align: center;
	margin-bottom: 30px;
}

#notice_list_div span {
	font-size: 18px;
	margin-right: 5%;
	margin-left: 5%
}

#notice_list_div label {
	font-size: 17px;
	font-weight: bold;
}

.infoN {
	text-align: center;
}

.notice_list_row {
	margin-left: 5%;
	margin-bottom: 5%;
}

.notice_list_row strong {
	font-size: 17px;
}

.noti_profile_img {
	width: 13%;
	height: auto;
	border-radius: 100%;
	border: 1px dotted;
	border-color: #868e96; 
	vertical-align: top;
}

.noti_board_img {
	width: 13%;
	height: 13%;
	vertical-align: top;
}

.notice_list_row div {
	display: inline-block;
	width: 65%;
}

.follow div {
	width: 80%;
}
</style>
<script type="text/javascript">
function my_board() {
	opener.header_form2.action = "myBoard.do";
	opener.header_form2.submit(); 
	self.close();
}

function member_board(nickname) {
	opener.header_form2.action = "memberBoard.do?nickname=" + nickname;
	opener.header_form2.submit(); 
	self.close();
}

function board_detail(bseq) {
	opener.header_form2.action = "boardDetail.do?bseq=" + bseq;
	opener.header_form2.submit();
	self.close();
}
</script>
</head>
<body>
<form name="notice_form" method="post">
	<div id="notice_list_div">
		<c:if test="${noticeGoodList.size() >= 0}"><!-- 좋아요 일때 실행 구문  -->
			<span style="font-weight: bold;">좋아요</span>
			<a href="noticeFollow.do">
				<span style="color: #868e96;">팔로우</span>
			</a>
			<a href="noticeNews.do">
				<span style="color: #868e96;">News</span>
			</a>
			<br><br>
			<label>좋아요 리스트</label>
		</c:if>
		<c:if test="${noticeFollowList.size() >= 0}"> <!-- 팔로우 일때 실행 구문  -->
			<a href="noticeGood.do">
				<span style="color: #868e96;">좋아요</span>
			</a>	
			<span style="font-weight: bold;">팔로우</span>
			<a href="noticeNews.do">
				<span style="color: #868e96;">News</span>
			</a>
			<br><br>
			<label>팔로우 리스트</label>
		</c:if>
		<c:if test="${noticeNewsList.size() >= 0}"> <!-- News 일때 실행 구문  -->
			<a href="noticeGood.do">
				<span style="color: #868e96;">좋아요</span>
			</a>
			<a href="noticeFollow.do">
				<span style="color: #868e96;">팔로우</span>
			</a>	
			<span style="font-weight: bold;">News</span>
			<br><br>
			<label>News 리스트</label>
		</c:if>
	</div>
	<c:choose>
		<c:when test="${noticeGoodList.size() > 0}"> <!-- 좋아요 리스트 일때 실행 구문  -->
			<c:forEach items="${noticeGoodList}" var="noticeGood" varStatus="status">
			 	<div class="notice_list_row">
			 		<!-- 다른 회원이 내게시물을 좋아요 했을 때  -->
			 		<c:if test="${empty noticeGood.ffwingNick}">
			 			<a href="#" onclick="member_board('${noticeGood.fwerNick}')">
					 		<img class="noti_profile_img" alt="좋아요한 사람 프로필" 
					 				src="image/member_image/${memberList.get(status.index).image}"> 
				 		</a>		
				 		<div>
					 		<span>
					 			<strong><a href="#" onclick="member_board('${noticeGood.fwerNick}')">
					 				${noticeGood.fwerNick}</a></strong>님이 회원님의 게시물을 좋아합니다.
					 		</span>
					 		<br>${noticeGood.indate}
				 		</div>
				 		<a href="#" onclick="board_detail('${boardList.get(status.index).bseq}')">
					 		<img class="noti_board_img" alt="좋아요 게시판 이미지" 
					 				src="image/board_image/${boardList.get(status.index).image}">
				 		</a>		
			 		</c:if>
			 		<!-- 팔로잉한 회원이 게시물을 좋아요 했을때  -->
			 		<c:if test="${not empty noticeGood.ffwingNick}">
			 			<!-- 팔로잉한 회원이 내가 작성한 게시물을 좋아요 했을때  -->
			 			<c:if test="${loginUser.nickname == memberList.get(status.index).nickname}">
				 			<a href="#" onclick="my_board()">
					 			<img class="noti_profile_img" alt="좋아요한 사람 프로필" 
					 					src="image/member_image/${memberList.get(status.index).image}">
				 			</a>		
			 			</c:if>	
			 			<!-- 팔로잉한 회원이 좋아요한 게시물이 내가 작성한 글이 아닐 때  -->	
			 			<c:if test="${loginUser.nickname != memberList.get(status.index).nickname}">
				 			<a href="#" onclick="member_board('${noticeGood.fwingNick}')">
					 			<img class="noti_profile_img" alt="좋아요한 사람 프로필" 
					 					src="image/member_image/${memberList.get(status.index).image}">
				 			</a>		
			 			</c:if>		
				 		<div>
					 		<span>
					 			<strong><a href="#" onclick="member_board('${noticeGood.fwingNick}')">
					 				${noticeGood.fwingNick}</a></strong>님이 
						 		<!-- 팔로잉한 회원이 내가 작성한 게시물을 좋아요 했을때  -->
						 		<c:if test="${loginUser.nickname == memberList.get(status.index).nickname}">
						 			<strong><a href="#" onclick="my_board()">
						 					${noticeGood.ffwingNick}</a></strong>님의 게시물을 좋아합니다.
						 		</c:if>
						 		<!-- 팔로잉한 회원이 좋아요한 게시물이 내가 작성한 글이 아닐 때  -->	
						 		<c:if test="${loginUser.nickname != memberList.get(status.index).nickname}">
						 			<strong><a href="#" onclick="member_board('${noticeGood.ffwingNick}')">
						 					${noticeGood.ffwingNick}</a></strong>님의 게시물을 좋아합니다.
						 		</c:if>
					 		</span>
					 		<br>${noticeGood.indate}
				 		</div>
				 		<a href="#" onclick="board_detail('${boardList.get(status.index).bseq}')">
					 		<img class="noti_board_img" alt="좋아요 게시판 이미지" 
					 				src="image/board_image/${boardList.get(status.index).image}">
				 		</a>		
			 		</c:if>
			 	</div>
			</c:forEach>
		</c:when>
		<c:when test="${noticeFollowList.size() > 0}"> <!-- 팔로우 리스트 일때 실행 구문  -->
			<c:forEach items="${noticeFollowList}" var="noticeFollow" varStatus="status">
			 	<div class="notice_list_row follow">
			 		<!-- 다른 회원이 나를 팔로우 했을 때 -->
			 		<c:if test="${empty noticeFollow.ffwingNick}">
			 			<a href="#" onclick="member_board('${noticeFollow.fwerNick}')">
					 		<img class="noti_profile_img" alt="나를 팔로우한 사람 프로필" 
					 			src="image/member_image/${memberList.get(status.index).image}">
				 		</a> 	
				 		<div>
					 		<span>
				 				<strong><a href="#" onclick="member_board('${noticeFollow.fwerNick}')">
				 					${noticeFollow.fwerNick}</a></strong>님이 회원님을 팔로우 했습니다.
					 		</span>
					 		<br>${noticeFollow.indate}
				 		</div>
			 		</c:if>
			 		<!-- 내가 팔로잉한 회원이 팔로우 했을 때 -->
			 		<c:if test="${not empty noticeFollow.ffwingNick}"> 
			 			<a href="#" onclick="member_board('${noticeFollow.fwingNick}')">
					 		<img class="noti_profile_img" alt="내가 팔로잉한 회원 프로필" 
					 			src="image/member_image/${memberList.get(status.index).image}"></a>
				 		<div>
					 		<span>
					 			<strong ><a href="#" onclick="member_board('${noticeFollow.fwingNick}')">
					 				${noticeFollow.fwingNick}</a></strong>님이
						 		<!-- 내가 팔로잉한 회원이 나를 팔로우 했을 때  -->
					 			<c:if test="${noticeFollow.ffwingNick == loginUser.nickname}">
						 			<strong><a href="#" onclick="my_board()">
						 				${noticeFollow.ffwingNick}</a></strong>님을 팔로우 했습니다.
					 			</c:if>
					 			<!-- 내가 팔로잉한 회원이 다른 회원을 팔로우 했을 때 -->
					 			<c:if test="${noticeFollow.ffwingNick != loginUser.nickname}">
						 			 <strong><a onclick="member_board('${noticeFollow.ffwingNick}')">
							 			${noticeFollow.ffwingNick}</a></strong>님을 팔로우 했습니다.
					 			</c:if>
					 		</span>
					 		<br>${noticeFollow.indate}
				 		</div>
			 		</c:if>
			 	</div>
			</c:forEach>
		</c:when>
		<c:when test="${noticeNewsList.size() > 0}"> <!-- News 리스트 일때 실행 구문  -->
			<c:forEach items="${noticeNewsList}" var="noticeNews" varStatus="status">
			 	<div class="notice_list_row">
			 		<a href="#" onclick="member_board('${noticeNews.fwingNick}')">
				 		<img class="noti_profile_img" alt="팔로잉한 회원 프로필" 
				 			src="image/member_image/${memberList.get(status.index).image}">
			 		</a>	
			 		<div>
				 		<span>
			 				<strong><a href="#" onclick="member_board('${noticeNews.fwingNick}')">
			 					${noticeNews.fwingNick}</a></strong>님이 게시물을 게시했습니다.
				 		</span>
				 		<br>${noticeNews.indate}
			 		</div>
			 		<a href="#" onclick="board_detail('${boardList.get(status.index).bseq}')">
				 		<img class="noti_board_img" alt="팔로잉 회원이 게시한 글 이미지" 
					 				src="image/board_image/${boardList.get(status.index).image}">
				 	</a>			
			 	</div>
			</c:forEach>
		</c:when> 
		<c:otherwise> <!-- 메세지 리스트에 아무 정보가 없을 때  -->
		 	<div class="notice_list_row infoN">
		 		<span style="color: #868e96;">등록된 정보가 없습니다.</span>
		 	</div>
		</c:otherwise>
	</c:choose>
	</form>
</body>
</html>