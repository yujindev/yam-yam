<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집랭킹</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SJ.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fplace.Bmstore2.js"></script>


</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<div class="content-main">
			<h2 class="fw-700">맛집랭킹</h2>
		<!-- 필터 시작 -->
		<form action="list.do" method="get" class="w-60 m-0auto">
    <div class="filter-container">
        <div class="filter flex-box">
            <button type="button" class="btn-re filter-btn br-1 mr-1">전체</button>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter1" value="식사">식사</label>
            <label class=" mt-05"><input type="checkbox" name="fp_filter1" value="간식">간식</label>
        </div>
        <div class="filter flex-box">
            <button type="button" class="btn-re filter-btn br-1 mr-1">전체</button><br>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter2" value="한식">한식</label>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter2" value="중식">중식</label>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter2" value="일식">일식</label>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter2" value="양식">양식</label>
            <label class="mt-05"><input type="checkbox" name="fp_filter2" value="아시안">아시안</label>
        </div>
        <div class="filter flex-box">
            <button type="button" class="btn-re filter-btn br-1 mr-1">전체</button>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter3" value="혼밥">혼밥</label>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter3" value="친구">친구</label>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter3" value="연인">연인</label>
            <label class="mr-1 mt-05"><input type="checkbox" name="fp_filter3" value="가족">가족</label>
            <label class=" mt-05"><input type="checkbox" name="fp_filter3" value="모임">모임</label>
        </div>
    </div>
    <button type="submit" class="block-box ml-auto">검색</button>
</form>
		
		<!-- 필터 끝 -->
		<!-- 목록 생성 -->
		
		
		<c:if test="${count == 0}">
			<div class="result-display">
			 	표시할 식당정보가 없습니다.
			</div>
		</c:if>
		
		<c:if test="${count > 0}">
			<table class="ranking-table">
    			<c:forEach var="fplace" items="${list}">
	        		<tr class="table-row">
	            		 <!-- 순위 -->
	            		<td>
	                		<div class="rank-number">${fplace.rank}</div>
	            		</td>
						<!-- 가게이미지 -->
	            		<td>
	                		<img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" alt="${fplace.fp_name}" class="image-cell">
	            		</td>
	            
	            		<!-- 가게 정보 -->
			            <td class="details-cell">
			                <p class="category">${fplace.fp_filter1}, ${fplace.fp_filter2}, ${fplace.fp_filter3}</p>
			                <p class="name">
			                	<a href="detail.do?fp_num=${fplace.fp_num}">${fplace.fp_name}</a>
			                </p>
			                <p class="avg-score">⭐ ${fplace.fp_avgscore}</p> 
			                <p class="r-count">리뷰 ${fplace.reviews_count}개</p> <!-- !!vo랑 같아야 함!! -->
			            </td>
			            
	           			 <!-- 북마크 -->
			            <td>
			            	<c:if test="${fplace.isBookmarked == 0}"><img class="output_bmstore" data-num="${fplace.fp_num}" src="${pageContext.request.contextPath}/images/icon-flag-g.png" width="auto"></c:if>
			                <c:if test="${fplace.isBookmarked > 0}"><img class="output_bmstore" data-num="${fplace.fp_num}" src="${pageContext.request.contextPath}/images/icon-flag-o.png" width="auto"></c:if>
			            </td>
       				</tr>
   			 	</c:forEach>
			</table>
			<div class="align-center">${page}</div>
		</c:if>
		<!-- 목록 끝 -->
		</div>
	</div>
<jsp:include page="/WEB-INF/views/reserv/reservList.jsp" />
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>