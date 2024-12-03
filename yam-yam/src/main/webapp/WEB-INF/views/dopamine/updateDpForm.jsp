<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이게 뭐지? 글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HR.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
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
		<h2 class="fw-700">글 수정</h2>
		<form id="update_form" action="updateDp.do" method="post" enctype="multipart/form-data" class="mt-3 w-80 m-0auto">
			<input type="hidden" name="dp_num" value="${dopamine.dp_num}">
			<ul>
				<li class="flex-box mb-2">
					<label for="dp_title" class="form-ttl">제목</label>
					<input type="text" name="dp_title" id="dp_title"
					       value="${dopamine.dp_title}"
					       maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
				</li>
				<li class="flex-box">  
					<label for="dp_content">내용</label>
					<textarea rows="5" cols="40" name="dp_content" id="dp_content" class="input-check w-90 p-05 ml-auto block-box">${dopamine.dp_content}</textarea>
					<input type="file" name="dp_file" id="dp_file" accept="image/gif,image/png,image/jpeg" class="input-check w-90 p-05 ml-auto block-box">
				</li>
			</ul> 
			<div class="flex-box f-end mt-3">
				<input type="submit" value="수정" class="btn btn-primary">
				<input type="button" value="글 상세" onclick="location.href='detail.do?dp_num=${dopamine.dp_num}'" class="btn ml-1">
			</div>                               
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>




