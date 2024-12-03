<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티 - 전체 글 보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<script type="text/javascript">
    window.onload = function() {
        const myForm = document.getElementById('search_form');
        myForm.onsubmit = function() {
            const keyword = document.getElementById('keyword');
            if (keyword.value.trim() == '') {
                alert('검색어를 입력해주세요.');
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
    	<h2 class="fw-700 fs-16 m-1">전체 글 보기</h2>
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

            <!-- 게시물 목록 -->
            <c:if test="${count == 0}">
                <div class="result-display">
                    표시할 게시물이 없습니다.
                </div>
            </c:if>
            <c:if test="${count > 0}">
               <table>
		             <tr>
		                 <th>글번호</th>
		                 <th>카테고리</th>
		                 <th>제목</th>
		                 <th>작성일</th>
		                 <th>조회수</th>
		             </tr>
		             <c:forEach var="board" items="${list}">
		                 <tr>
		                     <td>${board.board_num}</td>
		                     <td>${board.category}</td>
			                  <td>
			                    <a href="${pageContext.request.contextPath}/${board.tableUrl}_detail.do?${board.tableUrlNum}=${board.board_num}">${board.board_title}</a>			                
			                </td>
		                     <td>${board.board_date}</td>
		                     <td>${board.board_hit}</td>		                     
		                 </tr>
		             </c:forEach>
		            </table>
                  <div class="align-center">${page}</div>
            </c:if>
            </div>
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	
	<jsp:include page="/WEB-INF/views/reserv/reservList.jsp" />
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
