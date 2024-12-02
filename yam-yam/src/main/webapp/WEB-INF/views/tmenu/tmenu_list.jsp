<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>랜덤 메뉴관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('search_form');
		//이벤트 연결
		myForm.onsubmit = function(){
			const keyword = document.getElementById('keyword');
			if(keyword.value.trim()==''){
				alert('검색어를 입력하세요');
				keyword.value='';
				keyword.focus();
				return false;
			}
		};
	};
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<div class="content-main">
	<h2 class="fw-700">랜덤 메뉴관리</h2>
	<form id="search_form" action="tmenuList.do" method="get" class="text-c">
		<ul class="search w-100">
			<li>
				<select name="keyfield" class="search-cat">
					<option value="1" <c:if test="${param.keyfield==1}"></c:if>>메뉴명</option>
				</select>
			</li>
			<li class="ml-1">
				<input type="search" class="search-input bg-gr150 w-20" size="10" name="keyword" id="keyword" placeholder="검색할 내용을 입력하세요." value="${param.keyword}">
			</li>
			<li class="ml-1">
				<input type="submit" value="" class="btn-re icon-search">
			</li>
		</ul>
	</form>
	<div class="list-space align-right">
	<c:if test="${!empty user_num && user_auth == 9}">
		<input type="button" value="메뉴 등록" onclick="location.href='tmenuWriteForm.do'" class="btn-re btn-line-primary " style="width: 80px;">
		<input type="button" value="목록" onclick="location.href='tmenuList.do'" class="btn-re btn-line-primary ">
	</c:if>
	</div>
	<c:if test="${count ==0}">
	<div class="result-display">
	 	표시할 메뉴가 없습니다.
	</div>
	</c:if>
	<c:if test="${count > 0}">
	 	<table class="list-table mt-3">
	 		<tr>
	 			<th>번호</th>
	 			<th>메뉴명</th>
	 			<th>삭제</th>
	 		</tr>
	 		<c:forEach var="tmenu" items="${list}">
	 		<tr align="center">
	 			<td>${tmenu.tm_num}</td>
	 			<td>${tmenu.tm_menu}</td>
	 			<td>
	 				<c:if test="${!empty user_num && user_auth == 9}">
	 				<input type="button" value="메뉴 삭제" data-num="${tmenu.tm_num}" class="tm_delete_btn">
	 				</c:if>
	 			</td>
	 		</tr>
	 		</c:forEach>
	 	</table>
	 	<script type="text/javascript">
			const delete_btn = document.querySelectorAll('.tm_delete_btn');
			delete_btn.forEach(function(element,index,array){
				//이벤트 연결
				element.onclick = function() {
					
					let choice = confirm('삭제하시겠습니까?');
					if (choice) {
						location.replace('delete.do?tm_num='+this.getAttribute('data-num'));
					}
				}
			});
		</script>
	 	<div class="align-center">${page}</div>
	</c:if>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>