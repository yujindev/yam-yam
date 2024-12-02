<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현재 채팅목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/YJ.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(function(){
	//채팅방 삭제
	$('.chatDeleteBtn').on('click',function(){
		let notice = confirm('채팅방을 나가시겠습니까? 채팅방을 나가신 후에는 채팅내역이 복구되지 않습니다.');
		if(notice) {
			location.replace('chatDelete.do?chat_receiver_num='+$(this).attr('data-receiver')+'&chat_sender_num='+$(this).attr('data-sender'));
			alert('채팅방이 삭제되었습니다.');
		}
	});
});

</script>
</head>
<body>
	<div class=page-main>
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<h2 class="text-c">${user_nickname}님의 1:1채팅목록 </h2>
			<div class="list-space align-right">
				<input type="hidden" id="user_num" value="${user_num}">
				<input type="button"value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
			<c:if test="${count == 0}">
				<div class="result-display">표시할 채팅이 없습니다.</div>
			</c:if>
			
			
			<c:if test="${count>0}">
				<ul class="chat-list-box">
				<c:forEach var="chat" items="${list}">
				<li class="chat-list-item flex-box bg-gr150">
					<a class="w-90" href="showChat.do?chat_receiver_num=${user_num==chat.chat_receiver_num ? chat.chat_sender_num : chat.chat_receiver_num}">
						<ul>
							<li class="fw-800 fs-12">${chat.receiver_nickname} <span class="bg-gr100 text-main chat-unread">${chat.cnt}</span></li>
							<li class="pt-2">${fn:substring(chat.chat_message,0,40)}</li>
							<li class="text-r chat-sentat">${chat.chat_sent_at}</li>
						</ul>
					</a>
					<button class="chatDeleteBtn ml-auto btnClose" data-receiver="${chat.chat_receiver_num}" data-sender="${chat.chat_sender_num}"></button>
				</li>
				</c:forEach>
				</ul>
				<div class="pt-2 text-c">${page}</div>
			</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>