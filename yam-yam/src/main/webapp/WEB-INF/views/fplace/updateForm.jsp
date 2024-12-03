<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript">
		$(function(){
			// 기존 등록된 이미지 경로 저장
		    let originalPath = "${!empty fplace.fp_storeimg ? pageContext.request.contextPath.concat('/upload/').concat(fplace.fp_storeimg) : ''}";
		  
		 // 파일 삭제 버튼 동작
            $('#file_del').click(function () {
                let choice = confirm('이미지를 삭제하시겠습니까?');
                if (choice) {
                    $.ajax({
                        url: 'deleteFile.do',
                        type: 'post',
                        data: { fp_num: ${fplace.fp_num} },
                        dataType: 'json',
                        success: function (param) {
                            if (param.result === 'logout') {
                                alert('로그인 후 사용하세요');
                            } else if (param.result === 'success') {
                                $('#file_detail').hide();
                                $('#preview_img').attr('src', '').hide();
                            } else {
                                alert('파일 삭제 중 오류가 발생했습니다.');
                            }
                        },
                        error: function () {
                            alert('네트워크 오류가 발생했습니다.');
                        }
                    });
                }
            });

         // 파일 선택 이벤트 처리
            $('#fp_storeimg').change(function () {
                let selectedFile = this.files[0];
                if (!selectedFile) {
                    $('#preview_img').attr('src', originalPath).show();
                    return;
                }
                if (selectedFile.size > 1024 * 1024) {
                    alert('이미지 크기는 1024KB 이하여야 합니다.');
                    $('#fp_storeimg').val('');
                    $('#preview_img').attr('src', originalPath).show();
                    return;
                }
                let reader = new FileReader();
                reader.onload = function (e) {
                    $('#preview_img').attr('src', e.target.result).show();
                };
                reader.readAsDataURL(selectedFile);
            });

			
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
			        
			       

				}//end of for
			});//end of submit
		});
	
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<div class="content-main">
			<h2 class="fw-700">식당 정보 수정</h2>
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
					
					<li><label for="fp_filter1">필터1 (식사, 간식)</label></li>
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
	                <c:if test="${!empty fplace.fp_storeimg}">
	                    <div id="file_detail">
	                      
	                        <img id="preview_img" src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" width="100">
	                          <p>(${fplace.fp_storeimg}) 파일이 등록되어 있습니다.</p>
	                        <input type="button" value="파일삭제" id="file_del" class="btn block-box ml-auto w-20">
	                    </div>
	                </c:if>
	                   <input type="file" name="fp_storeimg" id="fp_storeimg" accept="image/gif,image/png,image/jpeg">
	                <c:if test="${empty fplace.fp_storeimg}">
	                    <img id="preview_img" src="" width="100" style="display:none;">
	                </c:if>
	                </li>
	                
	                <li><label for="">예약 가능 시간</label> 
					<br>
					<!-- 11시 ~ 2시 --> 
						<input type="checkbox" name="ft_time" value="11:00"> <span>11:00</span>
						<input type="checkbox" name="ft_time" value="11:30"> <span>11:30</span>
						<input type="checkbox" name="ft_time" value="12:00"> <span>12:00</span>
						<input type="checkbox" name="ft_time" value="12:30"> <span>12:30</span>
						<input type="checkbox" name="ft_time" value="13:00"> <span>13:00</span>
						<input type="checkbox" name="ft_time" value="13:30"> <span>13:30</span>
						<input type="checkbox" name="ft_time" value="14:00"> <span>14:00</span>

						<!-- 5시 ~ 8시 --> 
						<br>
						<input type="checkbox" name="ft_time" value="17:00"> <span>17:00</span> 
						<input type="checkbox" name="ft_time" value="17:30"> <span>17:30</span> 
						<input type="checkbox" name="ft_time" value="18:00"> <span>18:00</span>
						<input type="checkbox" name="ft_time" value="18:30"> <span>18:30</span>
						<input type="checkbox" name="ft_time" value="19:00"> <span>19:00</span>
						<input type="checkbox" name="ft_time" value="19:30"> <span>19:30</span>
						<input type="checkbox" name="ft_time" value="20:00"> <span>20:00</span>
					</li>
	                
	                
	                
	            </ul>
	            
				<div class="align-center">
					<button type="submit" class="btn-primary">수정</button>
					 <button type="button" class="btn-line-primary" 
            onclick="location.href='detail.do?fp_num=${fplace.fp_num}'">식당 정보</button>
				</div>
			</form>
		</div>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>