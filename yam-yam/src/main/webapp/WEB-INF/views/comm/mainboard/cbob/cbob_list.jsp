<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밥친구찾기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('search_form');
		myForm.onsubmit=function(){
			const myForm = document.getElementById('keyword');
			if (keyword.value.trim()=='') {
				alert('검색어를 입력해라');
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
    	<input type="button"onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="icon block-box ml-auto icon-home bg-gr300">
    	<h2 class="fw-700 fs-16 m-1">밥친구 게시판 게시판</h2>
    	<form action="list.do" id="search_form" method="get" class="text-c">
    		<ul class="search w-100">
    			<li>
    				<select name="keyfield" class="search-cat">
    					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
    					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
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
	<div class="list-space flex-box f-end m-1">
    		<c:if test="${!empty user_num}">
    		<input type="button" value="글쓰기" onclick="location.href='cbob_writeForm.do'" class="btn-re btn-line-primary ">
    		</c:if>
    		<!-- <input type="button" value="목록" onclick="location.href='list.do'" class="btn-re ml-1"> -->
    	</div>
    		<c:if test="${count == 0}">
    			<div class="result-display">
    				표시할 게시물이 없습니다.
    			</div>
    		</c:if>
    		<c:if test="${count > 0}">
    			<ul class="w-95 m-0auto">
             <c:forEach var="cbob" items="${list}">
                 <li class="mt-2">
                 <a href="cbob_detail.do?cbob_num=${cbob.cbob_num}">
                     <p class="fs-10 text-main">${cbob.cbob_num}</p>
                     <p class="fs-12 fw-700 mt-1">${cbob.cbob_title}</p>
                     <p class="fs-08 mt-1">${cbob.cbob_date}</p>
                     <p class="fs-08 text-r">조회수 | ${cbob.cbob_hit}</p>
                     <div class="bar pt-2"></div>
                 </a>    
                 </li>
             </c:forEach>
           </ul>
		            <div class="align-center mt-3">${page}</div>
		       </c:if>
    	</div>
    	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>
</body>
</html>