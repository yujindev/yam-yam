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
				<li class="tab selected">
					<a href="${pageContext.request.contextPath}/others/saveStore.do?mem_id=${member.mem_id}">
					<img src='${pageContext.request.contextPath}/images/icon-flag-g.png' class='icon'>
					<span>찜한식당</span>
					</a>
				</li>
				<li class="tab">
				<a href="${pageContext.request.contextPath}/others/saveReview.do?mem_id=${member.mem_id}">
					<img src='${pageContext.request.contextPath}/images/icon-star-o.png' class='icon'>
					<span>찜한리뷰</span>
				</a>
				</li>
			</ul>
			<c:if test="${count == 0}">
				<div class="mt-3 text-c">
					<p>${member.mem_nickname}님이 북마크한 식당이 없습니다.</p>
					<button onclick="location.href ='${pageContext.request.contextPath}/fplace/list.do'" class="block-box m-0auto btn btn-primary mt-2">식당찾기</button>
				</div>
			</c:if>
			<c:if test="${count == 1}">
			<c:forEach var="fplace" items="${list}">
			<ul class="bg-gr300 pb-1">
				<li>음식점 이름 : ${fplace.fp_name}</li>
				<li>연락처 : ${fplace.fp_phone}</li>
				<li>영업시간 : ${fplace.fp_time}</li>
				<li>위치 : ${fplace.fp_loc}</li>
				<li><img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" alt="${fplace.fp_name}" class="image-cell"></li>
			</ul>
				</c:forEach>
			</c:if>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>

</body>
</html>