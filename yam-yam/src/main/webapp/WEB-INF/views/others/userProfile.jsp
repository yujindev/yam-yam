<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="text-c">
	<h2 class="fw-600 fs-16">${member.mem_nickname}님의 프로필</h2>
		<button type="button"
			onclick="location.href='${pageContext.request.contextPath}/chat/showChat.do?chat_receiver_num=${member.mem_num}'"
			class="btn-primary btn-icon pl-1 block-box ml-auto "
			style="background-image: url('${pageContext.request.contextPath}/images/icon-chat.png')";>채팅하기</button>
</div>