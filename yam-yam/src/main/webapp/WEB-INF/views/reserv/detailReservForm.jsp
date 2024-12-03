<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약관리(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HR.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>예약 상태 관리</h2>
		<form action="adminReserv.do" method="post" id="detail_form">
			<input type="hidden" name="rs_num" value="${reserv.rs_num}">
			<ul>
				<li><label>식당명</label>${reserv.fp_name}</li>
				<li><label>예약자 아이디</label>${reserv.mem_id}</li>
				<li><label>예약자 닉네임</label>${reserv.mem_nickname}</li>
				<li><label>예약일시</label>${reserv.rs_time}</li>
				<li><label>예약인원</label>${reserv.rs_cnt}</li>
			</ul>
			<ul>
				<li>
					<label>예약상태</label>
						<input type="radio" name="rs_status" value="1"
						      id="rs_status1" <c:if test="${reserv.rs_status == 1}">checked</c:if>>예약 확인중
						<input type="radio" name="rs_status" value="2"
						      id="rs_status2" <c:if test="${reserv.rs_status == 2}">checked</c:if>>예약 완료
						<input type="radio" name="rs_status" value="3"
						      id="rs_status3" <c:if test="${reserv.rs_status == 3}">checked</c:if>>예약 지남
						<input type="radio" name="rs_status" value="4"
						      id="rs_status4" <c:if test="${reserv.rs_status == 4}">checked</c:if>>예약 취소
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="예약 수정">
				<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/fplace/adminReservList.do'">
			</div>
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>