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
function follow(form) {
	form.action = "follow.do"
	form.submit();
}

function unfollow(form) {
	form.action = "unFollow.do"
	form.submit();
}
</script>
</head>
<body>
<section>
	<form name="join_form" method="post">
		<article id="myboard_info">
				<c:choose>
					<c:when test="${myBoard == 'yes'}">
						<label>
							<a href="followerList.do">팔로워 ${myFolloWerList.size()} /</a>
						</label>	
						<label>
							<a href="followingList.do">팔로잉 ${myFolloWingList.size()}</a>
						</label>
						<div>	
							<img src="image/member_image/${loginUser.image}"><br><br>
							<span>${loginUser.nickname}</span>
							<span>(${loginUser.name})</span>
							<a href="memberModifyForm.do">
								<i class="fas fa-pen"></i>
							</a>	
						</div>
					</c:when>
					<c:otherwise>
						<label>
							<a href="followerList.do?nickname=${member.nickname}">
								팔로워 ${myFolloWerList.size()} /</a>
						</label>	
						<label>
							<a href="followingList.do?nickname=${member.nickname}">
							팔로잉 ${myFolloWingList.size()}</a>
						</label>
							<c:choose>
								<c:when test="${result == 0}">
									<input type="button" class="button1 follow" value="팔로우" onclick="follow(this.form)">
								</c:when>
								<c:otherwise>
									<input type="button" class="button2 follow" value="팔로잉" onclick="unfollow(this.form)">
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="nickname" value="${member.nickname}">
						<div>	
							<img src="image/member_image/${member.image}"><br><br>
							<span>${member.nickname}</span>
							<span>(${member.name})</span>
						</div>
					</c:otherwise>
				</c:choose>
		</article>
		<div class="clear"></div>
		<article class="board_arti">
				<c:if test="${myBoard == 'yes'}">
					<div id="board_write">
						<a href="boardWriteForm.do">
							<i class="fas fa-plus"></i> 글쓰기
						</a>
					</div>
				</c:if>	
				<%-- <c:forEach items="${myBoardList}" var="boardVO">
				 		<div class="board_list">
							 <a href="SnsServlet?command=board_detail&bseq=${boardVO.bseq}">
		           				 <img src="image/board_image/${boardVO.image}"/>
								 <h3>${boardVO.subject}</h3>
							 </a>
						</div> --%>
				<div class="board_list">
		 			<c:forEach items="${myBoardList}" var="boardVO">
			 			<figure>
							 <a href="boardDetail.do?bseq=${boardVO.bseq}">
		           				 <img src="image/board_image/${boardVO.image}"/> 
							 </a>
							 <figcaption>${boardVO.subject}</figcaption>
						 </figure>
					 </c:forEach>
				</div>
		</article>
	</form>
</section>
<%@ include file="../footer.jsp" %> 	
</body>
</html>