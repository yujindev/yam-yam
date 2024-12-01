<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>랜덤 메뉴관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
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
<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
	<h2>랜덤 메뉴관리</h2>
	<form id="search_form" action="tmenuList.do" method="get">
		<ul class="search">
			<li>
				<select name="keyfield">
					<option value="1" <c:if test="${param.keyfield==1}"></c:if>>메뉴명</option>
				</select>
			</li>
			<li>
				<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
		</ul>
	</form>
	<div class="list-space align-right">
	<c:if test="${!empty user_num && user_auth == 9}">
		<input type="button" value="메뉴 등록" onclick="location.href='tmenuWriteForm.do'">
		<input type="button" value="목록" onclick="location.href='tmenuList.do'">
	</c:if>
	</div>
	<c:if test="${count ==0}">
	<div class="result-display">
	 	표시할 메뉴가 없습니다.
	</div>
	</c:if>
	<c:if test="${count > 0}">
	 	<table>
	 		<tr>
	 			<th>번호</th>
	 			<th>메뉴명</th>
	 			<th>삭제</th>
	 		</tr>
	 		<c:forEach var="tmenu" items="${list}">
	 		<tr>
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