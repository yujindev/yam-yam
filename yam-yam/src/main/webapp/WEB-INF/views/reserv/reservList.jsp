<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
/* 테이블 스타일 */
/* 예약 내역 제목 */
.sreserv-title {
    font-size: 19px;
    color: #ff7800;
    text-align: center;
    font-weight: bold;
    margin-bottom: 5px;
}

/* 예약 내역 전체 컨테이너 */
.sreserv-container {
    display: flex;
    flex-direction: column; /* 세로 정렬 */
    align-items: center; /* 가로 가운데 정렬 */
    justify-content: center; /* 세로 가운데 정렬 */
    margin: 0;
    padding: 15px;
    width: 140px;
    max-width: 140px;
    background-color: #fff;
    border-radius: 15px;
    border: 2px solid #ff7800;
    position: fixed; /* 절대 위치로 조정 */
    top: 200px; /* 화면 기준 위에서부터 200px */
    right: 100px; /* 화면 기준 오른쪽에서부터 100px */
    z-index: 1000; /* 다른 요소보다 위에 표시 */
    overflow: hidden;
}

/* 예약 항목 스타일 */
.sreserv-details {
    font-family: Arial, sans-serif;
}

/* 예약 항목 */
.sreserv-item {
    margin-bottom: 20px;
}

/* 항목 이름 */
.sreserv-label {
    font-size: 14px;
    color: #ff7800;
    margin-bottom: 5px;
}

/* 예약 정보 */
.sreserv-info {
    font-size: 13px;
    color: #757575;
    margin-bottom: 10px;
}

/* 구분선 */
.sreserv-divider {
    border: 0;
    border-top: 1px solid #ff7800;
    margin: 20px 5px;
}

/* 예약 건수를 동그라미 안에 표시 */
.sreserv-count-wrapper {
    text-align: center;
    margin-bottom: 10px;
}

/* 예약 건수 */
.sreserv-count {
    display: inline-block;
    background-color: #ff7800;
    color: white;
    font-size: 20px;
    font-weight: bold;
    padding: 0px;
    border-radius: 50%;
    width: 50px;
    height: 50px;
    line-height: 50px;
    /* 텍스트를 세로로 가운데 정렬 */
    text-align: center;
}
</style>
<div id="soutput"></div>
<script>
//로딩 이미지 노출
$('#loading').show();
//서버와 통신
$.ajax({
	url:'${pageContext.request.contextPath}/fplace/reservList.do',
	type:'post',
	data:{mem_num:$('#mem_num').val()},
	dataType:'json',
	success:function(param){
		let rs_now;
		if(param.result=='logout'){
			alert('비회원은 예약내역이 존재하지 않습니다.');
		}else if(param.result == 'success'){
			let output = '<div class="sreserv-container">'
				output += '<div class="sreserv-list">';
				output += '<h2 class="sreserv-title">현재 예약 내역</h2>';
				output += '<div class="sreserv-count-wrapper">';
				output += '<div class="sreserv-count">'+param.rs_now+'<small><small>건</small></small></div>';
				output += '</div>';  
				output += '<div class="sreserv-items">';
			$(param.reserv).each(function(index,item){
				output += '<div class="sreserv-item">';
				output += '<div class="sreserv-label">식당명</div>';
				output += '<div class="sreserv-info">'+item.fp_name+'</div>';
				output += '<div class="sreserv-label">예약일시</div>';
				output += '<div class="sreserv-info">'+item.rs_time+'</div>';
				output += '<div class="sreserv-label">예약인원</div>';
				output += '<div class="sreserv-info">'+item.rs_cnt+'</div>';
				output += '<div class="sreserv-label">예약상태</div>';
				if(item.rs_status == 1){
				output += '<div class="sreserv-info">예약 확인중</div>';					
				}else if(item.rs_status == 2){
				output += '<div class="sreserv-info">예약 완료</div>';}
				output += '</div>'
				//구분선 대신 클래스 추가하여 스타일링
				output += '<div class="sreserv-divider"></div>';
				
			});
				output += '</div>';
				output += '</div>';
				output += '</div>';
			$('#soutput').append(output);
			
		}
	},
	error:function(){
		$('#loading').hide();
	}
})
</script>


