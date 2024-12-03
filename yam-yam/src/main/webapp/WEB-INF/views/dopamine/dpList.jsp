<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이게뭐지 게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HR.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style type="text/css">
</style>
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
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2 class="fw-700 fs-16 m-1">이게 뭐지?</h2>
		<form id="search_form" action="dpList.do" method="get" class="text-c">
			<ul class="search w-100">
				<li>
					<select name="keyfield" class="search-cat">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
					</select>
				</li>
				<li class="ml-1">
					<input type="search" size="16" name="keyword"
					       id="keyword" value="${param.keyword}" class="search-input bg-gr150 w-80" placeholder="검색할 내용을 입력하세요.">
				</li>
				<li class="ml-1">
					<input type="submit" value="" class="btn-re icon-search">
				</li>
			</ul>
		</form>
		<div class="list-space flex-box f-end m-1">
			<c:if test="${user_auth==9}">
			<input type="button" value="글쓰기" 
			       onclick="location.href='writeDpForm.do'" class="btn-re btn-line-primary">
			</c:if>       
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<ul class="w-95 m-0auto">
             <c:forEach var="dopamine" items="${list}">
                 <li class="mt-2">
                 <a href="detail.do?dp_num=${dopamine.dp_num}">
                     <p class="fs-10 text-main">${dopamine.dp_num}</p>
                     <p class="fs-12 fw-700 mt-1">${dopamine.dp_title}</p>
                     <div class="bar pt-2"></div>
                 </a>    
                 </li>
             </c:forEach>
           </ul>
		<div class="align-center mt-3">${page}</div>
		</c:if>		
<jsp:include page="/WEB-INF/views/reserv/reservList.jsp"/>
	</div>	
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>




