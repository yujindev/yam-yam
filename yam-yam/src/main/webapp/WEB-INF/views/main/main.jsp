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
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<div class="content-main">

	 <!-- 룰렛 섹션 -->
    <div class="roulette-section">
    	<h2>룰렛</h2>
    	<a href="${pageContext.request.contextPath}/tmenu/roulette.do" class="roulette-link">
        	<img src="${pageContext.request.contextPath}/images/roulette.png" alt="룰렛 이미지">
        </a>
    </div>

    <!-- 커뮤니티 섹션 -->
    <div class="community-section">
        <h2>커뮤니티</h2>
        <ul>
            <li>오늘의 인기글 제목 예시입니다.</li>
            <li>오늘의 인기글 제목 예시입니다.</li>
            <li>오늘의 인기글 제목 예시입니다.</li>
            <li>오늘의 인기글 제목 예시입니다.</li>
        </ul>
    </div>
	
	<!-- 식당추천 -->
	<h2 class="section-title">맛집 추천</h2>
	<c:if test="${count > 0}">
		<c:forEach var="fplace" items="${list}">
			<div class="restaurant-recommendation">
			    <div class="restaurant-grid">
			        <div class="restaurant-card">
			        	<a href="${pageContext.request.contextPath}/fplace/detail.do?fp_num=${fplace.fp_num}"></a>
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
	
	<!-- 축제 이미지 배너 섹션 -->
    <div class="banner-section">
        <h2>축제 이미지 배너 슬라이드</h2>
        <!-- 축제 배너 슬라이드 이미지 추가 -->
    <!-- 축제 이미지 배너 슬라이드 -->
    <div class="festival-slider">
        <c:forEach var="dopamine" items="${list2}">
            <div class="slide">
                <div class="festival-card">
                   
                    <img src="${pageContext.request.contextPath}/upload/${dopamine.dp_file}" alt="축제정보" class="dopamie-cell">
                </div>
            </div>
        </c:forEach>
    </div>
</div>


<script>
$(document).ready(function () {
    let currentIndex = 0;
    const slides = $('.festival-slider .slide');
    const totalSlides = slides.length;

    // 슬라이드가 없을 경우 처리
    if (totalSlides === 0) {
        console.warn("No slides available.");
        return;
    }

    // 특정 슬라이드 표시
    function showSlide(index) {
        slides.removeClass('active'); // 모든 슬라이드 숨김
        slides.eq(index).addClass('active'); // 현재 슬라이드만 표시
    }

    // 다음 슬라이드로 이동
    function nextSlide() {
        currentIndex = (currentIndex + 1) % totalSlides;
        showSlide(currentIndex);
    }

    // 초기화
    showSlide(currentIndex);

    // 3초마다 슬라이드 변경
    setInterval(nextSlide, 3000);
});


</script>
    
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>