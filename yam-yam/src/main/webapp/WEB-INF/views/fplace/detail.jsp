<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.reply.js"></script> -->
 
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>식당정보</h2>
		<!-- 필터노출 -->
		<p>
		${fplace.fp_filter1}, ${fplace.fp_filter2}, ${fplace.fp_filter3}
		</p>
		<br>
		<!-- 식당이미지 -->
		<div class="storimg">
		 <img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" width="400">
		</div>
		
		<!-- 식당정보 -->
		<li>
			<%--북마크
			<img id="output_fav" data-num="${board.board_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
			북마크
			<span id="output_fcount"></span>--%>
		</li>
		<h2>${fplace.fp_name}</h2>
		<br>	
		<%--리뷰 별 노출 >
		<p>
		${fplace.fp_reviews}
		</p>--%>
		<!-- 지도보기 , 예약버튼 생성 -->
		<%-- onlink 연결해야함  --%>
		<input type="button" value="지도보기">
		<input type="button" value="예약">
		</div>
		
		<p>${fplace.fp_loc}</p>
		<br>
		<p>${fplace.fp_time}</p>
		<br>
		<p>${fplace.fp_phone}</p>

		<br>
		<!-- 메뉴, 지도 -->
		<!-- 로그인한 회원이 관리자면 수정, 삭제 가능 -->
				<c:if test="${!empty user_num && user_auth == 9}">
				<input type="button" value="수정" onclick="location.href='updateForm.do?fp_num=${fplace.fp_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					const delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('delete.do?fp_num=${fplace.fp_num}');
						}
					}
					
				</script>
		</c:if>
	</div>
</div>	
</body>
</html>