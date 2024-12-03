<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당 검색</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HY.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/SJ.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	window.onload=function(){
		const myForm = document.getElementById('search_form');
		//이벤트 연결
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
	<h2 class="fw-700">식당 검색</h2>
	<form id="search_form" action="fplaceUserList.do" method="get" class="text-c">
		<ul class="search w-100">
			<li>
				<select name="keyfield" class="search-cat">
					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>식당이름</option>
					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>위치</option>
				</select>
			</li>
			<li class="ml-1">
				<input type="search" class="search-input bg-gr150 w-40" size="10" name="keyword" id="keyword" placeholder="검색할 내용을 입력하세요." value="${param.keyword}">
			</li>
			<li class="ml-1">
				<input type="submit" value="" class="btn-re icon-search">
			</li>
		</ul>
	</form>


        <!-- 목록 생성 -->
        <c:if test="${count == 0}">
            <div class="result-display">
                표시할 식당정보가 없습니다.
            </div>
        </c:if>

        <c:if test="${count > 0}">
            <table class="ranking-table">
                <c:forEach var="fplace" items="${list}">
                    <tr class="table-row">
                        <!-- 가게 이미지 -->
                        <td>
                            <img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" alt="${fplace.fp_name}" class="image-cell" width="100">
                        </td>

                        <!-- 가게 정보 -->
                        <td class="details-cell">
                            <p class="category">${fplace.fp_filter1}, ${fplace.fp_filter2}, ${fplace.fp_filter3}</p>
                            <p class="name">
                                <a href="detail.do?fp_num=${fplace.fp_num}">${fplace.fp_name}</a>
                            </p>
                            <p>⭐ ${fplace.fp_avgscore}</p>
                            <p>리뷰 ${fplace.reviews_count}개</p>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="align-center">${page}</div>
        </c:if>
        <!-- 목록 끝 -->
    </div>
</div>
<jsp:include page="/WEB-INF/views/reserv/reservList.jsp" />
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>