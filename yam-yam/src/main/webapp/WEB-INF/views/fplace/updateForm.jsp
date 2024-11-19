<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당정보 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript">
		$(function(){
			//식당정보 유효성 체크 
			$('#update_form').submit(function(){
				const items = document.querySelectorAll('.input-check');
				for(let i=0; i<items.length;i++){
					if(items[i].value.trim()==''){
						const label = document.querySelector('label[for="'+items[i].id+'"]');
						alert(label.textContent + ' 필수입력');
						items[i].value='';
						$(items[i]).focus();
						return false;
					}
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

				}//end of for
			});//end of submit
		});
	
	</script>
</head>
<body>
<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2>식당 정보 수정</h2>
			<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
				<input type="hidden" name="fp_num" value="${fplace.fp_num}">
				<ul>
					<li><label for="fp_name">식당이름</label> 
					<input type="text" name="fp_name" id="fp_name"  value="${fplace.fp_name}" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_phone">식당번호</label> 
					<input type="text" name="fp_phone" id="fp_phone"  value="${fplace.fp_phone}" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_time">식당운영시간</label> 
					<input type="text" name="fp_time" id="fp_time"  value="${fplace.fp_time}" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_loc">식당위치</label> 
					<input type="text" name="fp_loc" id="fp_loc"  value="${fplace.fp_loc}" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_filter1">필터1 (식사, 요리, 간식)</label></li>
					<li>
					    <label><input type="checkbox" name="fp_filter1" value="식사">식사</label>
					    <label><input type="checkbox" name="fp_filter1" value="요리">요리</label>
					    <label><input type="checkbox" name="fp_filter1" value="간식">간식</label>
					</li>
					
					<li><label for="fp_filter2">필터2 (한식, 중식, 일식, 양식, 아시안)</label></li>
					<li>
					    <label><input type="checkbox" name="fp_filter2" value="한식">한식</label>
					    <label><input type="checkbox" name="fp_filter2" value="중식">중식</label>
					    <label><input type="checkbox" name="fp_filter2" value="일식">일식</label>
					    <label><input type="checkbox" name="fp_filter2" value="양식">양식</label>
					    <label><input type="checkbox" name="fp_filter2" value="아시안">아시안</label>
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
					<c:if test="${!empty fplace.fp_storeimg}">
										<div id="file_detail">
							(${fplace.fp_storeimg}) 파일이 등록되어 있습니다.
							<img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" width="100">
							<input type="button" value="파일삭제" id="file_del">
							<script type="text/javascript">
								$('#file_del').click(function(){
									let choice = confirm('삭제하시겠습니까?');
									if(choice){
										//서버와 통신
										$.ajax({
											url:'deleteFile.do',
											type :'post',
											data:{fp_num:${fplace.fp_num}},
											dataType:'json',
											success:function(param){
												if(param.result == 'logout'){
													alert('로그인 후 사용하세요');
												}else if(param.result == 'success'){
													$('#file_detail').hide();
												}else if(param.result == 'wrongAccess'){
													alert('잘못된 접속입니다.');
												}else{
													alert('파일 삭제 오류 발생')
												}
											},
											error:function(){
												alert('네트워크 오류 발생');
											}
										});
									}
								});
							</script>
					</div>
					</c:if>
					</li>
					
				</ul>
				<div class="align-center">
					<input type="submit" value="수정"> 
					<input type="button" value="식당 정보" onclick="location.href='detail.do?fp_num=${fplace.fp_num}'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>