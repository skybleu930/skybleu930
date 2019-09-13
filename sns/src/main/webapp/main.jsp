<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@page isELIgnored="false"%> 
<%@ include file="header.jsp" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<article class="board_arti">
	
		<!-- <div class="row"> -->
 		<div class="board_list">
 			<c:forEach items="${boardList}" var="boardVO">
	 			<figure>
					 <a href="boardDetail.do?bseq=${boardVO.bseq}">
           				 <img src="image/board_image/${boardVO.image}"/> 
					 </a>
					 <figcaption>${boardVO.subject}</figcaption>
				 </figure>
			 </c:forEach>
		</div>
	<!-- 	</div> -->
</article>
<%@ include file="footer.jsp" %> 	
</body>
</html>