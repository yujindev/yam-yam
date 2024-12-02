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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cbob.reply.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<input type="button"onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="icon block-box ml-auto icon-home bg-gr300">
			<h2 class="fw-700">${cbob.cbob_title}</h2>
			<ul class="">
				<li class="fw-700 fs-12">
					<a href="#">
					<c:if test="${!empty board.photo}">
						<img src="${pageContext.request.contextPath}/upload/${board.photo}"width="40" height="40" class="my-photo">
					</c:if>
					 <c:if test="${empty board.photo}">
						<img src="${pageContext.request.contextPath}/images/face.png"	width="40" height="40" class="my-photo">
					</c:if>
						<span class="va-super">${cbob.mem_nickname}</span>
					</a>
				</li>
				<li class="fs-08 text-r">
					<span>조회 : ${cbob.cbob_hit}</span>
					<span class="ml-2"><c:if test="${!empty cbob.cbob_date}"></c:if> 작성일 : ${cbob.cbob_date}</span>
				</li>
			</ul>
			
			<div class="bar mt-1"></div>
			
			<article class="p-2">
				<c:if test="${!empty board.filename}">
					<div class="align-center">
						<img
							src="${pageContext.request.contextPath}/upload/${board.filename}"
							class="detail-img">
					</div>
				</c:if>
				메뉴 : ${cbob.cbob_menu}
				<br>
				<br>
				만나는 시간 : ${cbob.cbob_meet}
				<br>
				<br>				
				내 성별 : ${cbob.cbob_gender1}
				<br>
				<br>
				상대 성별 : ${cbob.cbob_gender2}
				<br>
				<br>
				내용 : ${cbob.cbob_article}
				</article>
				
				<div class="flex-box f-center p-1">
					<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%> 
					<c:if test="${user_num == cbob.mem_num}">
						<input type="button" value="수정"onclick="location.href='updateForm.do?cbob_num=${cbob.cbob_num}'" class="btn btn-line-primary mr-1">
						<input type="button" value="삭제" id="delete_btn" class="btn btn-line-gray mr-1">
						<script type="text/javascript">
							const delete_btn = document
									.getElementById('delete_btn');
							delete_btn.onclick = function() {
								let choice = confirm('삭제하시겠습니까?');
								if (choice) {
									location
											.replace('delete.do?cbob_num=${cbob.cbob_num}');
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
					<input type="hidden" name="cbob_num" value="${cbob.cbob_num}"
						id="cbob_num">
					<textarea rows="3" cols="50" name="cbob_re_content" id="cbob_re_content" class="rep-content w-80"
						<c:if test="${empty user_num}"> disabled = "disabled" </c:if>>
						<c:if	test="${empty user_num}">로그인 해야 작성할수있습니다.</c:if>
					</textarea>
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