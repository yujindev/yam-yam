<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
$(function(){
		$('#update_form').submit(function() {
			const items = document
					.querySelectorAll('.input-check');
			for (let i = 0; i < items.length; i++) {
				if (items[i].value.trim() == '') {
					const label = $('label[for="'+items[i].id+'"]');
					alert(label.text() + ' 필수 입력');
					items[i].value = '';
					$(items[i]).focus();
					return false;
				}
			}
		});
	});
</script>

</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2 class="fw-700">글 수정</h2>
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data" class="mt-3 w-80 m-0auto">
			<input type="hidden" name="ctalk_num" value="${ctalk.ctalk_num}">
				<ul>
					<li class="flex-box mb-2">
					<label for="ctalk_title" class="form-ttl">제목</label>
					<input type="text" value="${ctalk.ctalk_title}" name="ctalk_title" id="ctalk_title" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box">
					<label for="ctalk_article" class="form-ttl">내용</label> 
					<textarea rows="20" cols="40" name="ctalk_article" id="ctalk_article" class="input-check w-90 p-05 ml-auto block-box">${ctalk.ctalk_article}</textarea>
					</li>
				</ul>
				<div class="flex-box f-end mt-3">
					<input type="submit" value="수정" class="btn btn-primary"> 
					<input type="button" value="수정 취소" onclick="location.href='ctalk_detail.do?ctalk_num=${ctalk.ctalk_num}'" class="btn ml-1">
				</div>
			</form>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>