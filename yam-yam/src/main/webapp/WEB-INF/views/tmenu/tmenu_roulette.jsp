<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tmenu.roulette.js"></script>
    <title>룰렛</title>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	 <!-- 룰렛 컨테이너 -->
    <div class="roulette-container">
        <!-- 화살표 이미지 -->
        <img id="arrow_image" src="${pageContext.request.contextPath}/images/arrow.png" alt="arrow">
        <!-- 캔버스 -->
        <canvas id="roulette_canvas" width="400" height="400"></canvas>
        <!-- 버튼 -->
        <br><br>
        <div class="radom-button-container">
            <button id="spin_roulette" class="random-button">룰렛 돌리기</button>
            <button id="refresh_menu" class="random-button">메뉴 바꾸기</button>
        </div>
        <!-- 결과 텍스트 
        <div id="result-display">
            <p id="result_text">결과는?</p>
        </div>
        -->
    </div>
</div>
</body>
</html>
