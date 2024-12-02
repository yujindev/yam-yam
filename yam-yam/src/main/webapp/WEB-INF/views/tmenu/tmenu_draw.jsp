<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>제비뽑기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tmenu.draw.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
	<h2>제비뽑기</h2>
   <div id="canvas-container">
    <canvas id="name-canvas" width="400" height="400"></canvas>
    <img id="shuffle-gif" src="../images/tmenudraw2.gif" style="align-content:center; display: none; width: 500px; height: 500px;" alt="섞는 중">
    <div class="random-button-container">
        <button id="pick-draw" class="random-button">제비뽑기</button>
        <button id="refresh-menu" class="random-button">메뉴 바꾸기</button>
    </div>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
