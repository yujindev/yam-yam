$(function(){
	let clickedElement;
	/*======================================
	 * 좋아요 등록 (및 삭제) 이벤트 연결
	 *====================================== */	
	$('.output_bmstore').click(function(){
		clickedElement = $(this); // 클릭된 요소 저장
		//const fpNum = clickedElement.attr('data-num'); // 클릭된 요소의 데이터
		//서버와 통신
		$.ajax({
			url:'writeBmstore.do',
			type:'post',
			data:{fp_num:clickedElement.attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인 후 북마크를 눌러주세요');
				}else if(param.result=='success'){
					displayBmstore(param);
				}else{
					alert('가게 북마크 등록/삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	/*======================================
	 * 좋아요 표시 함수
	 *====================================== */		
	function displayBmstore(param){
		let output;
		if(param.status == 'yesBmstore'){//북마크 선택
			output = '../images//icon-flag-o.png';
		}else{//북마크 미선택
			output = '../images//icon-flag-g.png';
		}
		//문서 객체에 설정
		clickedElement.attr('src',output);
	}
});




