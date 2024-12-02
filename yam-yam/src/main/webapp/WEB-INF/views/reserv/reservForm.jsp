<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>예약 페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/HR.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.14.1/themes/base/jquery-ui.css">
<style>
	
/* 핑크 박스 내부 스타일링 */

.reservation-container {
    background-color: #fff;
    padding: 20px;
    margin: 20px;
}
     
/* 가게 정보 */
.store-info {
    text-align: center;
    margin-bottom: 20px;
}

.store-info img {
    width: 100%;
    max-width: 600px;
    height: auto;
    border-radius: 10px;
}

.store-details {
    margin-top: 15px;
}

.store-name {
    font-size: 24px;
    font-weight: bold;
    color: #222;
    margin-bottom: 10px;
}

.store-time, .store-loc {
    font-size: 14px;
    color: #666;
}

/* 레이아웃 조정: datepicker와 시간 선택 양옆 배치 */
.time-selection-container {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 20px;
    margin-top: 20px;
}

/* 예약 시간 선택 */
.time-selection {
    max-width: calc(40% - 50px);
    text-align: center;
}

.reserv-time-select {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 10px;
}

.reserv-time-select label {
	flex: 0 0 8%;
    align-items: center;
    background-color: #f5f5f5;
    width: 20%;
    padding: 0;
    border-radius: 5px;
    border: 1px solid #ddd;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

.reserv-time-select input {
    display: none;
}

.reserv-time-select label:hover {
    background-color: #ff69b4;
    color: #fff;
}

.reserv-time-select input:checked + span {
    background-color: #ff69b4;
    color: #fff;
}

/* 버튼 스타일 */
button {
    display: inline-block;
    width: 45%;
    padding: 10px 0;
    margin: 10px 2%;
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.btn-submit {
    background-color: #ff7800;
    color: #fff;
}

.btn-submit:hover {
    background-color: #ff7800;
}

.btn-cancel {
    background-color: #ddd;
    color: #333;
}

.btn-cancel:hover {
    background-color: #bbb;
}

/* 예약 주의사항 */
p {
    font-size: 12px;
    color: #777;
    line-height: 1.6;
    margin-top: 20px;
    text-align: center;
}

/*음*/
/* datepicker 전체 컨테이너 */
#datepicker {
    font-family: 'Arial', sans-serif;
    max-width: 600px;
    margin: 0 auto;
    padding: 30px;
    background-color: #fff;
    border:none;
    width:60%;
}

/* 달력 헤더 (월/년도 표시) */
.ui-datepicker-header {
    background-color: #FF7800;
    color: white;
    text-align: center;
    padding: 20px 0;
    font-size: 20px;
    font-weight: bold;
    border-radius: 10px 10px 0 0;
}

/* 요일 헤더 */
.ui-datepicker-calendar thead th {
    text-align: center;
    color: #666;
    font-size: 14px;
    font-weight: bold;
    padding-bottom: 8px;
    border-bottom: 1px solid #ddd;
}

/* 날짜 기본 스타일 */
.ui-datepicker-calendar tbody td {
    text-align: center;
    vertical-align: middle;
    width: 40px;
    height: 40px;
}

/* 날짜 버튼 */
.ui-datepicker-calendar tbody td a {
    display: inline-block;
    text-align: center;
    font-size: 16px;
    width: 100%;
    height: auto;
    line-height: 40px;
    color: #333;
    transition: all 0.3s ease;
    border: none;
}

/* 오늘 날짜 강조 */
.ui-datepicker-calendar tbody td a.ui-state-highlight {
    font-weight: bold;
    color: #FF7800;
}

/* 선택된 날짜 (원형 테두리 추가) */
.ui-datepicker-calendar tbody td a.ui-state-active {
    position: relative;
    border: 2px solid #FF7800;
    border-radius: 50%;
    padding: 5px;
    font-weight: bold;
    color: #FF7800;
    background-color:white;
}

/* hover 효과 */
.ui-datepicker-calendar tbody td a:hover {
    background-color: #FF7800;
    color: white;
    border-radius: 50%;
}

/* 비활성화된 날짜 */
.ui-datepicker-calendar tbody td.ui-state-disabled span {
    color: #bbb;
    background-color: transparent;
    cursor: not-allowed;
    border:0px;
}

/* 이전/다음 버튼 */
.ui-datepicker-prev, .ui-datepicker-next {
    position: absolute;
    top: 15px;
    width: 32px;
    height: 32px;
    background-color: white;
    border: 1px solid #FF7800;
    border-radius: 50%;
    text-align: center;
    line-height: 30px;
    cursor: pointer;
    color: #FF7800;
    font-weight: bold;
    transition: all 0.3s ease;
}

.ui-datepicker-prev {
    left: 15px;
}

.ui-datepicker-next {
    right: 15px;
}

/* 이전/다음 버튼 hover */
.ui-datepicker-prev:hover, .ui-datepicker-next:hover {
    background-color: #FF7800;
    color: white;
    border:0px;
}
/* 날짜에 테두리 없이 스타일링 */
.ui-datepicker-calendar tbody td a.no-border {
    border: none !important;
}
/* 흠 */
/* 예약 시간 선택 버튼 레이아웃 수정 */
.reserv-time-select {
    display: flex;
    flex-wrap: wrap;  /* 줄 바꿈을 허용 */
    justify-content: space-between;  /* 버튼 간격을 균등하게 */
    gap: 10px;  /* 버튼 간격 */
}

/* 각 예약 시간 선택 버튼 */
.reserv-time-select label {
    flex: 0 0 8%;  /* 버튼 너비를 4개가 한 줄에 배치될 수 있도록 설정 */
    display: flex;
    align-items: left;
    color: #919191;
    background-color: white;
    padding: 10px 15px;
    border-radius: 5px;
    border: 1px solid #ddd;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

/* 버튼이 4개씩 줄 바뀌도록 하기 */
.reserv-time-select label:nth-child(4n) {
    margin-right: 0;  /* 4번째 버튼은 오른쪽 여백을 없애줍니다 */
}
</style>
<script src="https://code.jquery.com/ui/1.14.1/jquery-ui.js"></script>
<script>

//한글 설정
	$.datepicker.setDefaults({
	  closeText: '닫기',
	  prevText: '이전',
	  nextText: '다음',
	  currentText: '오늘',
	  monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	  monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	  dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
	  dayNamesShort: ['일','월','화','수','목','금','토'],
	  dayNamesMin: ['일','월','화','수','목','금','토'],
	  dateFormat: 'yy-mm-dd',
	  firstDay: 0,
	  isRTL: false
	});
	
	$(function() {
	  $("#datepicker").datepicker({
	    minDate: 0, // 과거 날짜 비활성화
	    beforeShowDay: function(date) {
	      const day = date.getDay();
	      if (day === 0) {
	        // 일요일
	        return [true, 'sunday'];
	      } else if (day === 6) {
	        // 토요일
	        return [true, 'saturday'];
	      }
	      return [true, '']; // 평일
	    },
	    onSelect: function(dateText, inst) {
	    	//숨겨진 필드에 선택된 날짜 저장
	    $("#selected_date").val(dateText);
	      // 이전 선택된 날짜 초기화
	      $(".ui-datepicker td").removeClass("selected");
	
	      // 선택된 날짜에 클래스 추가
	      const selectedDate = inst.selectedDay;
	      const selectedMonth = inst.selectedMonth;
	      const selectedYear = inst.selectedYear;
	
	      // 달력에서 선택된 날짜 찾기
	      $(".ui-datepicker-calendar td").each(function() {
	        const cell = $(this);
	        const cellDate = cell.data("year") === selectedYear &&
	                         cell.data("month") === selectedMonth &&
	                         cell.text() == selectedDate;
	
	        if (cellDate) {
	          cell.addClass("selected");
	        }
	      });
	    }
	  });
	});
	
	$(function() {
		  $("#datepicker").datepicker({
		    minDate: 0, // 과거 날짜 비활성화
		    beforeShowDay: function(date) {
		      const today = new Date();
		      const isTodayOrAfter = date >= today;  // 오늘 날짜 이상인지 체크

		      // 날짜가 오늘 또는 그 이후이면 테두리를 없애는 클래스를 추가
		      return [true, isTodayOrAfter ? 'no-border' : '']; 
		    },
		    onSelect: function(dateText) {
		      // 선택한 날짜에 대한 처리
		      $("#selected_date").val(dateText);
		    }
		  });
		});
  </script>
</head>
<body>
<div class="reservation-container">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="store-info">
		<img src="${pageContext.request.contextPath}/upload/${fplace.fp_storeimg}" width="400">
		<div class="store-details">
			<h1 class="store-name">${fplace.fp_name}</h1>
			<p class="store-time">${fplace.fp_time}</p>
			<p class="store-loc">${fplace.fp_loc}</p>
		</div>
		<form action="reserv.do" method="post" class="m-0auto">
		<input type="hidden" name="fp_num" value="${fplace.fp_num}">
		<div class="input-group">
			<label for="rs-cnt">예약 인원</label>
			<input type="number" id="rs-cnt" name="rs_cnt" min="1" max="6" autocomplete="off" value="${reserv.rs_cnt}">
		</div>
		<div class="time-selection-container">
		    <!-- 달력 -->
		    <div id="datepicker"></div>
		    
		    <!-- 예약 시간 선택 -->
		    <div class="time-selection">
		        <input type="hidden" id="selected_date" name="rs_time" value="rs_time">
		        <div class="reserv-time-select">
		            <c:forEach var="time" items="${list}">
		                <label>
		                    <input type="radio" name="rs_time" value="${fplace_time.ft_time}">
		                    <span>${time.ft_time}</span>
		                </label>	
		            </c:forEach>
		        </div>
		    </div>
		</div>
		<button type="submit" class="btn-submit">예약 신청</button>
        <button type="button" class="btn-cancel" onclick="location.href='detail.do?fp_num=${fplace.fp_num}'">예약취소</button>
		<p>
		예약시 주의 사항<br>
		예약신청 후 사장님의 승인으로 예약확정이 완료됩니다.<br>
		예약시간 노쇼시 이후 예약확정에 어려움이 있을 수 있습니다.<br>
		단체손님 예약은 따로 문의해야합니다.
		</p>
		</form>
		<jsp:include page="/WEB-INF/views/reserv/reservList.jsp"/>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>