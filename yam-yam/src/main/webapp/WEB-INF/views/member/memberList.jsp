<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('search_form');
		//이벤트 연결
		myForm.onsubmit=function(){
			const keyword = document.getElementById('keyword');
			if(keyword.value.trim()==''){
				alert('검색어를 입력하세요!');
				keyword.value='';
				keyword.focus();
				return false;
			}
		}
	}
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
</div>
</body>
</html>