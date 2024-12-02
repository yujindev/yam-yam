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
<style>
main, .content-main {
    max-width: 1200px;
    margin: 0 auto;
    min-height: 100vh;
    padding-top: 10px; /* 상단 여백 조정 */
}

.container {
    width: 80%; /* 부모 요소 기준 80% */
    max-width: 1200px; /* 최대 너비 제한 */
    height: 400px; /* 고정 높이 */
    padding: 20px; /* 내부 여백 */
    margin: 0 auto; /* 중앙 정렬 */
    background-color: #f9f9f9; /* 배경색 */
    border: 1px solid #ddd; /* 테두리 */
    border-radius: 10px; /* 모서리 둥글게 */
}

 /* 메인 컨테이너 */
.main-container {
    display: flex;
    gap: 20px;
    justify-content: space-between;
    padding: 20px 0;
}

/* 하단 컨테이너 */
.bottom-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: space-between;
}

/* 섹션 공통 스타일 */
/* 룰렛*/
.roulette-section {
    height: 400px; /* 룰렛 섹션의 높이를 원하는 대로 설정 */
}
/* 커뮤니티*/
.community-section {
    /* 필요하면 커뮤니티 섹션에 다른 높이나 스타일 추가 */
    height: 400px; /* 커뮤니티 섹션은 기본 높이를 유지 */
}
/*축제*/
.banner-section{
	height: 500px; 
	top: -200px; /* 위로 20px 올리기 */
}
/*맛집추천*/
.restaurant-recommendation-section{
	height: 800px; 
	top: -20px; /* 위로 20px 올리기 */
}
.section {
    flex: 1;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 10px;
    background-color: #fff;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

/* 섹션 헤더 스타일 */
.section h2 {
    margin-bottom: 15px;
    font-size: 20px;
    text-align: center;
    color: #333;
}

/* 커뮤니티 섹션 */
.community-section ul {
    list-style: none;
    padding: 0;
}

.community-section ul li {
    margin-bottom: 10px;
    font-size: 14px;
    color: #666;
}

/* 맛집 추천 섹션 */
.roulette-section img {
    max-height: 350px; /* 이미지의 최대 높이를 제한 */
    margin: 0 auto; /* 중앙 정렬 */
    display: block; /* 블록 요소로 설정 */
    top:-20px;
}

.restaurant-recommendation {
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
    justify-content: space-around;
}

.restaurant-card {
    flex: 1 1 calc(35% - 20px); /* 2열 레이아웃 */
    text-align: center;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 10px;
    background-color: #f9f9f9;
    max-width: 250px; /* 카드의 최대 너비 */
    height: 200px;
}

.restaurant-card img {
    width: 100%; /* 부모 요소의 너비에 맞춤 */
    aspect-ratio: 4 / 3; /* 4:3 비율 유지 */
    object-fit: cover; /* 이미지가 카드 영역에 꽉 차도록 조정 */
    border-radius: 5px; /* 모서리 둥글게 */
    margin-bottom: 5px;
}

.restaurant-name {
    font-size: 14px;
    font-weight: bold;
    color: #333;
}

/* 축제 이미지 배너 섹션 */
.banner-section .festival-slider {
    display: flex;
    justify-content: center;
    gap: 10px;
}

.banner-section .festival-slider .festival-card {
    width: 100%; /* 고정된 너비 */
    height: 450px; /* 고정된 높이 */
    overflow: hidden; /* 이미지가 영역을 벗어나지 않도록 */
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 8px; /* 둥근 모서리 */
    background-color: #f9f9f9; /* 이미지가 없는 경우 배경색 */
}

.banner-section .festival-slider .festival-card img {
    width: 100%; /* 부모 컨테이너 너비에 맞춤 */
    height: 100%; /* 부모 컨테이너 높이에 맞춤 */
    object-fit: cover; /* 이미지 비율 유지하며 컨테이너 채움 */
    border-radius: 8px; /* 이미지에 둥근 모서리 적용 */
}

/* 반응형 디자인 */
@media (max-width: 768px) {
    .main-container {
        flex-direction: column;
    }

    .bottom-container {
        flex-direction: column;
    }
}


</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
    <div class="content-main">

      <div class="page-main">
    <div class="content-main">

        <!-- 메인 컨테이너 -->
        <div class="main-container">
            <!-- 룰렛 섹션 -->
            <div class="section roulette-section" >
                <h2>룰렛</h2>
                <a href="${pageContext.request.contextPath}/tmenu/roulette.do" class="roulette-link">
                    <img src="${pageContext.request.contextPath}/images/roulette.jpeg" alt="룰렛 이미지">
                </a>
            </div>

            <!-- 커뮤니티 섹션 -->
            <div class="section community-section">
                <h2>커뮤니티</h2>
                <ul>
        			<c:forEach var="board" items="${list3}">
            			<li>
                		<strong>${board.category}</strong> - 
                			<a href="${pageContext.request.contextPath}/${board.tableUrl}_detail.do?${board.tableUrlNum}=${board.board_num}">
                   			${board.board_title}
                			</a>
            			</li>
        			</c:forEach>
   		 		</ul>
            </div>
        </div>

        <!-- 하단 컨테이너 -->
        <div class="bottom-container">
            <!-- 맛집 추천 섹션 -->
            <div class="section restaurant-recommendation-section">
                <h2>맛집 추천</h2>
                <div class="restaurant-recommendation">
                    <c:if test="${count > 0}">
                        <c:forEach var="fplace" items="${list}">
                            <div class="restaurant-card">
                                <a href="${pageContext.request.contextPath}/fplace/detail.do?fp_num=${fplace.fp_num}">
                                    <img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" alt="${fplace.fp_name}">
                                </a>
                                <p class="restaurant-name">${fplace.fp_name}</p>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${count == 0}">
                        <p>추천할 맛집이 없습니다.</p>
                    </c:if>
                </div>
            </div>

            <!-- 축제 이미지 배너 섹션 -->
            <div class="section banner-section">
                <h2>축제 정보</h2>
                <div class="festival-slider">
                    <c:forEach var="dopamine" items="${list2}">
                        <div class="slide">
                            <div class="festival-card">
                                <img src="${pageContext.request.contextPath}/upload/${dopamine.dp_file}" alt="축제정보">
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
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
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
