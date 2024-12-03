<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰목록</title>
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
				keyword.value = '';
				keyword.focus();
				return false;
			}
		};
	};
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
	<input type="button"onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="icon block-box ml-auto icon-home bg-gr300">
		<h2 class="fw-700 fs-16 m-1">리뷰목록(관리자 전용)</h2>
		<form id="search_form" action="adminReviewsList.do" method="get">
			<ul class="search w-100">
				<li>
					<select name="keyfield" class="search-cat">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>닉네임</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>가게이름</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
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
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 리뷰정보가 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table class="list-table mt-3">
			<tr>
				<th>닉네임</th>
				<th>가게명</th>
				<th>내용</th>
				<th>별점</th>
				<th>작성일</th>
			</tr>
			<c:forEach var="reviews" items="${list}">
			<tr>
			<%--아이디 눌렀을때 이동하게 할건지..
				<td>
					<a href="adminUserForm.do?mem_num=${reviews.mem_num}">${reviews.id}</a>
				</td> --%>
				<td>${reviews.mem_nickname}</td>
				<td>${reviews.fp_name}</td>
				<td class="w-60">${reviews.reviews_con}</td>
				<td>${reviews.reviews_score}</td>
				<td>${reviews.reviews_date}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center mt-3">${page}</div>
		</c:if>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>




