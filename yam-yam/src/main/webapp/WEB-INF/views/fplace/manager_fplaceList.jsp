<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 식당 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SJ.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('search_form');
		// 검색어 입력 여부 확인
		myForm.onsubmit = function(){
			const keyword = document.getElementById('keyword');
			if(keyword.value.trim()==''){
				alert('검색어를 입력하세요');
				keyword.value='';
				keyword.focus();
				return false;
			}
		};
	};
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
	<div class="content-main">
		<h2>내 식당 관리</h2>
		<form id="search_form" action="fplaceManagerList.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}"></c:if>>식당이름</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
		<div class="list-space align-right">
			<input type="button" value="목록" onclick="location.href='fplaceManagerList.do'">
		</div>
		<c:if test="${count == 0}">
			<div class="result-display">
				표시할 식당이 없습니다.
			</div>
		</c:if>
		<c:if test="${count > 0}">
			<table>
				<tr>
					<th>식당 이름</th>
					<th>별점</th>
					<th>리뷰 개수</th>
					<th>필터 정보</th>
				</tr>
				<c:forEach var="fplace" items="${list}">
					<tr>
						<td><a href="detail.do?fp_num=${fplace.fp_num}">${fplace.fp_name}</a></td>
						<td>${fplace.fp_avgscore}</td>
						<td>${fplace.reviews_count}개</td>
						<td>${fplace.fp_filter1}, ${fplace.fp_filter2}, ${fplace.fp_filter3}</td>
					</tr>
				</c:forEach>
			</table>
			<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>
