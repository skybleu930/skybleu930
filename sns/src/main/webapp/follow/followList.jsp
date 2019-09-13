<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@page isELIgnored="false"%> 
<%@ include file="../header.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

</script>
</head>
<body>
<article id="follow_list_arti">	
	<form name="follow_list_form" method="post">
		<div id="follow_label_div">
			<c:choose>
				<c:when test="${myInfo == 'yes'}"> <!-- 로그인 회원에 팔로우 정보 일때  -->
					<c:if test="${myFollowerListInfo.size() >= 0}"><!-- 팔로워 리스트 일때 실행 구문  -->
						<span style="font-weight: bold;">팔로워</span>
						<a href="followingList.do">
							<span style="color: #868e96;">팔로잉</span>
						</a>
						<br><br><br>
						<label>팔로워 리스트</label>
					</c:if>
					<c:if test="${myFollowingListInfo.size() >= 0}"> <!-- 팔로잉 리스트 일때 실행 구문  -->
						<a href="followerList.do">
							<span style="color: #868e96;">팔로워</span>
						</a>	
						<span style="font-weight: bold;">팔로잉</span>
						<br><br><br>
						<label>팔로잉 리스트</label>
					</c:if>
				</c:when>
				<c:otherwise> <!-- 다른 회원에 팔로우 정보 일때  -->
					<c:if test="${myFollowerListInfo.size() >= 0}"><!-- 팔로워 리스트 일때 실행 구문  -->
						<span style="font-weight: bold;">팔로워</span>
						<a href="followingList.do?nickname=${nickname}">
							<span style="color: #868e96;">팔로잉</span>
						</a>
						<br><br><br>
						<label>팔로워 리스트</label>
					</c:if>
					<c:if test="${myFollowingListInfo.size() >= 0}"> <!-- 팔로잉 리스트 일때 실행 구문  -->
						<a href="followerList.do?nickname=${nickname}">
							<span style="color: #868e96;">팔로워</span>
						</a>	
						<span style="font-weight: bold;">팔로잉</span>
						<br><br><br>
						<label>팔로잉 리스트</label>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
		<c:choose>
			<c:when test="${myFollowerListInfo.size() > 0}"> <!-- 팔로워 리스트 일때 실행 구문  -->
				<c:forEach items="${myFollowerListInfo}" var="follower">
				 	<div class="follow_list_row">
				 	<c:choose>
				 		<c:when test="${follower.nickname == loginUser.nickname}">
					 		<a href="myBoard.do">
						 		<img alt="팔로워 이미지" src="image/member_image/${follower.image}">
						 		<span>${follower.nickname}</span><span style="color: #868e96;">${follower.name}</span>
					 		</a>
				 		</c:when>
				 		<c:otherwise>
				 			<a href="memberBoard.do?nickname=${follower.nickname}">
					 		<img alt="팔로워 이미지" src="image/member_image/${follower.image}">
					 		<span>${follower.nickname}</span><span style="color: #868e96;">${follower.name}</span>
				 		</a>
				 		</c:otherwise>
				 	</c:choose>
				 	</div>
				</c:forEach>
			</c:when>
			<c:when test="${myFollowingListInfo.size() > 0}"> <!-- 팔로잉 리스트 일때 실행 구문  -->
				<c:forEach items="${myFollowingListInfo}" var="following">
				 	<div class="follow_list_row">
				 		<c:choose>
				 			<c:when test="${following.nickname == loginUser.nickname}">
						 		<a href="myBoard.do">
							 		<img alt="팔로잉 이미지" src="image/member_image/${following.image}">
							 		<span>${following.nickname}</span><span style="color: #868e96;">(${following.name})</span>
						 		</a>
				 			</c:when>
				 			<c:otherwise>
				 				<a href="memberBoard.do?nickname=${following.nickname}">
							 		<img alt="팔로잉 이미지" src="image/member_image/${following.image}">
							 		<span>${following.nickname}</span><span style="color: #868e96;">(${following.name})</span>
						 		</a>
				 			</c:otherwise>
				 		</c:choose>
				 	</div>
				</c:forEach>
			</c:when>
			<c:otherwise> <!-- 팔로잉 팔로우 리스트에 아무 정보가 없을 때  -->
			 	<div class="follow_list_row">
			 		<span style="color: #868e96;">등록된 정보가 없습니다.</span>
			 	</div>
			</c:otherwise>
		</c:choose>
	</form>
</article>
<%@ include file="../footer.jsp" %> 	
</body>
</html>