<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#login_form').submit(function(){
			if($('#mem_id').val().trim()==''){
				alert('아이디를 입력하세요');
				$('#mem_id').val('').focus();
				return false;
			}
			if($('#mem_pw').val().trim()==''){
				alert('비밀번호를 입력하세요');
				$('#mem_pw').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<input type="button"onclick="location.href='${pageContext.request.contextPath}/main/main.do'" class="icon block-box ml-auto icon-home bg-gr300">
		<h2 class="fw-700 fs-16 m-1 text-c">로그인</h2>
		<form id="login_form" action="login.do" method="post" class="w-50">
			<ul>
				<li class="floating-label mb-1">
					<input type="text" class="form-input block-box m-0auto w-90 p-05" placeholder="아이디" name="mem_id" id="mem_id" maxlength="12" autocomplete="off">
					<label for="mem_id" class="pl-2">아이디</label>
				</li>
				<li class="floating-label">
					<input type="password" class="form-input block-box m-0auto w-90 p-05" placeholder="비밀번호" name="mem_pw" id="mem_pw" maxlength="12">
					<label for="mem_pw" class="pl-2">비밀번호</label>
				</li>
			</ul>
			<div class="mt-3">
				<input type="submit" value="로그인" class="block-box w-100 btn btn-primary">				
				<input type="button" value="회원가입" onclick="location.href='registerUserForm.do'" class="block-box w-100 mt-1 btn btn-line-primary">
			</div>
		</form>
	</div>
</div>
</body>
</html>