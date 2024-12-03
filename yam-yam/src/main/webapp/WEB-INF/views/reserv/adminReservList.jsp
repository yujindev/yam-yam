<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>  
<meta charset="UTF-8">
<title>예약관리()</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HR.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2 class="fw-700">내 식당 예약 관리</h2>
		<c:if test="${count==0}">
				<div class="result-display">
					표시할 예약정보가 없습니다.
				</div>
				</c:if>
				<c:if test="${count>0}">
				<table class="list-table mt-3">
					<tr>
						<th>식당명</th>
						<th>예약자 아이디</th>
						<th>예약자 닉네임</th>
						<th>예약일시</th>
						<th>예약인원</th>
						<th>현재 예약 상태</th>
					</tr>
					<c:forEach var="reserv" items="${reserv}">
					<tr class="text-c">
						<td>${reserv.fp_name}</td>
						<td>${reserv.mem_id}</td>
						<td>${reserv.mem_nickname}</td>
						<td>${reserv.rs_time}</td>
						<td>${reserv.rs_cnt}</td>
						<td>
							<c:if test="${reserv.rs_status == 1}"><a href="detailReservForm.do?rs_num=${reserv.rs_num}">예약 확인중</a></c:if>
							<c:if test="${reserv.rs_status == 2}"><a href="detailReservForm.do?rs_num=${reserv.rs_num}">예약 확정</a></c:if>
							<c:if test="${reserv.rs_status == 3}"><a href="detailReservForm.do?rs_num=${reserv.rs_num}">예약 지남</a></c:if>
							<c:if test="${reserv.rs_status == 4}"><a href="detailReservForm.do?rs_num=${reserv.rs_num}">예약 취소</a></c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
				<div class="align-center mt-3">${page}</div>
				</c:if>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>