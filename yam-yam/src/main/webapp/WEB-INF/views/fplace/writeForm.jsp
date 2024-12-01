<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당정보 저장</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
		$(function(){
			//게시판  유효성 체크 
			$('#write_form').submit(function(){
				const items = document.querySelectorAll('.input-check');
				for(let i=0; i<items.length;i++){
					if(items[i].value.trim()==''){
						const label = document.querySelector('label[for="'+items[i].id+'"]');
						alert(label.textContent + ' 필수입력');
						items[i].value='';
						$(items[i]).focus();
						return false;
					}
				}//end of for
				if (document.querySelectorAll('input[name="fp_filter1"]:checked').length === 0) {
		            alert('필터1를 하나 이상 선택해야 합니다.');
		            return false;
		        }

		        // 필터2 체크박스 유효성 검사
		        if (document.querySelectorAll('input[name="fp_filter2"]:checked').length === 0) {
		            alert('필터2를 하나 이상 선택해야 합니다.');
		            return false;
		        }

		        // 필터3 체크박스 유효성 검사
		        if (document.querySelectorAll('input[name="fp_filter3"]:checked').length === 0) {
		            alert('필터3를 하나 이상 선택해야 합니다.');
		            return false;
		        }
		        
		        const photoField = document.getElementById('fp_storeimg');
		        if (photoField.files.length === 0) { // 파일이 첨부되지 않았는지 확인
		            alert('사진을 반드시 업로드해야 합니다.');
		            photoField.focus();
		            return false;
		        }
				
			});//end of submit
		});
	</script>

</head>
<body>
<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2>식당 정보 저장</h2>
			<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
				<ul>
					<li><label for="fp_name">식당이름</label> 
					<input type="text" name="fp_name" id="fp_name" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_phone">식당번호</label> 
					<input type="text" name="fp_phone" id="fp_phone" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_time">식당운영시간</label> 
					<input type="text" name="fp_time" id="fp_time" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_loc">식당위치</label> 
					<input type="text" name="fp_loc" id="fp_loc" maxlength="50" class="input-check">
					</li>
					
					<li>
					<label for="fp_filter1">필터1 (식사, 간식)</label></li>
					<li>
					    <label><input type="radio" name="fp_filter1" value="식사">식사</label>
					    <label><input type="radio" name="fp_filter1" value="간식">간식</label>
					</li>
					
					<li><label for="fp_filter2">필터2 (한식, 중식, 일식, 양식, 아시안)</label></li>
					<li>
					    <label><input type="radio" name="fp_filter2" value="한식">한식</label>
					    <label><input type="radio" name="fp_filter2" value="중식">중식</label>
					    <label><input type="radio" name="fp_filter2" value="일식">일식</label>
					    <label><input type="radio" name="fp_filter2" value="양식">양식</label>
					    <label><input type="radio" name="fp_filter2" value="아시안">아시안</label>
					</li>

					
					<li><label for="fp_filter3">필터3 (혼밥, 친구, 연인, 가족, 모임)</label></li>
					<li>
				    <label><input type="checkbox" name="fp_filter3" value="혼밥">혼밥</label>
				    <label><input type="checkbox" name="fp_filter3" value="친구">친구</label>
				    <label><input type="checkbox" name="fp_filter3" value="연인">연인</label>
				    <label><input type="checkbox" name="fp_filter3" value="가족">가족</label>
				    <label><input type="checkbox" name="fp_filter3" value="모임">모임</label>
					</li>



					<li><label for="fp_storeimg">식당이미지</label> 
					<input type="file" name="fp_storeimg" id="fp_storeimg" accept="image/gif,image/png,image/jpeg">
					</li>
					
					<li><label for="">예약 가능 시간</label>
					<input type="checkbox" name="ft_time" value="${fplace_time.ft_time}">
					<span>11:00</span>
					<input type="checkbox" name="ft_time" value="${fplace_time.ft_time}">
					<span>11:30</span>
					<input type="checkbox" name="ft_time" value="${fplace_time.ft_time}">
					<span>12:00</span>
					<input type="checkbox" name="ft_time" value="${fplace_time.ft_time}">
					<span>12:30</span>
					</li>
					
				</ul>
				<div class="align-center">
					<input type="submit" value="등록"> 
					<input type="button" value="목록" onclick="location.href='list.do'">
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/reserv/reservList.jsp" />
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>