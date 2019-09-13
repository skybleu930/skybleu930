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
function go_back(form) {
	form.action = "myBoard.do";
	form.submit();
}
</script>
</head>
<body>
	<article id="board_write_arti">	
		<form action="boardWrite.do" name="board_write_form" method="post" enctype="multipart/form-data">
			<div id="board_write_subject_div">
				<input id="subject" name="subject" type="text" placeholder=" 제목">
			</div>
			<div id="board_write_content_div">
				<textarea id="content" name="content" rows="8" cols="40" placeholder=" 내용"></textarea>
			</div>
			<div id="board_write_image_div">
				<label>이미지 올리기 : </label><input type="file" name="upfile">
			</div>
			<div id="board_write_input_div">
				<input type="submit" class="button1" value="등 록">
				<input type="button" class="button2" value="취 소" onclick="go_back(this.form)">
			</div>
		</form>
	</article>
<%@ include file="../footer.jsp" %> 	
</body>
</html>