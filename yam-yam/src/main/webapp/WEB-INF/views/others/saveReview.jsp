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
	<div class="content-main">
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
			<c:if test="${count == 0}">
				<div class="mt-3 text-c">
					<p>${member.mem_nickname}님이 북마크한 리뷰가 없습니다.</p>
				</div>
			</c:if>
			<section class="w-80 m-0auto">
				<c:forEach var="reviews" items="${list}">
				<ul class="bg-gr150 p-2 mb-1">
					<li class="fw-700 fs-12">${reviews.fp_name}</li>
					<li class="mt-1 fw-500">
						<%-- <img src='${pageContext.request.contextPath}/images/icon-account.png' alt="개인프로필아이콘" class="w-05"> --%> 
						<span class="va-super">작성자 | ${reviews.mem_nickname}</span>
					</li>
					<%-- <li class="avg-score">⭐ ${fplace.fp_avgscore}</li> --%>
					<li class="mt-05">${reviews.reviews_con}</li>
					<c:if test="${reviews.reviews_img1 !=null}">
					<li class="mt-2"><img src="${pageContext.request.contextPath}/upload/${reviews.reviews_img1}" alt="${reviews.fp_name}" class="w-20"></li>
					</c:if>
					<li class="text-r fs-08 text-gr400 mt-1">작성일 : ${reviews.reviews_date}</li>
				</ul>
				</c:forEach>
			</section>
		</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>