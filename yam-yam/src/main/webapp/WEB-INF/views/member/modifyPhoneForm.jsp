<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modify phone</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HR.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//회원 정보 등록 유효성 체크
		$('#modify_form').submit(function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector(
							       'label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 필수 입력');
					items[i].value='';
					items[i].focus();
					return false;
				}		
			}//end of for
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>연락처 수정</h2>
		<form id="modify_form" action="modifyUser.do" method="post">
			<ul>
				<li>
					<label for="mem_phone">연락처</label>
					<input type="text" name="mem_phone" id="mem_phone" 
					       value="${member.mem_phone}"
					       maxlength="15" class="input-check">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="마이페이지"
				    onclick="location.href='myPage.do'">
			</div>                               
		</form>
	</div>
</div>
</body>
</html>




