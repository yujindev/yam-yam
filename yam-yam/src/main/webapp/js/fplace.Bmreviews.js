$(function(){
	/*======================================
	 * 좋아요 선택 여부와 선택한 총 개수를 읽기
	 *====================================== */
	function selectBmreviews(){
		//서버와 통신
		$.ajax({
			url:'getBmreviews.do',
			type:'post',
			data:{reviews_num:$('#output_bmreviews').attr('data-num')},
			dataType:'json',
			success:function(param){
				displayBmreviews(param);
			},
			error:function(){
				alert('네트워크 오류 발생11');
			}
		});
	}
	/*======================================
	 * 좋아요 등록 (및 삭제) 이벤트 연결
	 *====================================== */	
	$('#output_bmreviews').click(function(){
		//서버와 통신
		$.ajax({
			url:'writeBmreviews.do',
			type:'post',
			data:{reviews_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인 후 북마크를 눌러주세요');
				}else if(param.result=='success'){
					displayBmreviews(param);
				}else{
					alert('북마크 등록/삭제 오류 발생2');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!2');
			}
		});
	});
	/*======================================
	 * 좋아요 표시 함수
	 *====================================== */		
	function displayBmreviews(param){
		let output;
		if(param.status == 'yesBmreviews'){//북마크 선택
			output = '../images/fav02.gif';
		}else{//북마크 미선택
			output = '../images/fav01.gif';
		}
		//문서 객체에 설정
		$('#output_bmreviews').attr('src',output);
		$('#output_brcount').text(param.count);
	}
	/*======================================
     * 좋아요 표시 함수
	 *====================================== */	
	selectBmreviews();
});




