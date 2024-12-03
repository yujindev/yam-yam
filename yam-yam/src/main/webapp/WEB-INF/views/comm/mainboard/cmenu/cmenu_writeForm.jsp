<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		$('#write_form').submit(function() {
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
			<h2 class="fw-700">게시판 글쓰기</h2>
			<form id="write_form" action="cmenu_write.do" method="post" enctype="multipart/form-data" class="mt-3 w-80 m-0auto">
				<ul>
					<li class="flex-box mb-2">
					<label for="cmenu_title">제목</label> <input type="text"
						name="cmenu_title" id="cmenu_title" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cmenu_loc">위치</label> <input type="text"
						name="cmenu_loc" id="cmenu_loc" maxlength="50" class="input-check w-90 p-05 ml-auto block-box" >
					</li>
					
					<li class="flex-box mb-2">
					<label for="cmenu_name">음식명</label> <input type="text"
						name="cmenu_name" id="cmenu_name" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cmenu_article">내용</label> 
					<textarea rows="5" cols="40" name="cmenu_article" id="cmenu_article" class="input-check w-90 p-05 ml-auto block-box"></textarea>
					</li>
									
					<li class="flex-box mb-2">
					<label for="cmenu_filename">이미지</label> <input type="file"
						name="cmenu_filename" id="cmenu_filename" accept="image/gif,image/png,image/jpeg" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cmenu_filename2">이미지2</label> <input type="file"
						name="cmenu_filename2" id="cmenu_filename2" accept="image/gif,image/png,image/jpeg" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cmenu_star">별점</label> <input type="text"
					name="cmenu_star" id="cmenu_star" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
				</ul>
				<div class="flex-box f-end mt-3">
					<input type="submit" value="등록" class="btn btn-primary"> 
					<input type="button" value="목록" onclick="location.href='list.do'" class="btn ml-1">
				</div>
			</form>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>