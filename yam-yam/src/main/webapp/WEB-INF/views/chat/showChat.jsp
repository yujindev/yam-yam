<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${member.mem_nickname}님과의 1:1 채팅</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/YJ.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>
</head>
<body id="chatRoomPage">
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2 class="text-c fs-16 fw-500">${member.mem_nickname}님과의 1:1 채팅</h2>

		<div id="chatting_message"></div>
		<form id="chatting_form" class="msg-box flex-box w-80">
			<input type="hidden" name="chat_receiver_num" id="chat_receiver_num" value="${member.mem_num}">
			<input type="hidden" id="user_num" value="${user_num}">
			<textarea name="message" id="message"placeholder="메시지를 입력하세요"></textarea>
			<button type="submit">전송</button>
		</form>
		<input type="button" value="채팅목록" onclick ='location.href="${pageContext.request.contextPath}/chat/chatList.do?=${user_num}"'>
	</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>