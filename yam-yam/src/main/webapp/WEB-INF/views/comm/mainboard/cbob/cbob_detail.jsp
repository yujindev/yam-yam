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
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<h2>${cbob.cbob_title}</h2>
				<ul class="detail-info">
					<li>
						<c:if test="${!empty board.photo}">
						<img src="${pageContext.request.contextPath}/upload/${board.photo}" width="40" height="40" class="my-photo">
						</c:if>
						<c:if test="${empty board.photo}">
						<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
						</c:if>
					</li>
					<li>
						${cbob.cbob_num}<br>
						조회 : ${cbob.cbob_hit}
					</li>
				</ul>
				
				<hr size="1" noshade="noshade" width="100%">
				<p>
					<c:if test="${!empty board.filename}">
				<div class="align-center">
				<img src="${pageContext.request.contextPath}/upload/${board.filename}" class="detail-img">		
				</div>
				</c:if>
				${cbob.cbob_menu}
				<br>
				${cbob.cbob_meet}
				<br>				
				${cbob.cbob_gender1}
				<br>
				${cbob.cbob_gender2}
				<br>
				${cbob.cbob_article}
				</p>
					<hr size="1" noshade="noshade" width="100%">
					<ul class="detail-sub">
						<li>
						<c:if test="${!empty cbob.cbob_date}">
						</c:if>
						작성일 : ${cbob.cbob_date}
						<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>
						<c:if test="${user_num == cbob.mem_num}">
							<input type="button" value="수정" onclick="location.href='updateForm.do?cbob_num=${cbob.cbob_num}'">
							<input type="button" value="삭제" id="delete_btn">
						<script type="text/javascript">
							const delete_btn = document.getElementById('delete_btn');
							delete_btn.onclick=function(){
								let choice = confirm('삭제하시겠습니까?');
								if (choice) {
									location.replace('delete.do?cbob_num=${cbob.cbob_num}');
								}
							};
						</script>
					</c:if>
				</li>
			</ul>
			<!-- 댓글시작 -->
			<div id="reply_div">
				<span class="re-title">댓글달기</span>
				<form id="re_form">
					<input type="hidden" name="cbob_num" value="${cbob.cbob_num}" id="cbob_num">
					<textarea rows="3" cols="50" name="cbob_re_content" id="cbob_re_content" class="rep-content"
					<c:if test="${empty user_num}"> disabled = "disabled" </c:if>
					><c:if test="${empty user_num}">로그인 해야 작성할수있습니다.</c:if></textarea>
					<c:if test="${!empty user_num}">
					<div id="re_first">
						<span class="letter-count">300/300</span>
					</div>
					<div id="re_second" class="align-right">
						<input type="submit" value="전송">
					</div>
					</c:if>
				</form>
			</div>
			<!-- 댓글 목록 출력 시작 -->
			<div id="output"></div>
			<div class="paging-button" style="display: none;">
				<input type="button" value="다음글 보기">
			</div>
			<div id="loading" style="display: none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
			</div>
			<!-- 댓글 목록 출력 끝 -->
			<!-- 댓글끝 -->
		</div>
</div>
</body>
</html>