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

function reply_view() {
	var reply_view = document.getElementById("reply_view");
	if(reply_view.innerHTML.trim() == "보기") {
		reply_view.innerHTML = "접기"
		reply_show();
	} else {
		reply_view.innerHTML = "보기";
		reply_hide();
	}
}

function reply_show() {
	document.getElementById("reply_wrap").style.display="block";
}
function reply_hide() {
	document.getElementById("reply_wrap").style.display="none";
}

/* function reply_write_show() {
	document.getElementById("reply_write").style.display="block";
}

function reply_write_hide() {
	document.getElementById("reply_write").style.display="none";
} */

//메세지폼 
function message_form() {
	var windowW = 400;  // 창의 가로 길이
    var windowH = 300;  // 창의 세로 길이
    var left = ((window.screen.width / 2) - (windowW / 2));
    var top = ((window.screen.height / 2) - (windowH / 2));
	var url="messageForm.do?dearNick=${boardDetail.nickname}"
				+"&profileImg=${profileImg}";
	window.open(url, "popup_win", "left=" + left + ", top=" + top +
				", width="+ windowW +", height="+ windowH);
}

//댓글 쓰기
function reply_reg_parent() {
	document.board_detail_form.action = "replyReg.do";
	document.board_detail_form.submit();
	document.board_detail_form.comment.value = "";
}

//답글 달기
function reply_reg_child(parentNick, rseq, grp, depth, comments) {
	var commentValue = document.getElementById(comments).value;
	//alert(document.board_detail_form.commentsd.value);
	 document.board_detail_form.action = "replyReg.do?"
										+"&parentNick=" + parentNick
										+"&rseq=" + rseq
										+"&grp=" + grp
										+"&depth=" + depth
										+"&"+ comments +"=" + commentValue; 
	document.board_detail_form.submit();
	document.board_detail_form.comments.value = "";
}

//댓글 작성 폼 보이게하기
function reply_write_form(rseq) {
	document.getElementById(rseq).style.display="block";
}

function reply_write_cancel(rseq) {
	document.getElementById(rseq).style.display="none";
}

