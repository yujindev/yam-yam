<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당정보 저장</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
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
					
					<li><label for="fp_filter1">필터1(식사, 요리, 간식)</label> 
					<input type="text" name="fp_filter1" id="fp_filter1" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_filter2">필터2(한식, 중식, 일식, 양식, 아시안)</label> 
					<input type="text" name="fp_filter2" id="fp_filter2" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_filter3">필터3(혼밥, 친구, 연인, 가족, 모임)</label> 
					<input type="text" name="fp_filter3" id="fp_filter3" maxlength="50" class="input-check">
					</li>
					
					<li><label for="fp_storeimg">식당이미지</label> 
					<input type="file" name="fp_storeimg" id="fp_storeimg" accept="image/gif,image/png,image/jpeg">
					</li>
					
					<li><label for="fp_menuimg1">메뉴이미지1</label> 
					<input type="file" name="fp_menuimg1" id="fp_menuimg1" accept="image/gif,image/png,image/jpeg">
					</li>
					
					<li><label for="fp_menuimg2">메뉴이미지2</label> 
					<input type="file" name="fp_menuimg2" id="fp_menuimg2" accept="image/gif,image/png,image/jpeg">
					</li>
					
					<li><label for="fp_menuimg3">메뉴이미지3</label> 
					<input type="file" name="fp_menuimg3" id="fp_menuimg3" accept="image/gif,image/png,image/jpeg">
					</li>
					
					<li><label for="fp_menuimg4">메뉴이미지4</label> 
					<input type="file" name="fp_menuimg4" id="fp_menuimg4" accept="image/gif,image/png,image/jpeg">
					</li>
					
				</ul>
				<div class="align-center">
					<input type="submit" value="등록"> 
					<input type="button" value="목록" onclick="location.href='list.do'">
				</div>
			</form>
		</div>
	</div>
</body>
</html>