<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/JH.css" type="text/css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="page-main">
			<div class="content-main">
				<h2 class="text-c">${user_nickname}프로필</h2>
				<h3 class="text-c">${user_id}프로필</h3>

				<div class="list-space align-right">
					<input type="hidden" id="user_num" value="${user_num}"> <input
						type="button" value="홈으로"
						onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
				</div>
						
				<div class="flex-box f-center" style="margin-top: 20px;">
				<ul class="m-0auto w-50">
					<li class="m-0auto p-1">
						<button class="btn-primary1"
							onclick="location.href='${pageContext.request.contextPath}/member/modifyPasswordForm.do'">
							<img src="${pageContext.request.contextPath}/images/editmypage.png"
								alt="수정 아이콘" class="btn-icon1	"> 회원정보수정
							<img src="${pageContext.request.contextPath}/images/tag.png" alt="태그 아이콘" class="btn-icon2"> 
						</button>
					</li>
							<li class="mb-1 p-1">
								<button class="btn-primary1" onclick="location.href='${pageContext.request.contextPath}/member/modifyForm.do'">
									<img src="${pageContext.request.contextPath}/images/elsemypage.png"
										alt="수정 아이콘" class="btn-icon1"> 찜
							<img src="${pageContext.request.contextPath}/images/tag.png" alt="태그 아이콘" class="btn-icon2"> 										
								</button>
							</li>

							<li class="mb-1 p-1">
								<button class="btn-primary1"
									onclick="location.href='${pageContext.request.contextPath}/member/modifyForm.do'">
									<img
										src="${pageContext.request.contextPath}/images/elsemypage.png"
										alt="수정 아이콘" class="btn-icon1"> 식당후기
							<img src="${pageContext.request.contextPath}/images/tag.png" alt="태그 아이콘" class="btn-icon2"> 								
								</button>
							</li>

							<li class="mb-1 p-1">
								<button class="btn-primary1"
									onclick="location.href='${pageContext.request.contextPath}/member/modifyForm.do'">
									<img
										src="${pageContext.request.contextPath}/images/elsemypage.png"
										alt="수정 아이콘" class="btn-icon1"> 글/댓글 관리
							<img src="${pageContext.request.contextPath}/images/tag.png" alt="태그 아이콘" class="btn-icon2"> 

								</button>
							</li>

							<li class="mb-1 p-1">
								<button class="btn-primary1"
									onclick="location.href='${pageContext.request.contextPath}/member/modifyForm.do'">
									<img
										src="${pageContext.request.contextPath}/images/elsemypage.png"
										alt="수정 아이콘" class="btn-icon1"> 1:1 채팅목록 
							<img src="${pageContext.request.contextPath}/images/tag.png" alt="태그 아이콘" class="btn-icon2"> 
								</button>
							</li>

							<li class="mb-1 p-1">
								<button class="btn-primary1"
									onclick="location.href='${pageContext.request.contextPath}/member/deleteUserForm.do'">
									<img
										src="${pageContext.request.contextPath}/images/elsemypage.png"
										alt="수정 아이콘" class="btn-icon1	"> 회원탈퇴
							<img src="${pageContext.request.contextPath}/images/tag.png" alt="태그 아이콘" class="btn-icon2"> 
								</button>
							</li>
						</ul>
					</div>
				</div>
				</div>
			</div>
</body>
</html>
