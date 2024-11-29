<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="text-c">
	<h2>${member.mem_nickname}님의프로필</h2>
	<img src='${pageContext.request.contextPath}/images/icon-account.png'
		alt="개인프로필아이">
		<button type="button"
			onclick="location.href='${pageContext.request.contextPath}/chat/showChat.do?chat_receiver_num=${member.mem_num}'"
			class="btn-primary btn-icon pl-1 block-box ml-auto "
			style="background-image: url('${pageContext.request.contextPath}/images/icon-chat.png')";>채팅하기</button>
</div>
<ul class="flex-box f-center pb-1">
	<li class="tab selected"><a href="${pageContext.request.contextPath}/others/saveStore.do?mem_id=${member.mem_id}">찜한식당</a></li>
	<li class="tab"><a href="${pageContext.request.contextPath}/others/saveReview.do?mem_id=${member.mem_id}">찜한리뷰</a></li>
</ul>