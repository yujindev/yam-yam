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
			<h2 class="fw-700">글 수정</h2>
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="cmenu_num" value="${cmenu.cmenu_num}">
				<ul>
					<li class="flex-box mb-2">
						<label for="cmenu_title">제목</label> <input type="text" value="${cmenu.cmenu_title}"
						name="cmenu_title" id="cmenu_title" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cmenu_loc">위치</label> <input type="text" value="${cmenu.cmenu_loc}"
						name="cmenu_loc" id="cmenu_loc" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cmenu_name">음식명</label> <input type="text" value="${cmenu.cmenu_name}"
						name="cmenu_name" id="cmenu_name" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box">
					<label for="cmenu_article">내용</label> 
					<textarea rows="5" cols="40" name="cmenu_article" id="cmenu_article" class="input-check w-90 p-05 ml-auto block-box">${cmenu.cmenu_article}</textarea>
					</li>
							
					<li class="flex-box mb-2">
					<label for="cmenu_filename">이미지</label>
					<input type="file" name="cmenu_filename" id="cmenu_filename" 
					       accept="image/gif,image/png,image/jpeg" class="input-check w-90 p-05 ml-auto block-box">
					<c:if test="${!empty cmenu.cmenu_filename}">
					<div id="file_detail">
						(${cmenu.cmenu_filename})파일이 등록되어 있습니다.
						<img src="${pageContext.request.contextPath}/upload/${czone.czone_filename}" width="100">
						<input type="button" value="파일삭제" id="file_del" class="btn ml-1">
						<script type="text/javascript">
							$('#file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									//서버와 통신
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{cmenu_num:${cmenu.cmenu_num}},
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
					
					<li class="flex-box mb-2">
					<label for="cmenu_filename2">이미지2</label>
					<input type="file" name="cmenu_filename2" id="cmenu_filename2" 
					       accept="image/gif,image/png,image/jpeg" class="input-check w-90 p-05 ml-auto block-box">
					<c:if test="${!empty cmenu.cmenu_filename2}">
					<div id="file_detail">
						(${cmenu.cmenu_filename})파일이 등록되어 있습니다.
						<img src="${pageContext.request.contextPath}/upload/${czone.czone_filename2}" width="100">
						<input type="button" value="파일삭제" id="file_del" class="btn ml-1">
						<script type="text/javascript">
							$('#file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									//서버와 통신
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{cmenu_num:${cmenu.cmenu_num}},
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
					
					<li class="flex-box mb-2">
					<label for="cmenu_star">별점</label> <input type="text"
					name="cmenu_star" id="cmenu_star" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
				</ul>
				<div class="flex-box f-end mt-3">
					<input type="submit" value="수정" class="btn btn-primary"> 
					<input type="button" value="수정 취소" onclick="location.href='cmenu_detail.do?cmenu_num=${ctalk.ctalk_num}'" class="btn ml-1">
				</div>
			</form>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>