<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밥친구찾기 글쓰기</title>
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
			<h2>밥친구 찾기 글 수정</h2>
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="cbob_num" value="${cbob.cbob_num}">
				<ul>
					<li><label for="cbob_title">제목</label> <input type="text"
					value="${cbob.cbob_title}" name="cbob_title" id="cbob_title" maxlength="50" class="input-check">
					</li>
					
					<li><label for="cbob_gender1">내 성별</label> <input type="checkbox"
					name="cbob_gender1" id="cbob_gender1" maxlength="50" class="input-check">
					</li>
					
					<li><label for="cbob_gender2">상대 성별</label> <input type="checkbox"
						name="cbob_gender2" id="cbob_gender2" maxlength="50" class="input-check">
					</li>
					
					<li><label for="cbob_meet">만나는 시간</label> <input type="text"
						name="cbob_meet" id="cbob_meet" maxlength="50" class="input-check">
					</li>
					
					<li>
					<label for="cbob_article">내용</label> 
					<textarea rows="5" cols="40" name="cbob_article" id="cbob_article" class="input-check">${cbob.cbob_article}</textarea>
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="수정"> 
					<input type="button" value="글 상세" onclick="location.href='cbob_detail.do?cbob_num=${cbob.cbob_num}'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>