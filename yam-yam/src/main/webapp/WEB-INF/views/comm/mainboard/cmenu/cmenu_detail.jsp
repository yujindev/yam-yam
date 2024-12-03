<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cmenu.reply.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<input type="button"onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="icon block-box ml-auto icon-home bg-gr300">
			<h2 class="fw-700">${cmenu.cmenu_title}</h2>
				<ul class="">
					<li class="fw-700 fs-12">
					<span class="va-super">${cmemu.mem_nickname}</span>
					<a href="#">
						<c:if test="${!empty cmenu.mem_img2}">
						<img src="${pageContext.request.contextPath}/upload/${cmenu.mem_img2}" width="40" height="40" class="my-photo">
						</c:if>
						<c:if test="${empty cmenu.mem_img2}">
						<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
						</c:if>
							<span class="va-super">${cmemu.mem_nickname}</span>
						</a>
					</li>
					<li class="fs-08 text-r">
						<span>조회 : ${cmenu.cmenu_hit}</span>
						<span class="ml-2"><c:if test="${!empty cmenu.cmenu_date}"></c:if> 작성일 : ${cmenu.cmenu_date}</span>
					</li>
				</ul>
				
				<div class="bar mt-1"></div>
				
				<article class="p-2">
					<c:if test="${!empty cmenu.cmenu_filename}">
				<div class="align-center">
				<img src="${pageContext.request.contextPath}/upload/${cmenu.cmenu_filename}" class="detail-img">
				<img src="${pageContext.request.contextPath}/upload/${cmenu.cmenu_filename2}" class="detail-img">		
				</div>
				</c:if>
				내용 : ${cmenu.cmenu_article}
				</article>
				
				<div class="flex-box f-center p-1">
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%> 
					<c:if test="${user_num == cmenu.mem_num}">
						<input type="button" value="수정"onclick="location.href='updateForm.do?cmenu_num=${cmenu.cmenu_num}'" class="btn btn-line-primary mr-1">
						<input type="button" value="삭제" id="delete_btn" class="btn btn-line-gray mr-1">
						<script type="text/javascript">
							const delete_btn = document
									.getElementById('delete_btn');
							delete_btn.onclick = function() {
								let choice = confirm('삭제하시겠습니까?');
								if (choice) {
									location
											.replace('delete.do?cmenu_num=${cmenu.cmenu_num}');
								}
							};
						</script>
					</c:if>
					<input type="button" value="글목록" onclick="location.href='list.do'" class="btn block-box">
			</div>
			<!-- 댓글시작 -->
			<div class="bar"></div>
			<div id="reply_div">
				<p class="re-title p-1 fw-600">댓글</p>
				<form id="re_form" class="w-90 flex-box f-between position-r m-0auto">
					<input type="hidden" name="cmenu_num" value="${cmenu.cmenu_num}"
						id="cmenu_num">
					<textarea rows="3" cols="50" name="cmenu_re_content" id="cmenu_re_content" class="rep-content w-80" <c:if test="${empty user_num}"> disabled = "disabled" placeholder="로그인 해야 작성할 수 있습니다."</c:if>></textarea>
					<c:if test="${!empty user_num}">
						<div id="re_first" class="no-float position-a re-count w-05">
							<span class="letter-count fs-08 text-gr300">300/300</span>
						</div>
						<div id="re_second" class="no-float w-10">
							<input type="submit" value="등록" class="btn btn-primary">
						</div>
					</c:if>
				</form>
			</div>
			<!-- 댓글 목록 출력 시작 -->
			<div id="output" class="p-1"></div>
			<div class="paging-button" style="display: none;">
				<input type="button" value="다음글 보기">
			</div>
			<div id="loading" style="display: none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif"
					width="50" height="50">
			</div>
			<!-- 댓글 목록 출력 끝 -->
			<!-- 댓글끝 -->
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />		
</div>
</body>
</html>