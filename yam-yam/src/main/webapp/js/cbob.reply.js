$(function(){
	let rowCount = 10;
	let currentPage;
	let count;
	
	/* =========================
	*	댓글 목록
 	*  =========================*/
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;

	// 로딩이미지 노출
	$('#loading').show();
	$.ajax({
		url:'listReply.do',
		type :'post',
		data:{pageNum:pageNum, rowCount:rowCount, cbob_num:$('#cbob_num').val()},
		dataType :'json',
		success:function(param){
			// 로딩이미지 감추기
			$('#loading').hide();
			count = param.count;
			if(pageNum==1){
				$('#output').empty();
			}
			
			$(param.list).each(function(index,item){
	        let output = '<div class="item mt-1 p-1 bg-gr150 position-r">';
	        output += '<h4 class="fw-600 fs-12"><a href="#">' + item.mem_nickname + '</a></h4>';
	        output += '<div class = "sub-item">';
	        output += '<p class="p-1">' + item.cbob_re_content + '</p>';
	
	        if(item.cbob_re_mdate){
	            output += '<span class = "modify-date fs-08 text-gr450 block-box text-r mr-2"> 최근 수정일 :' + item.cbob_re_mdate + ' </span>';
	        } else{
	            output += '<span class = "modify-date fs-08 text-gr450 block-box text-r mr-2"> 등록일 :' + item.cbob_re_date + ' </span>';
	        }
	
	        if(param.user_num == item.mem_num){
	            output += ' <input type="button" data-renum="'+item.cbob_re_num+'" class="modify-btn btn-edit btn-reEidt">'
	            output += ' <input type="button" data-renum="'+item.cbob_re_num+'" class="delete-btn btn-close btn-reClose">'
	        }
	            output += '</div>';
	            output +='</div>';
	            $('#output').append(output);
	    });
			if(currentPage >= Math.ceil(count/rowCount)){
				// 다음 페이지가 없음
				$('.paging-button').hide();
			} else{
				$('.paging-button').show();
			}
	   }, 
	   error:function(){    
		$('#loading').hide();
		alert('네트워크 오류발생');
		}
	});
}
$('.paging-button input').click(function(){
		selectList(currentPage + 1); 
});
	
	/* =========================
	*	댓글 등록
	*  =========================*/
	//댓글 등록 이벤트 연결
	$('#re_form').submit(function(event){
		if($('#cbob_re_content').val().trim()==''){
			alert('내용을 입력해라');
			$('#cbob_re_content').val('').focus();
			return false;
		}
		//form 이하의 태그에 입력한 데이터를 모두 읽어서 쿼리 스트링으로 반환
		let form_data = $(this).serialize();
		// 서버와  통신
		$.ajax({
			url:'writeReply.do',
			   type :'post',
			   data:form_data,
			   dataType :'json',
			   success:function(param){
				if(param.result=='logout'){
					alert('로그인 후 작성가능');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글 목록을 다시 호출함
					selectList(1);
				}else{
					alert('오타');
				}
		   }, error:function(){    
		       alert('네트워크 오류발생1');
		   }
		});
		event.preventDefault();
	});

	// 댓들 작성 폼 초기화
	function initForm(){
			$('textarea').val('');
			$('#cbob_re_first.letter-count').text('300/300');
	}
	/* =========================
	*	댓글 수정
	*  =========================*/
	// 댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		// 댓글 번호
		let cbob_re_num = $(this).attr('data-renum');
		// 댓글 내용
		let cbob_re_content = $(this).parent().find('p').html().replace(/<br>/gi, '\n'); 
																// g:지정문자열 모두 , i: 대소문자 무시
		// 댓글 수정폼 UI
		let modifyUI = '<form id="mre_form" class="p-05">';
		modifyUI += '<input type ="hidden" name="cbob_re_num" id="cbob_mre_num" value="'+cbob_re_num+'">';
		modifyUI += '<textarea rows="3" cols="50" name="cbob_re_content" id="cbob_mre_content" class="rep-content w-100">'+cbob_re_content+'</textarea>';
		modifyUI += '<div id="mre_first" class="no-float text-r w-100"><span class="letter-count">300/300</span></div>';
		modifyUI += '<div id="mre_second" class="no-float text-r w-100">';
		modifyUI += ' <input type="submit" value="수정" class="btn-re btn-line-primary">';
		modifyUI += ' <input type="button" class="re-reset btn-close btn-reClose">';
		modifyUI += "</div>";
		modifyUI += '</form>';
		
		// 이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면 숨김 sub-item 클래스로 지정한 div를 환원 시키고 수정폼 제거
		initModifyForm();
		
		// 지금 클릭해서 수정하고자하는 데이터는 감추기 수정버튼을 감싸고 있는 div
		$(this).parent().hide();
		
		// 수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		$(this).parents('.item').append(modifyUI);
		
		// 입력한 글자수 셋팅
		let inputLength = $('#cbob_mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);
		
	});

	// 댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	
	// 수정폼에서 취소 버튼 클릭시 수정폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});
	// 댓글수정
	$(document).on('submit', '#mre_form', function(event){
	            if($('#cbob_mre_content').val().trim()==''){
	                alert('내용을 입력하세요!');
	                $('#cbob_mre_content').val('').focus();
	                return false;
	            }
	            //폼에 입력한 데이터 반환
	            let form_data = $(this).serialize();
	            //서버와 통신
	            $.ajax({
	                url:'updateReply.do',
	                type:'post',
	                data:form_data,
	                dataType:'json',
	                success:function(param){
	                    if(param.result =='logout'){
	                        alert('로그인해야 수정할 수 있습니다.');
	                    }else if(param.result == 'success'){
	                        $('#mre_form').parent().find('p').html(
	                            $('#cbob_mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g, '<br>'));
	                            $('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초미만');
	                            //수정폼 삭제 및 초기화
	                            initModifyForm();
	                    }else if(param.result == 'wrongAccess'){
	                        alert('타인의 글을 수정할 수 없습니다.');
	                    }else{
	                        alert('댓글 수정 오류 발생');
	                    }
	                },
	                error:function(){
	                    alert('네트워크 오류 발생2');
	                }
	            });
	            //기본이벤트 제거
	            event.preventDefault();
	        });
	/* =========================
	*	댓글 등록 및 수정 공통
	*  =========================*/
	$(document).on('keyup', 'textarea', function(){
	       //입력한 글자수 구함
	       let inputLength = $(this).val().length;
	       
	       if(inputLength>300){//300자가 넘어선 경우
	           $(this).val($(this).val().substring(0,300));
	       }else{//300자 이하인경우
	           let remain = 300 - inputLength;
	           remain +='/300';
	           if($(this).attr('id') == 'cbob_re_content'){
	               //등록폼 글자수
	               $('#re_first .letter-count').text(remain);
	           }else{
	               //수정폼 글자수
	               $('#mre_first .letter-count').text(remain);
	           }
	       }
	   });
	   
	   /* =========================
	   	*	댓글 삭제
	   	*  =========================*/		
		$(document).on('click','.delete-btn',function(){
		        //댓글번호
		        let re_num = $(this).attr('data-renum');
		        //서버와 통신
		        $.ajax({
		            url: 'deleteReply.do',
		            type:'post',
		            data:{cbob_re_num:re_num},
		            dataType:'json',
		            success:function(param){
		                if(param.result=='logout'){
		                    alert('로그인해야 삭제할 수 있습니다.');
		                }else if(param.result == 'success'){
		                    alert('삭제완료!');
		                    selectList(1);
		                }else if(param.result == 'wrongAccess'){
		                    alert('타인의 글을 삭제할 수 없습ㄴ디ㅏ.');
		                }else{
		                    alert('댓글 삭제 오류 발생!');
		                }
		            },
		            error:function(){
		                alert('네트워크 오류 발생3');
		            }
		        });
		    });
	/* =========================
	*	초기 데이터 호출
	*  =========================*/		
	selectList(1);
});