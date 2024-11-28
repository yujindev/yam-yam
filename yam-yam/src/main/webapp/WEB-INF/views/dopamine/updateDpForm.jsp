<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//게시판 유효성 체크
		$('#update_form').submit(function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector(
							       'label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 필수 입력');
					items[i].value='';
					items[i].focus();
					return false;
				}			
			}//end of for		
		});//end of submit
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>글 수정</h2>
		<form id="update_form" action="updateDp.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="dp_num" value="${dopamine.dp_num}">
			<ul>
				<li>
					<label for="dp_title">제목</label>
					<input type="text" name="dp_title" id="dp_title"
					       value="${dopamine.dp_title}"
					       maxlength="50" class="input-check">
				</li>
				<li>  
					<label for="dp_content">내용</label>
					<textarea rows="5" cols="40" name="dp_content" id="dp_content" class="input-check">${dopamine.dp_content}</textarea>
					<input type="file" name="dp_content" id="dp_content_image" accept="image/gif,image/png,image/jpeg">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="글 상세" onclick="location.href='detail.do?dp_num=${dopamine.dp_num}'">
			</div>                               
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>




