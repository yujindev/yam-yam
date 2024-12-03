<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HR.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style type="text/css">
.dpcontent-main {
    padding: 3%;
    max-width: 1200px;
    margin: 0 auto;
    min-height: 100vh;
    width: 80%;
}
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="dpcontent-main">
		<h2 class="fw-700">${dopamine.dp_title}</h2>
		<hr size="1" noshade="noshade" width="100%">
		<article class="p-2">>
		<img src="${pageContext.request.contextPath}/upload/${dopamine.dp_file}" class="detail-img">
		
		<p>
			${dopamine.dp_content}
		</p>
		</article>
		<hr size="1" noshade="noshade" width="100%">
		<div class="flex-box f-center p-1">
				<c:if test="${user_auth == 9}">
				<input type="button" value="수정"
				   onclick="location.href='updateDpForm.do?dp_num=${dopamine.dp_num}'" class="btn btn-line-primary mr-1">
				<input type="button" value="삭제" id="delete_btn" class="btn btn-line-gray mr-1">
				<script type="text/javascript">
					const delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하겠습니까?');
						if(choice){
							location.replace('deleteDp.do?dp_num=${dopamine.dp_num}');
						}
					};
				</script>   
				</c:if>     
			</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>



