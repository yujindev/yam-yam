<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('search_form');
		//이벤트 연결
		myForm.onsubmit=function(){
			const keyword = document.getElementById('keyword');
			if(keyword.value.trim()==''){
				alert('검색어를 입력하세요!');
				keyword.value='';
				keyword.focus();
				return false;
			}
		}
	}
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<input type="button"onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="icon block-box ml-auto icon-home bg-gr300">
		<h2 class="fw-700 fs-16 m-1">회원목록(관리자 전용)</h2>
		<form id="search_form" action="adminList.do" method="get">
			<ul class="search w-100">
				<li>
					<select name="keyfield" class="search-cat">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>아이디</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>닉네임</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>연락처</option>
					</select>
				</li>
				<li class="ml-1">
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" class="search-input bg-gr150 w-80" placeholder="검색할 내용을 입력하세요.">
				</li>
				<li class="ml-1">
					<input type="submit" value="" class="btn-re icon-search">
				</li>
			</ul>
		</form>
		<c:if test="${count==0}">
		<div class="result-display">
			표시할 회원정보가 없습니다.
		</div>
		</c:if>
		<c:if test="${count>0}">
		<table class="list-table mt-3">
			<tr>
				<th>아이디</th>
				<th>닉네임</th>
				<th>연락처</th>
				<th>가입일</th>
				<th>등급</th>
			</tr>
			<c:forEach var="member" items="${list}">
			<tr class="text-c">
				<td>
					<c:if test="${member.mem_auth>0}">
					<a href="adminUserForm.do?mem_num=${member.mem_num}">${member.mem_id}</a>
					</c:if>
				</td>
				<td>${member.mem_nickname}</td>
				<td>${member.mem_phone}</td>
				<td>${member.mem_date}</td>
				<td>
					<c:if test="${member.mem_auth == 0}">탈퇴</c:if>
					<c:if test="${member.mem_auth == 1}">경고</c:if>
					<c:if test="${member.mem_auth == 2}">일반</c:if>
					<c:if test="${member.mem_auth == 7}">식당 사장님</c:if>
					<c:if test="${member.mem_auth == 9}">운영자</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center mt-3">${page}</div>
		</c:if>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
</body>
</html>