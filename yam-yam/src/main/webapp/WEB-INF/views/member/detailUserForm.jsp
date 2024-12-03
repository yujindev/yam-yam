<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<style type="text/css">
.rsform-lb{
	color:#FF7800;
}
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2 class="fw-700">${member.mem_id}의 회원정보(관리자 전용)</h2>
		<form action="adminUser.do" method="post" id="detail_form">
			<input type="hidden" name="mem_num" value="${member.mem_num}">
			<ul class="w-95 m-0auto">
				<li class="mt-2">
					<label class="rsform-lb fw-700">등급</label>
					<c:if test="${member.mem_auth != 9}">
					<input type="radio" name="mem_auth" value="1"
					      id="mem_auth1" <c:if test="${member.mem_auth == 1}">checked</c:if>>경고
					<input type="radio" name="mem_auth" value="2"
					      id="mem_auth2" <c:if test="${member.mem_auth == 2}">checked</c:if>>일반
					<input type="radio" name="mem_auth" value="7"
					      id="mem_auth7" <c:if test="${member.mem_auth == 7}">checked</c:if>>식당 사장님
					</c:if>
					<c:if test="${member.mem_auth == 9}">
					<input type="radio" name="mem_auth" value="9" id="mem_auth4" checked>운영자
					</c:if>
				</li>
			</ul> 
			<ul class="w-95 m-0auto">
				<li class="mt-2">
					<label class="rsform-lb fw-700">아이디		</label>${member.mem_id}
				</li>
				<li class="mt-2">
					<label class="rsform-lb fw-700">닉네임		</label>${member.mem_nickname}
				</li>
				<li class="mt-2">
					<label class="rsform-lb fw-700">전화번호		</label>${member.mem_phone}
				</li>
			</ul>      
			<br>                                           
			<br>                                           
			<div class="align-center">
				<c:if test="${member.mem_auth != 9}">
				<input type="submit" value="수정" class="btn-re btn-primary">
				</c:if>
				<input type="button" value="목록"
				       onclick="location.href='adminList.do'" class="btn-re btn-line-primary">
			</div>
		</form>
	</div>
</div>
</body>
</html>
