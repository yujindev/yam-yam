$(function(){
	let rowCount = 10;
	let currentPage;
	let count;
	/*======================================
	 * 댓글 목록
	 *====================================== */
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		
		//로딩 이미지 노출
		$('#loading').show();
		//서버와 통신
		$.ajax({
			url:'listReviews.do',
			type:'post',
			data:{pageNum:pageNum,rowCount:rowCount, fp_num:$('#fp_num').val()},
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<h3><a href="../others/saveStore.do?mem_id='+item.mem_id+'">'+item.mem_id + '</a></h3>';
					output += '<h3>'+item.fp_name + '</h3>';
					output += '<div class="sub-item">';
					output += '<p>'+item.reviews_con+'</p>';
					if (item.reviews_img1) {
					      // 이미지 경로가 존재하는 경우 이미지 태그로 렌더링
					     output += '<img src="../upload/'+item.reviews_img1+'" width="200" height="200">';
					}
					output+='<div class="top-actions">';
					//로그인한 회원번호와 작성자의 회원번호 일치 여부
					if(param.user_num == item.mem_num){
						//로그인한 회원번호와 작성자 회원번호 일치
						output += ' <input type="button" data-renum="'+item.reviews_num+'" value="삭제" class="delete-btn">';
						output += '<img class="output_bmreviews disabled" src="../images/fav-disabled.gif" width="50">';
					}else{
						// 북마크 버튼
						if (item.bookmarked) {
							output += '<img class="output_bmreviews" data-num="' + item.reviews_num + '" src="../images/fav02.gif" width="50">';
						} else {
							output += '<img class="output_bmreviews" data-num="' + item.reviews_num + '" src="../images/fav01.gif" width="50">';
						}
					}
					output += '<span class="output_brcount"> '+item.reviews_count+'</span>'; 
					output+='</div>';
					
					output += '<hr size = "1" noshade width="98%">';
					//등록일
					output += '<span class="modify-date">등록일 : ' + item.reviews_date + '</span>';
					output += '<hr size = "1" noshade width="100%">';
					output += '</div>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});//end of each
				
				//page button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				$('#loading').hide();
				alert('네트워크 오류 발생1');
			}
		});
	}
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage+1);
	});
	/*======================================
	 * 댓글 등록
	 *====================================== */
	//댓글 등록 이벤트 연결
	$('#reviews_form').submit(function(event){
		if($('#reviews_con').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#reviews_con').val('').focus();
			return false;
		}
		//form 이하의 태그에 입력한 데이터를 모두 읽어서 쿼리 스트링으로 반환
		const form_data = new FormData($(this)[0]);
				
		//서버와 통신
		$.ajax({
			url:'writeReviews.do',
			type:'post',
			data:form_data,
			processData: false, // FormData로 전송할 때 필요
			contentType: false,
			dataType : 'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result=='success'){
					//폼 초기화
					initForm();
					$('.stars input[type="radio"]').prop('checked', false); //별점 작성 후 조기화
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글 목록을 다시 호출함
					selectList(1)
				}else{
					alert('댓글 등록 오류 발생');
				}
			},error:function(){
				alert('네트워크 오류 발생2');
			}
		});
		//기본 이벤트 제거
		event.preventDefault();
		});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('reviews_first .letter-count').text('300/300');
	}
	
	/*======================================
	 * 댓글 등록 및 수정 공통(textarea에 내용 입력시 글자수 체크)
	 *====================================== */
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함
		let inputLength = $(this).val().length;
		
		if(inputLength>300){ //300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id') == 'reviews_con'){
				//등록폼 글자수
				$('#reviews_first .letter-count').text(remain);
			}
		}
	});
	/*======================================
	* 댓글 삭제
	*====================================== */
	$(document).on('click','.delete-btn',function(){
			//댓글 번호
			let reviews_num = $(this).attr('data-renum');
			
				//서버와 통신
				$.ajax({
					url:'deleteReviews.do',
					type:'post',
					data:{reviews_num:reviews_num},
					dataType:'json',
					success:function(param){
						if(param.result=='logout'){
							alert('로그인해야 삭제할 수 있습니다.');
						}else if(param.result=='success'){
							alert('삭제완료');
							selectList(1);
						}else if(param.result =='wrongAccess'){
							alert('타인의 글을 삭제할 수 없습니다.');
						}else{
							alert('댓글 삭제 오류 발생');
						}
					},
					error:function(){
						alert('네트워크 오류 발생3');
					}
				})
			});
			
			
	// 북마크 등록 (및 삭제) 이벤트 연결
	$(document).on('click', '.output_bmreviews', function () {
	    const button = $(this); // 클릭된 버튼 요소
		//const parentItem = button.closest('.item'); // 해당 리뷰 블록 전체를 찾음
		//const countElement = parentItem.find('.output_brcount'); // 같은 리뷰의 북마크 개수 요소
		const countElement = button.siblings('.output_brcount'); // 북마크 개수 요소
		
		if ($(this).hasClass('disabled')) {
		        alert('본인의 글은 북마크할 수 없습니다.');
		        return false; // 클릭 이벤트 중단
		}
	    // 서버와 통신
	    $.ajax({
	        url: 'writeBmreviews.do',
	        type: 'post',
	        data:{reviews_num:$(this).attr('data-num')},
	        dataType: 'json',
	        success: function (param) {
	            if (param.result === 'logout') {
	                alert('로그인 후 북마크를 눌러주세요');
					// 현재 페이지 URL 가져오기
					const currentUrl = encodeURIComponent(window.location.href);
					window.location.href = '../member/loginForm.do?redirectUrl=' + currentUrl;
	            } else if (param.result === 'success') {
					// 북마크 상태 업데이트
					const newSrc = param.status === 'yesBmreviews'
					             ? '../images/fav02.gif'
					             : '../images/fav01.gif';
					               button.attr('src', newSrc); // 클릭된 버튼의 이미지 변경
	                //$('.output_brcount').text(param.count); // 북마크 개수 업데이트
					countElement.text(param.count); // 해당 리뷰의 북마크 개수 업데이트
				} else {
	                alert('북마크 등록/삭제 오류 발생');
	            }
	        },
	        error: function () {
	            alert('네트워크 오류 발생!');
	        }
	    });
	});
	
	/*======================================
	* 초기 데이터(목록) 호출
	*====================================== */
	selectList(1);
});
