<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Bm.bookmark.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fplace.reviews.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
		<!-- 게시판 내용 시작 -->
		
		<!-- 게시판 내용 끝 -->
			<!-- 리뷰 작성 시작 -->	
			<div id="reply_div">
				<span class="reviews-title">리뷰 작성</span>
				<form id="reviews_form" enctype="multipart/form-data">
					<input type="hidden" name="fp_num" value="${fplace.fp_num}" id="fp_num">
					
					<!-- 음식점 제목 -->
        			<h3>${fplace.fp_name}</h3>
        			 <!-- 별점 선택 -->
			        <div class="rating">
			            <span>별점</span>
			            <div class="stars">
			                <input type="radio" id="star5" name="reviews_score" value="5">
			                <label for="star5" title="5점">★</label>
			                <input type="radio" id="star4" name="reviews_score" value="4">
			                <label for="star4" title="4점">★</label>
			                <input type="radio" id="star3" name="reviews_score" value="3">
			                <label for="star3" title="3점">★</label>
			                <input type="radio" id="star2" name="reviews_score" value="2">
			                <label for="star2" title="2점">★</label>
			                <input type="radio" id="star1" name="reviews_score" value="1">
			                <label for="star1" title="1점">★</label>
			            </div>
			        </div>
			        <!-- 리뷰 내용 입력 -->
					<textarea rows="5" cols="50" name="reviews_content" id="reviews_content" class="rep-content"
					   <c:if test="${empty user_num}">disabled="disabled"</c:if>
					  ><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>   
					<c:if test="${!empty user_num}">
					<div id="reviews_first">
						<span class="letter-count">300/300</span>
					</div>
					<!-- 사진 업로드 -->
					 <div class="photo-upload">
		                <label for="reviews_img1">사진 1:</label>
		                <input type="file" id="reviews_img1" name="reviews_img1" accept="image/*">
		                <label for="reviews_img2">사진 2:</label>
		                <input type="file" id="reviews_img2" name="reviews_img2" accept="image/*">
		            </div>
		             <!-- 전송 버튼 -->
					 <div id="re_second" class="align-right">
		                <input type="submit" value="등록">
		                <input type="button" value="취소" onclick="history.back();">
					</div>
					</c:if>
				</form>
			</div>
			<!-- 리뷰 작성 끝 -->
			<!-- 리뷰 목록 출력 시작 -->
			<div id="output"></div>
			<div class="paging-button" style="display:none;">
				<input type="button" value="다음글 보기">
			</div>
			<div id="loading" style="display:none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
			</div>
			<!-- 리뷰 목록 출력 끝 -->
		</div>
	</div>
</body>
</html>