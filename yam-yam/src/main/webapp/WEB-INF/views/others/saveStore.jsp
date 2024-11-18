<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ㅇㅇㅇ님의 프로필 페이지 </title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/YJ.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<div class="text-c">
				<h2>{$mem_nickname}님의 프로필</h2>
				<img src='${pageContext.request.contextPath}/images/icon-account.png'
					alt="개인프로필아이">
				<div class="flex-box f-center btn-area">
					<button type="button" onclick="location.href='${pageContext.request.contextPath}/chat/showChat.do'" class="btn-primary btn-icon pl-1" style="background-image: url('${pageContext.request.contextPath}/images/icon-chat.png')";>채팅하기</button>
					<button class="btn-line-primary btn-icon pl-1" style="background-image: url('${pageContext.request.contextPath}/images/icon-plus.png')";>팔로우하기</button>
				</div>
			</div>
			<ul class="flex-box f-center">
				<li><button type="button" onclick="location.href='page'">찜한식당</button></li>
				<li><button type="button" onclick="location.href='page'">식당리뷰</button></li>
			</ul>
		</div>
	</div>

</body>
</html>