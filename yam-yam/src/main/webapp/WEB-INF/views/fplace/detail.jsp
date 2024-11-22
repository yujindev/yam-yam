<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fplace.fpmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fplace.reviews.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2>식당정보</h2>
			<!-- 필터노출 -->
			<p>${fplace.fp_filter1},${fplace.fp_filter2},
				${fplace.fp_filter3}</p>
			<br>
			<!-- 식당이미지 -->
			<div class="storimg">
				<img
					src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" width="400">
			</div>

			<!-- 식당정보 -->

			<%--가게 북마크--%>
			<h2>${fplace.fp_name}</h2>
			<br>
			
			<%--리뷰 별 노출--%>
			
			<!-- 지도보기 , 예약버튼 생성 -->
			<%-- onlink 연결해야함  --%>
			<input type="button" value="지도보기">
			 <input type="button" value="예약" onclick="location.href='reservForm.do?fp_num=${fplace.fp_num}'">

		<p>${fplace.fp_loc}</p>
		<br>
		<p>${fplace.fp_time}</p>
		<br>
		<p>${fplace.fp_phone}</p>

		<br>
		<!-- 메뉴, 지도 -->
		<!-- 메뉴 시작 -->
		<h2 class="menu-title">메뉴 정보</h2>
		<script type="text/javascript">
				$(document).on('click', '#fpmenu-write-btn', function() {
									const userNum = '${user_num}'; // 서버에서 로그인 여부를 가져옴
									if (!userNum) {
										//비로그인 상태라면 로그인 페이지로 리다이렉트
										alert('로그인 후 이용할 수 있습니다.');
										window.location.href = '${pageContext.request.contextPath}/member/loginForm.do';
										return;
									}else{
									// 로그인 상태라면 리뷰 작성 폼 표시 
									$('#menu-form').show();
									}
								});

				// 식당정보 작성 취소 버튼 클릭 시 폼 숨기기
				$(document).on('click', '#fpmenu-cancel-btn', function() {
					$('#menu-form').hide();
					$('#fpmenu_name').val('');
					$('#fpmenu_price').val('');
					$('#fpmenu_img').val(''); // 폼 초기화
				});
			</script>
		<!-- 메뉴 작성 버튼 -->
		<c:if test="${!empty user_num && user_auth == 9}">
			<div class="list-space align-right">
				<button id="fpmenu-write-btn">메뉴 등록</button>
			</div>
		</c:if>
		<!-- 메뉴 작성 폼 시작 -->
		<div id="menu-form" style="display: none;">

			<form id="fpmenu_form" enctype="multipart/form-data" method="post">
				<input type="hidden" name="fp_num" value="${fplace.fp_num}"
					id="fp_num">
				<div class="form-group">
					<label for="fpmenu_name">메뉴 이름</label> <input type="text"
						name="fpmenu_name" id="fpmenu_name" class="input-check">
				</div>
				<div class="form-group">
					<label for="fpmenu_price">가격</label> <input type="number"
						name="fpmenu_price" id="fpmenu_price" min="1" max="999999999"
						class="input-check">
				</div>
				<div class="form-group">
					<label for="fpmenu_img">이미지</label> <input type="file"
						name="fpmenu_img" id="fpmenu_img" accept="image/*">
				</div>
				<div id="menu_buttons" class="align-right">
					<input type="submit" value="등록"> <input type="button"
						value="취소" id="fpmenu-cancel-btn">
				</div>
			</form>
		</div>
		<!-- 메뉴 작성 폼 끝 -->

		<!-- 메뉴 목록 출력 시작 -->
		<div id="menu_output"></div>
		<div class="menu-paging-button" style="display: none;">
			<input type="button" value="다음 메뉴 보기">
		</div>


		<div id="loading" style="display: none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif"
				width="50" height="50">
		</div>
		<!-- 메뉴 끝 -->
		
		<!-- 지도 시작 -->
		<div id="fpmap" style="width:100%;height:500px;"></div>
		<!-- 지도  끝 -->

		<!-- 로그인한 회원이 관리자면 수정, 삭제 가능 -->
		<c:if test="${!empty user_num && user_auth == 9}">
			<input type="button" value="식당 정보 수정"
				onclick="location.href='updateForm.do?fp_num=${fplace.fp_num}'">
			<input type="button" value="식당 정보 삭제" id="delete_btn">
			<script type="text/javascript">
				const delete_btn = document.getElementById('delete_btn');
				//이벤트 연결
				delete_btn.onclick = function() {
					let choice = confirm('삭제하시겠습니까?');
					if (choice) {
						location.replace('delete.do?fp_num=${fplace.fp_num}');
					}
				}
			</script>
		</c:if>
		</div>

	
	
	
