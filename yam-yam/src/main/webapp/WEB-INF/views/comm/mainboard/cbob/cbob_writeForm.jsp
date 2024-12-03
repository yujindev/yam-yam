<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
$(function(){
		$('#write_form').submit(function() {
			const items = document
					.querySelectorAll('.input-check');
			for (let i = 0; i < items.length; i++) {
				if (items[i].value.trim() == '') {
					const label = $('label[for="'+items[i].id+'"]');
					alert(label.text() + ' 필수 입력');
					items[i].value = '';
					$(items[i]).focus();
					return false;
				}
			}
		});
	});
</script>

</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2 class="fw-700">밥친구찾기 글쓰기</h2>
			<form id="write_form" action="cbob_write.do" method="post" enctype="multipart/form-data">
				<ul>
					<li class="flex-box mb-2">
					<label for="cbob_title">제목</label> <input type="text"
						name="cbob_title" id="cbob_title" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cbob_menu">메뉴 선정</label> <input type="text"
						name="cbob_menu" id="cbob_menu" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cbob_gender1">내 성별 : </label>
					<label><input type="radio" name="cbob_gender1" id="cbob_gender1" value="남자" class="input-check"> 남자</label>
                    <label><input type="radio" name="cbob_gender1" id="cbob_gender1" value="여자" class="input-check"> 여자</label>
                    </li>
					
					<li class="flex-box mb-2">
					<label for="cbob_gender2">상대 성별 : </label>
					<label><input type="radio" name="cbob_gender2" id="cbob_gender2" value="남자" class="input-check"> 남자</label>
                    <label><input type="radio" name="cbob_gender2" id="cbob_gender2" value="여자" class="input-check"> 여자</label>
                    <label><input type="radio" name="cbob_gender2" id="cbob_gender2" value="무관" class="input-check"> 무관</label>
                    
                    </li>
                    
					<li class="flex-box mb-2">
					<label for="cbob_meet">만나는 시간</label> <input type="text"
						name="cbob_meet" id="cbob_meet" maxlength="50" class="input-check w-90 p-05 ml-auto block-box">
					</li>
					
					<li class="flex-box mb-2">
					<label for="cbob_article">내용</label> 
					<textarea rows="5" cols="40" name="cbob_article" id="cbob_article" class="input-check w-90 p-05 ml-auto block-box"></textarea>
					</li>
				</ul>
				<div class="flex-box f-end mt-3">
					<input type="submit" value="등록" class="btn btn-primary"> 
					<input type="button" value="목록" onclick="location.href='list.do'" class="btn ml-1">
				</div>
			</form>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />		
	</div>
</body>
</html>