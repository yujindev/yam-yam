<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영상추천 게시판 목록</title>
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
    	<h2>영상추천 게시판 목록</h2>
    	<form action="list.do" id="search_form" method="get">
    		<ul class="search">
    			<li>
    				<select name="keyfield">
    					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
    					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>

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
    		<c:if test="${!empty user_num}">
    		<input type="button" value="글쓰기" onclick="location.href='cmov_writeForm.do'">
    		</c:if>
    		<input type="button" value="목록" onclick="location.href='list.do'">
    		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
    	</div>
    		<c:if test="${count == 0}">
    			<div class="result-display">
    				표시할 게시물이 없습니다.
    			</div>
    		</c:if>
    		<c:if test="${count > 0}">
    			<table>
		             <tr>
		                 <th>글번호</th>
		                 <th>제목</th>
		                 <th>작성일</th>
		                 <th>조회</th>
		             </tr>
		             <c:forEach var="cmov" items="${list}">
		                 <tr>
		                     <td>${cmov.cmov_num}</td>
		                     <td><a href="cmov_detail.do?cmov_num=${cmov.cmov_num}">${cmov.cmov_title}</a></td>
		                     <td>${cmov.cmov_date}</td>
		                     <td>${cmov.cmov_hit}</td>
		                 </tr>
		             </c:forEach>
		            </table>
		            <div class="align-center">${page}</div>
		       </c:if>
    	</div>
	</div>
</body>
</html>