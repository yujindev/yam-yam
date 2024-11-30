<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${member.mem_nickname}님의 프로필 </title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/YJ.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<main class="content-main">
			<jsp:include page="/WEB-INF/views/others/userProfile.jsp"/>
			<ul class="flex-box f-center pb-1">
				<li class="tab">
					<a href="${pageContext.request.contextPath}/others/saveStore.do?mem_id=${member.mem_id}">
					<img src='${pageContext.request.contextPath}/images/icon-flag-g.png' class='icon'>
					<span>찜한식당</span>
					
					</a>
				</li>
				<li class="tab selected">
				<a href="${pageContext.request.contextPath}/others/saveReview.do?mem_id=${member.mem_id}">
					<img src='${pageContext.request.contextPath}/images/icon-star-o.png' class='icon'>
					<span>찜한리뷰</span>
				</a>
				</li>
			</ul>
			<section class="w-80 m-0auto">
				<c:forEach var="reviews" items="${list}">
				<ul class="bg-gr150 p-2 mb-1">
					<li class="fw-700 fs-12">${reviews.fp_name}</li>
					<li>작성자 ${reviews.mem_nickname}</li>
					<li>${reviews.reviews_con}</li>
					<li class="text-r fs-08 text-gr400">작성일 : ${reviews.reviews_date}</li>
					<c:if test="${reviews.reviews_img1 !=null}">
					<li><img src="${pageContext.request.contextPath}/upload/${reviews.reviews_img1}" alt="${reviews.fp_name}" class="image-cell"></li>
					</c:if>
				</ul>
				</c:forEach>
			</section>
		</div>
	</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>