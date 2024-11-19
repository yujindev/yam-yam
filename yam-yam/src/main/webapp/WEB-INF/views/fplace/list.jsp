<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집랭킹</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<!-- 필터처리 추후 필요
<script type="text/javascript">
 	window.onload=function(){
 		const myForm = document.getElementById('search_form');
 		//이벤트 연결
 		myForm.onsubmit = function(){
 			const keyword = document.getElementById('keyword');
 			if(keyword.value.trim()==''){
 				alert('검색어를 입력하세요!');
 				keyword.value='';
 				keyword.focus();
 				return false;
 			}
 		};
 	};
</script>
-->
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2>맛집랭킹</h2>
		<!-- 필터 시작 -->
		<form action="search.do" method="get">
    <div class="filter-container">
        <div class="filter">
            <button type="button" class="filter-btn">전체</button>
            <label><input type="checkbox" name="fp_filter1" value="식사">식사</label>
            <label><input type="checkbox" name="fp_filter1" value="요리">요리</label>
            <label><input type="checkbox" name="fp_filter1" value="간식">간식</label>
        </div>
        <div class="filter">
            <button type="button" class="filter-btn">전체</button>
            <label><input type="checkbox" name="fp_filter2" value="한식">한식</label>
            <label><input type="checkbox" name="fp_filter2" value="중식">중식</label>
            <label><input type="checkbox" name="fp_filter2" value="일식">일식</label>
            <label><input type="checkbox" name="fp_filter2" value="양식">양식</label>
            <label><input type="checkbox" name="fp_filter2" value="아시안">아시안</label>
        </div>
        <div class="filter">
            <button type="button" class="filter-btn">전체</button>
            <label><input type="checkbox" name="fp_filter3" value="혼밥">혼밥</label>
            <label><input type="checkbox" name="fp_filter3" value="친구">친구</label>
            <label><input type="checkbox" name="fp_filter3" value="연인">연인</label>
            <label><input type="checkbox" name="fp_filter3" value="가족">가족</label>
            <label><input type="checkbox" name="fp_filter3" value="모임">모임</label>
        </div>
    </div>
    <button type="submit">검색</button>
</form>
		
		<!-- 필터 끝 -->
		<!-- 목록 생성 -->
		<div class="list-space align-right">
		<c:if test="${!empty user_num && user_auth == 9}">
		<input type="button" value="식당등록" onclick="location.href='writeForm.do'">
		</c:if>
		</div>
			<c:if test="${count == 0}">
			<div class="result-display">
			 	표시할 식당정보가 없습니다.
			</div>
			</c:if>
		<c:if test="${count > 0}">
		<table class="ranking-table">
    		<c:forEach var="fplace" items="${list}">
	        <tr class="table-row">
	            <!-- 랭킹 번호 및 이미지 -->
	            <td>
	            <div class="rank-cell">랭킹번호 어쩌구~~ </div>
	            </td>
	              <%-- 랭킹 번호 필요   
	              <div class="rank-number">${fplace.fp_rank}</div>  --%>
	            <td>
	                <img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" 
	                     alt="${fplace.fp_name}" 
	                     class="image-cell">
	            </td>
	            
	            <!-- 가게 정보 -->
	            <td class="details-cell">
	                <p class="category">${fplace.fp_filter1}, ${fplace.fp_filter2}, ${fplace.fp_filter3}</p>
	                <p class="name">
	                <a href="detail.do?fp_num=${fplace.fp_num}">${fplace.fp_name}</a></p>
	               <%-- 별점, 리뷰개수 필요 
	               		<div class="rating">
	                    <span class="store-stars">⭐ ${fplace.fp_??}</span>
	                    <span class="store-reviews">리뷰 ${fplace.fp_reviews}개</span> 
	                </div>
	                --%> 
	            </td>
	            <!-- 북마크 -->
	            <td class="bookmark-cell">
	                <div class="bookmark-icon">북마크</div>
	            </td>
       		</tr>
    </c:forEach>
</table>
</c:if>
		<!-- 목록 끝 -->
		</div>
	</div>
</body>
</html>