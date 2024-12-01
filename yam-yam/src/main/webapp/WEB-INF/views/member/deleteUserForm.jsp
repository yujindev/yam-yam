<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//회원탈퇴 유효성 체크
		$('#delete_form').submit(function(){
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
			
			if($('#mem_pw').val()!=$('#cpasswd').val()){
				alert('새비밀번호와 새비밀번호 확인이 불일치');
				$('#mem_wd').val('').focus();
				$('#cpasswd').val('');
				return false;
			}			
		});//end of submit
		
		//새비밀번호 확인까지 한 후 다시 새비밀번호를 수정하려고
		//하면 새비밀번호 확인을 초기화
		$('#mem_pw').keyup(function(){
			$('#cpasswd').val('');
			$('#message_cpasswd').text('');
		});
		
		//새비밀번호와 새비밀번호 확인 일치 여부 체크
		$('#cpasswd').keyup(function(){
			if($('#cpasswd').val()!='' && $('#mem_pw').val()==$('#cpasswd').val()){
				$('#message_cpasswd').text('새비밀번호 일치');
			}else{
				$('#message_cpasswd').text('');
			}
		});		
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>탈퇴</h2>
		<form id="delete_form" action="deleteUser.do" method="post">
			<ul>
				<li>
					<label for="mem_id">아이디</label>
					<input type="text" name="mem_id" id="mem_id" autocomplete="off"
					       maxlength="12" class="input-check">
				</li>
				<li>
					<label for="mem_pw">비밀번호</label>
					<input type="password" name="mem_pw" id="mem_pw" maxlength="12" class="input-check">
				</li>
				<li>
					<label for="cpasswd">비밀번호 확인</label>
					<input type="password" id="cpasswd" maxlength="12" class="input-check">
					<span id="message_cpasswd"></span>            
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="탈퇴">
				<input type="button" value="마이페이지"
				    onclick="location.href='myPage.do'">
			</div>                               
		</form>
	</div>
</div>
</body>
</html>




