<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>랜덤 메뉴 등록</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
		$(function(){
			//게시판  유효성 체크 
			$('#tmenuWrite_form').submit(function(){
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
			<h2>랜덤 메뉴 등록</h2>
			<form id="tmenuWrite_form" action="tmenuWrite.do" method="post">
				<ul>
					<li>
					<label for="tm_menu">메뉴명</label> 
					<input type="text" name="tm_menu" id="tm_menu" maxlength="50" class="input-check">
					</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="등록"> 
					<input type="button" value="목록" onclick="location.href='tmenuList.do'">
				</div>
			</form>
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</html>