</script>
</head>
<body>
<article id="board_arti">	
	<form name="board_detail_form" method="post">
		<div id="board_div1">
			<span id="board_subject">:: ${boardDetail.subject}</span>
			<span id="board_nickname">
				<c:choose>
					<c:when test="${myBoard == 'yes'}">
						<a href="myBoard.do">
							<img class="profile_img" src="image/member_image/${profileImg}">
							${boardDetail.nickname}
						</a>
					</c:when>
					<c:otherwise>
						<a href="memberBoard.do?nickname=${boardDetail.nickname}">
							<img class="profile_img" src="image/member_image/${profileImg}">
							${boardDetail.nickname}
						</a>
					</c:otherwise>
				</c:choose>
			</span>
		</div>
		<div id="board_div2">
			<div class="clear"></div>
			<span><img src="image/board_image/${boardDetail.image}"></span><br>
			<span>${boardDetail.content}</span>
		</div>
		<div id="board_div3">
			<c:choose>
				<c:when test="${goodCheck == 0}">
					<a href="boardGood.do?bseq=${boardDetail.bseq}
						&nickname=${boardDetail.nickname}">
						<span><i class="far fa-heart"></i></span> <!-- 하트 아이콘 -->
					</a>
				</c:when>
				<c:otherwise>
					<a href="boardGoodDelete.do?
						bseq=${boardDetail.bseq}
						&nickname=${boardDetail.nickname}">
						<span><i class="fas fa-heart"></i></span> <!--  빨간색 하트 아이콘 -->
					</a>
				</c:otherwise>
			</c:choose>
			<a href="#" onclick="message_form(); return false;">
				<span><i class="fas fa-envelope"></i></span> <!-- 메세지 아이콘 -->
			</a>
		</div>
		<div id="board_div4">
			<span>좋아요. ${boardDetail.good}개</span>
		</div>
		<div id="board_div5">
			<c:choose>
				<c:when test="${replyListSize == 0}">
					<span>첫 댓글을 달아주세요.</span>
				</c:when>
				<c:otherwise>
					<span >
						<a href="#" onclick="reply_view(); return false;">
							댓글 ${replyListSize}개 모두 <span id="reply_view">보기</span>
						</a>	
					</span>
				</c:otherwise>
			</c:choose>
			<div></div>
			<div id="reply_wrap">
				<c:forEach items="${replyList}" var="replyVO" varStatus="status">
					<c:if test="${replyVO.depth == 0}">
						<img class="profile_img2" src="image/member_image/
							${memberList.get(status.index).image}">
						<div class="parent_reply">
							<span class="parent_nick">${replyVO.parentNick}</span>  
							<span>${replyVO.comment}</span><br>  
							<span class="reply_write">
								<a href="#" onclick="reply_write_form('${replyVO.rseq}'); return false;">
									답글 달기
								</a>
								<span class="reply_indate">${replyVO.indate}</span>
							</span>
						</div>
						<div style="display: none;" id="${replyVO.rseq}" class="rfd1 reply_form_div">
								<img class="profile_img2" 
									src="image/member_image/${loginUser.image}">
								<textarea class="reply_write" id="comment${replyVO.rseq}" 
								name="comment${replyVO.rseq}" rows="1" cols="40">
								</textarea>	
								<div class="reply_button_div">
									<input type="button" class="reply_reg_button2" value="취 소 " 
									onclick="reply_write_cancel('${replyVO.rseq}')"><br>
									<input type="button" class="reply_reg_button" value="등 록"  
									onclick="reply_reg_child('${replyVO.parentNick}',
															 '${replyVO.rseq}',
															 '${replyVO.grp}',
															 '${replyVO.depth}',
															 'comment${replyVO.rseq}')">
								</div>						 
														 
						</div> 
					</c:if>
					<c:if test="${replyVO.depth != 0}">
						<img class="profile_img3" src="image/member_image/
								${memberList.get(status.index).image}">	
						<div class="child_reply">
							<span class="parent_nick">${replyVO.childNick}</span>   
							<span class="child_nick">@${replyVO.parentNick}</span> 
							<span>${replyVO.comment}</span><br>  
							<span class="reply_write">
								<a href="#" onclick="reply_write_form('${replyVO.rseq}'); return false;">
										답글 달기
								</a>
								 <span class="reply_indate">${replyVO.indate}</span>
							</span>
						</div> 
						<div style="display: none;" id="${replyVO.rseq}" class="rfd2 reply_form_div">
								<img class="profile_img3" 
									src="image/member_image/${loginUser.image}">
								<textarea class="reply_write" id="comment${replyVO.rseq}"  
								name="comment${replyVO.rseq}" rows="1" cols="40">
								</textarea>	
								<div class="reply_button_div">
									<input type="button" class="reply_reg_button2" value="취 소 " 
										onclick="reply_write_cancel('${replyVO.rseq}')"><br>
									<input type="button" class="reply_reg_button" value="등 록"  
									onclick="reply_reg_child('${replyVO.childNick}',
															 '${replyVO.rseq}',
															 '${replyVO.grp}',
															 '${replyVO.depth}',
															 'comment${replyVO.rseq}')">
								</div>						 
						</div> 
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class="rfd1 reply_form_div">
		<img class="profile_img2" 
				src="image/member_image/${loginUser.image}">	
		<!-- 	<span id="board_reply">
				<a href="#" onclick="reply_write_show(); return false;">
					댓글 달기...
				</a>
			</span> -->
			<textarea class="reply_write" name="comment" rows="1" cols="40" placeholder=" 댓글 쓰기"></textarea>	
			<div class="reply_button_div">
				<input type="button" class="reply_reg_button" value="등 록" onclick="reply_reg_parent()">
				<input type="hidden" name="bseq" value="${boardDetail.bseq}">
			</div>
		</div>
	</form>
</article>
<%@ include file="../footer.jsp" %> 	
</body>
</html>