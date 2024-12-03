<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tmenu.roulette.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
    <title>룰렛</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
	<div class="content-main">
	<h2 class="fw-700">룰렛</h2>
	 <!-- 룰렛 컨테이너 -->
    <div class="roulette-container">
        <!-- 화살표 이미지 -->
        <img id="arrow_image" src="${pageContext.request.contextPath}/images/arrow.png" alt="arrow">
        <!-- 캔버스 -->
        <canvas id="roulette_canvas" width="400" height="400"></canvas>
        <!-- 버튼 -->
        <div class="radom-button-container mt-3">
            <button id="spin_roulette" class="random-button btn-primary">룰렛 돌리기</button>
            <button id="refresh_menu" class="random-button btn-line-primary">메뉴 바꾸기</button>
        </div>
    </div>
   </div>
</div>
 <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
