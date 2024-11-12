<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		/* ============ 아이디 중복 체크 ============ */
		$('#id_check').click(function(){
			if(!/^[A-Za-z0-9]{6,12}$/.test($('#mem_id').val())){
				alert('영문 또는 숫자 사용, 6~12자를 사용하세요.');
				$('#mem_id').val('').focus();
				return;
			}
			
			//아이디:서버와 통신
			$.ajax({
				url:'checkMember.do',
				type:'post',
				data:{mem_id:$('#mem_id').val()},
				dataType:'json',
				success:function(param){
					if(param.result=='idNotFound'){
						idChecked = 1;
						$('#message_id').css('color','#009e1a').text('사용 가능한 ID');
					}else if(param.result=='idDuplicated'){
						idChecked = 0;
						$('#message_id').css('color','#ff6200').text('중복! 사용 불가능');
						$('#mem_id').val('').focus();
					}else{
						idChecked = 0;
						alert('아이디 중복 체크 오류가 발생했습니다.');
					}
				},
				error:function(){
					idChecked = 0;
					alert('네트워크 오류가 발생했습니다.');
				}
			})
		});//end of 아이디 중복체크
		
		//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
		$('#register_form #id').keydown(function(){
			idChecked = 0;
			$('message_id').text('');
		});
		
		/* ============ 닉네임 중복 체크 ============ */
		$('#nickname_check').click(function(){
			if(!/^[A-Za-z0-9가-힣]{1,10}$/.test($('#mem_nickname').val())){
				alert('영문,숫자,한글 사용(최대 10자)');
				$('#mem_nickname').val('').focus();
				return;
			}
			
			//서버와 통신
			$.ajax({
				url:'checkMember.do',
				type:'post',
				data:{mem_nickname:$('#mem_nickname').val()},
				dataType:'json',
				success:function(param){
					if(param.result=='nicknameNotFound'){
						nicknameChecked = 1;
						$('#message_nickname').css('color','#009e1a').text('사용 가능한 닉네임');
					}else if(param.result=='nicknameDuplicated'){
						nicknameChecked = 0;
						$('#message_nickname').css('color','#ff6200').text('중복! 사용 불가능');
						$('#mem_nickname').val('').focus();
					}else{
						nicknameChecked = 0;
						alert('닉네임 중복 체크 오류가 발생했습니다.');
					}
				},
				error:function(){
					nicknameChecked = 0;
					alert('네트워크 오류가 발생했습니다.');
				}
			})
		});;//end of 닉네임 중복체크
		
		//닉네임 중복 안내 메시지 초기화 및 닉네임 중복 값 초기화
		$('#register_form #nickname').keydown(function(){
			nicknameChecked = 0;
			$('message_nickname').text('');
		});
		
		//회원 정보 등록 유효성 체크
		$('#register_form').submit(function(){
			const items = document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector('label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 필수 입력');
					items[i].value='';
					items[i].focus();
					return false;
				}
				
				if(items[i].id == 'mem_id' && !/^[A-Za-z0-9]{6,12}$/.test($('#mem_id').val())){
					alert('영문 또는 숫자를 사용하세요(최대 12자)');
					$('mem_id').val('').focus();
					return false;
				}
				
				if(items[i].id=='mem_id' && idChecked==0){
					alert('아이디 중복체크 필수!');
					return false;
				}
				
				if(items[i].nickname == 'mem_nickname' && !/^[A-Za-z0-9가-힣]{1,10}$/.test($('#mem_nickname').val())){
					alert('한글,영문,숫자를 사용하세요(최대 10자)');
					$('mem_nickname').val('').focus();
					return false;
				}
				
				if(items[i].nickname=='mem_nickname' && nicknameChecked==0){
					alert('닉네임 중복체크 필수!');
					return false;
				}
				
			}//end of for
		});//end of 유효성 체크
	});//end of first function
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>회원 가입</h2>
	<form id="register_form" action="registerUser.do" method="post">
		<ul>
			<li>
				<label for="mem_id">아이디</label>
				<input type="text" name="mem_id" id="mem_id" maxlength="12" autocomplete="off" class="input-check">
				<input type="button" value="ID 중복체크" id="id_check">
				<span id="message_id"></span>
				<div class="form-notice">영문 또는 숫자(6자~12자)</div>
			</li>
			<li>
				<label for="mem_nickname">닉네임</label>
				<input type="text" name="mem_nickname" id="mem_nickname" maxlength="10" autocomplete="off" class="input-check">
				<input type="button" value="닉네임 중복체크" id="nickname_check">
				<span id="message_nickname"></span>
				<div class="form-notice">한글,영문,숫자 사용 가능(최대 10자)</div>
			</li>			
			<li>
				<label for="mem_pw">비밀번호</label>
				<input type="password" name="mem_pw" id="mem_pw" maxlength="12" class="input-check">
			</li>				
			<li>
				<label for="mem_phone">연락처</label>
				<input type="text" name="mem_phone" id="mem_phone" maxlength="15" class="input-check">
			</li>				
		</ul>
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</form>
	</div>
</div>
</body>
</html>