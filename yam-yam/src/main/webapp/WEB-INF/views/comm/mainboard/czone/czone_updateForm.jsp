<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영상추천 게시판 글쓰기</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
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
			<form id="update_form" action="update.do" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="czone_num" value="${czone.czone_num}">
				<ul>
					<li><label for="czone_title">제목</label> <input type="text"
						value="${czone.czone_title}" name="czone_title" id="czone_title"
						maxlength="50" class="input-check"></li>

					<li><label for="czone_loc">위치</label> <input type="text"
						value="${czone.czone_loc}" name="czone_loc" id="czone_loc"
						maxlength="50" class="input-check"></li>

					<li><label for="czone_article">내용</label> <textarea rows="5"
							cols="40" name="czone_article" id="czone_article"
							class="input-check">${czone.czone_article}</textarea></li>
				
					<li>
					<label for="czone_filename">이미지</label>
					<input type="file" name="czone_filename" id="czone_filename" 
					       accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty czone.czone_filename}">
					<div id="file_detail">
						(${czone.czone_filename})파일이 등록되어 있습니다.
						<img src="${pageContext.request.contextPath}/upload/${czone.czone_filename}" width="100">
						<input type="button" value="파일삭제" id="file_del">
						<script type="text/javascript">
							$('#file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									//서버와 통신
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{czone_num:${czone.czone_num}},
										dataType:'json',
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용하세요');
											}else if(param.result == 'success'){
												$('#file_detail').hide();
											}else if(param.result == 'wrongAccess'){
												alert('잘못된 접속입니다.');
											}else{
												alert('파일 삭제 오류 발생');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
									});
								}
							});
						</script>
					</div>
					</c:if>       
				</li>
				
				</ul>
				<div class="align-center">
					<input type="submit" value="수정"> <input type="button"
						value="글 상세"
						onclick="location.href='czone_detail.do?czone_num=${czone.czone_num}'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>