<!-- 지도 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e934e501c1a7405a592b156df3147ed7"></script>
<script type="text/javascript">
    var lat = 37.533836, lon = 126.896893;

   	//지도를 표시할 div
       var mapContainer = document.getElementById('fpmap'),
       mapOption = {
           center : new daum.maps.LatLng(lat,lon),//지도의 중심좌표
           level : 3 // 지도의 확대 레벨
       };

       //지도생성
       var map = new daum.maps.Map(mapContainer,mapOption);

       //마커가 표시될 위치
       var position = new daum.maps.LatLng(lat,lon);

       //마커 생성
       var marker = new daum.maps.Marker({
           position:position,
           clickable : true //마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정

       });

       //마커를 지도에 표시 
       marker.setMap(map);

       //마커를 클릭했을 때 마커 위에 표시할 인포윈도우 UI 
       var iwContent = '<div style="padding :10px;width:250px;height:38px;">' + lat + ', ' + lon + '<br>나 여기 있어요!!</div>', 
       iwRemoveable = true; //인포윈도우를 닫을 수 있는  x버튼이 표시

       //인포윈도우 생성
       var infowindow = new daum.maps.InfoWindow({
           content : iwContent,
           removeable : iwRemoveable
       });

       //마커에 클릭이벤트 등록
       daum.maps.event.addListener(marker,'click',function(){
           //마커 위에 인포윈도우 표시
           infowindow.open(map,marker);
       });
</script>
<!-- 리뷰 -->
<script type="text/javascript">
$(document).on('click', '#review-write-btn', function () {
     const userNum = '${user_num}'; // 서버에서 로그인 여부를 가져옴
     if (!userNum) { 
		//비로그인 상태라면 로그인 페이지로 리다이렉트
		alert('로그인 후 이용할 수 있습니다.');
        window.location.href = '${pageContext.request.contextPath}/member/loginForm.do';
        return;
	}

	// 로그인 상태라면 리뷰 작성 폼 표시 
 	$('#reply_div').show();
 	});

// 리뷰 작성 취소 버튼 클릭 시 폼 숨기기
$(document).on('click', '#review-cancel-btn', function () {
	$('#reply_div').hide();
     initForm(); // 폼 초기화
 });
</script>
		<!-- 리뷰 갯수 -->
		<div class="align-left">
			<span></span>
		</div>
		<!-- 리뷰 작성 버튼 -->
		<div class="list-space align-right">
			<button id="review-write-btn">리뷰 작성</button>
		</div>
		<!-- 리뷰 작성 시작	 -->
			<div id="reply_div" style="display: none;">
				<span class="reviews-title">리뷰 작성</span>
				<form id="reviews_form" enctype="multipart/form-data">
					<input type="hidden" name="fp_num" value="${fplace.fp_num}" id="fp_num">
					
        			<h3>${fplace.fp_name}</h3>
        			
			        <div class="rating">
			            <span>별점</span>
			            <div class="stars">
			                <input type="radio" id="star1" name="reviews_score" value="1">
			                <label for="star1" title="1점">★</label>
			                <input type="radio" id="star2" name="reviews_score" value="2">
			                <label for="star2" title="2점">★</label>
			                <input type="radio" id="star3" name="reviews_score" value="3">
			                <label for="star3" title="3점">★</label>
			                <input type="radio" id="star4" name="reviews_score" value="4">
			                <label for="star4" title="4점">★</label>
			                <input type="radio" id="star5" name="reviews_score" value="5">
			                <label for="star5" title="5점">★</label>
			            </div>
			        </div>
			        어떤 점이 좋았나요?<!-- reviews_con -->
					<textarea rows="5" cols="50" name="reviews_con" id="reviews_con" class="rep-content" 
					   <c:if test="${empty user_num}">disabled="disabled"</c:if>
					  ><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>   
					<c:if test="${!empty user_num}">
					<div id="reviews_first">
						<span class="letter-count">300/300</span>
					</div>
					
					 <div class="photo-upload">
		                <label for="reviews_img1">사진 :</label>
		                <input type="file" id="reviews_img1" name="reviews_img1" accept="image/*">
		            </div>
		             
					 <div id="re_second" class="align-right">
		                <input type="submit" value="등록">
		                <input type="button" value="취소" id="review-cancel-btn">
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
			
			<!-- 북마크 어디다가 넣지?? -->                                    
			<%-- 가게 북마크 --%> 
<%-- 		<img id="output_bmstore" data-num="${fplace.fp_num}" --%>
<%-- 		src="${pageContext.request.contextPath}/images/fav01.gif" width="50"> --%>
<!-- 			북마크<span id="output_bscount"></span>       -->
	</div>
</body>
</html>