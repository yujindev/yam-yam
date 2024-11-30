<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
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
			<h2>글 수정</h2>
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="cmenu_num" value="${cmenu.cmenu_num}">
				<ul>
						<li><label for="cmenu_title">제목</label> <input type="text"
						name="cmenu_title" id="cmenu_title" maxlength="50" class="input-check">
					</li>
					
					<li><label for="cmenu_loc">위치</label> <input type="text"
						name="cmenu_loc" id="cmenu_loc" maxlength="50" class="input-check">
					</li>
					
					<li><label for="cmenu_name">음식명</label> <input type="text"
						name="cmenu_name" id="cmenu_name" maxlength="50" class="input-check">
					</li>
					
					<li>
					<label for="cmenu_article">내용</label> 
					<textarea rows="5" cols="40" name="cmenu_article" id="cmenu_article" class="input-check"></textarea>
					</li>
									
					<li><label for="cmenu_filename">이미지</label> <input type="file"
						name="cmenu_filename" id="cmenu_filename" accept="image/gif,image/png,image/jpeg">
					</li>
					
					<li><label for="cmenu_filename2">이미지2</label> <input type="file"
						name="cmenu_filename2" id="cmenu_filename2" accept="image/gif,image/png,image/jpeg">
					</li>
					
					<li><label for="cmenu_star">별점</label> <input type="text"
					name="cmenu_star" id="cmenu_star" maxlength="50" class="input-check">
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="수정"> 
					<input type="button" value="글 상세" onclick="location.href='cmenu_detail.do?cmenu_num=${cmenu.cmenu_num}'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>