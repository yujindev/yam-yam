<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="content-main">
	<h4>메인 페이지</h4>
	<!-- 식당추천 -->
	<h2 class="section-title">맛집 추천</h2>
	<c:if test="${count > 0}">
		<c:forEach var="fplace" items="${list}">
			<div class="restaurant-recommendation">
			    <div class="restaurant-grid">
			        <div class="restaurant-card">
			        	<a href="${pageContext.request.contextPath}/fplace/detail.do?fp_num=${fplace.fp_num}">
			            <img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" alt="${fplace.fp_name}" class="image-cell">
			            <p class="restaurant-name">${fplace.fp_name}</p>
    				</div>
    			</div>
			</div>
		</c:forEach>
	</c:if>	
	<c:if test="${count == 0}">
		  <p>추천할 맛집이 없습니다.</p>
	</c:if>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>