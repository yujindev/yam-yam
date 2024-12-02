<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SJ.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/image-scroll.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fplace.fpmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fplace.reviews.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/image.scroll.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fplace.Bmstore.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<div class="content-main">
			<h2>식당정보</h2>
			
			<!-- 로그인한 회원이 관리자면 식당정보 수정, 삭제 가능 -->
			<c:if test="${!empty user_num && (user_auth == 9 || (user_auth == 7 && fplace.mem_num == user_num))}">
				<input type="button" value="식당 정보 수정"
					onclick="location.href='updateForm.do?fp_num=${fplace.fp_num}'">
			</c:if>
			<c:if test="${!empty user_num && user_auth == 9}">
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
			<!-- 식당정보 수정, 삭제 끝 -->

			<!-- 식당정보 -->
			
			<!-- 필터 -->	
			<p>${fplace.fp_filter1},${fplace.fp_filter2},
				${fplace.fp_filter3}</p>
			<br>
			
			<!-- 식당이미지 -->
			<div class="storimg">
				<img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" width="400">
			</div>

			<%--가게 북마크--%>
			<div class="bookmark-container">
				<img class="output_bmstore" data-num="${fplace.fp_num}"
					src="${pageContext.request.contextPath}/images/icon-flag-g.png.gif"
					width="auto">
				<h2>${fplace.fp_name}</h2>
			</div>
			<br>

			<%--리뷰 별 노출--%>

			<!-- 지도보기 , 예약 -->
			<button id="fpmenu-map-btn">길찾기</button>
			<input type="button" value="예약" onclick="location.href='reservForm.do?fp_num=${fplace.fp_num}'">
			<!-- 식당 상세 정보 -->	
			<p>${fplace.fp_loc}</p>
			<br>
			<p>${fplace.fp_time}</p>
			<br>
			<p>${fplace.fp_phone}</p>
			<br>
		
			<!-- 메뉴 시작 -->
			<h2 class="menu-title">메뉴 정보</h2>
			<script type="text/javascript">
				$(document).on('click', '#fpmenu-write-btn', function() {
					const userNum = '${user_num}'; // 서버에서 로그인 여부를 가져옴
					const userAuth = '${user_auth}';//사용자권한
					const memNum = '${fplace.mem_num}'; // 작성자의 사용자 ID 가져옴 (서버에서 전달)
					if (!userNum) {
						//비로그인 상태라면 로그인 페이지로 리다이렉트
						alert('로그인 후 이용할 수 있습니다.');
						window.location.href = '${pageContext.request.contextPath}/member/loginForm.do';
						return;
					}else if(userNum == memNum || userAuth == 9){
					// 로그인 상태라면 메뉴 작성 폼 표시 
					$('#menu-form').show();
					}else{
						alert('작성할 권한 없음')
					}
				});

				// 메뉴정보 작성 취소 버튼 클릭 시 폼 숨기기
				$(document).on('click', '#fpmenu-cancel-btn', function() {
					$('#menu-form').hide();
					$('#fpmenu_name').val('');
					$('#fpmenu_price').val('');
					$('#fpmenu_img').val(''); // 폼 초기화
				});
			</script>

			<!-- 메뉴 작성 버튼 -->
			<c:if test="${!empty user_num && (user_auth == 9 || user_num == fplace.mem_num)}">
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
        <div class="poster" style="display:flex;justify-content:center;">
	        <div class="poster-main" style="width:60%;">
	            <div class="place-xy scroll">
	              <div id="post-scroll" class="slider"></div>
	            </div>
	            <br><br>
	            <div class="btn-left left" style="display: none;">
	                <button type="button" class="poster-btn">
	                	<span>&lt;</span>
	                </button>
	            </div>
	            <div class="btn-right right">
	                <button type="button" class="poster-btn">
	                	<span>&gt;</span>    
	                </button>
	            </div>
	        </div>
        </div>
      <!-- 메뉴 끝 -->


		<!-- 지도 시작 -->
		<div id="mapsize" style="display:flex;justify-content:center;">
		<div id="fpmap" style="width: 70%; height: 500px;"></div>
		</div>	<!-- content main div 끝 -->
		</div>
		<!-- 지도 -->
		<script type="text/javascript"
			src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e934e501c1a7405a592b156df3147ed7&libraries=services,clusterer,drawing"></script>
		<script type="text/javascript">
			var lat, log; 
			
			var mapContainer = document.getElementById('fpmap'),mapOption={
					center: new kakao.maps.LatLng(37.500667213345366,127.03646041771586),
					level:3
			};
			
			//지도생성
			var map = new kakao.maps.Map(mapContainer,mapOption);
			
			//주소-좌표 변환 객체 생성
			var geocoder = new kakao.maps.services.Geocoder();
			
			//주소로 좌표 검색
			geocoder.addressSearch('${fplace.fp_loc}', function(result,status){
				//검색 완료
				if(status == kakao.maps.services.Status.OK){
					console.log('lat: ' + result[0].y);
					console.log('log: ' + result[0].x);
					
					lat = result[0].y;
					log = result[0].x;
					
					var coords = new kakao.maps.LatLng(lat, log)
					
					//결과값으로 받은 위치를 마커로 표시
					var marker = new kakao.maps.Marker({
						map: map,
						position: coords
					});
					
					//장소에 대한 설명 표시
					var infowindow = new kakao.maps.InfoWindow({
						content:'<div style="width:200px;text-align:center;padding:6px 0;">${fplace.fp_name} 위치</div>'
					});
					
					infowindow.open(map,marker);
					
					//지도의 중심을 결과값으로 받은 위치로 이동
					map.setCenter(coords);
				}
			});	
				//버튼 클릭시 길찾기 연결
				$(document).on('click', '#fpmenu-map-btn', function(){
						const kakaoMapUrl ='https://map.kakao.com/link/to/${fplace.fp_name},'+lat+','+log;
						//const kakaoMapUrl = `https://map.kakao.com/link/to/${fplace.fp_name},37.4988753075969,127.034612707642`;
				        window.open(kakaoMapUrl); // 새 창에서 열기
				        console.log('URL:', kakaoMapUrl);
			});			
			</script>
		<!-- 지도 끝 -->

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
		
		<!-- 리뷰 개수(안됨 dao에서 조인해야 할듯) -->
		<div class="align-left">
		<span> 리뷰${count}</span>
		
		</div>

		<!-- 리뷰 작성 버튼 -->
		<div class="list-space align-right">
			<button id="review-write-btn">리뷰 작성</button>
		</div>
		<!-- 리뷰 작성 시작	 -->
		<div id="reply_div" style="display: none;">
			<span class="reviews-title">리뷰 작성</span>
			<form id="reviews_form" enctype="multipart/form-data">
				<input type="hidden" name="fp_num" value="${fplace.fp_num}"
					id="fp_num">

				<h3>${fplace.fp_name}</h3>


					<div class="rating">
					<div class="stars">
						<input type="radio" id="star5" name="reviews_score" value="5">
						<label for="star5" class="star" title="5점">★</label> <input
							type="radio" id="star4" name="reviews_score" value="4"> <label
							for="star4" class="star" title="4점">★</label> <input type="radio"
							id="star3" name="reviews_score" value="3"> <label
							for="star3" class="star" title="3점">★</label> <input type="radio"
							id="star2" name="reviews_score" value="2"> <label
							for="star2" class="star" title="2점">★</label> <input type="radio"
							id="star1" name="reviews_score" value="1"> <label
							for="star1" class="star" title="1점">★</label>
					</div>
				</div>

				어떤 점이 좋았나요?
				<!-- reviews_con -->
				<textarea rows="5" cols="50" name="reviews_con" id="reviews_con" class="rep-content"
					<c:if test="${empty user_num}">disabled="disabled"</c:if>>
					<c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if>
				</textarea>
				<c:if test="${!empty user_num}">
					<div id="reviews_first">
						<span class="letter-count">300/300</span>
					</div>

					<div class="photo-upload">
						<label for="reviews_img1">사진 :</label> <input type="file"
							id="reviews_img1" name="reviews_img1" accept="image/*">
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
		<div class="paging-button" style="display: none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display: none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif"
				width="50" height="50">
		</div><!-- 리뷰 목록 출력 끝 -->

	</div> <!-- page main 끝  -->
<jsp:include page="/WEB-INF/views/reserv/reservList.jsp" />
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>