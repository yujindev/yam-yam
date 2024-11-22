$(function () {
	let rowCount = 3;
    let currentPage;
	let count;
	let currentIndex = 0; // 현재 슬라이드 시작 인덱스

    // 관리자 여부 체크 (서버에서 전달하는 값 사용)
   // const isAdmin = $('#user_auth').val() == '9';

    /*===========================
     * 메뉴 목록 출력 00
    ============================*/
    function selectMenuList(pageNum) {
        currentPage = pageNum;
		
		//로딩 이미지 노출
		$('#loading').show();

        // 서버와 통신
        $.ajax({
            url: 'listFpmenu.do',
            type: 'post',
            data: {pageNum: pageNum, rowCount: rowCount, fp_num: $('#fp_num').val()},
            dataType: 'json',
            success:function(param) {
				//로딩 이미지 감추기
				$('#loading').hide();
				
				count = param.count;

                if (pageNum === 1) {
                    // 처음 호출 시 목록 초기화
                    $('#menu_output').empty();//슬라이더 초기화??
                }

                $(param.list).each(function (index, item) {
					let output = '<div class="menu-item">';
		                //이미지 삽입해야함
		                output += '<div class="menu-info">';
						output += '<img src="../upload/'+item.fpmenu_img+'" alt="메뉴 이미지" class="menu-img">';
		                output += `<h4>${item.fpmenu_name}</h4>`;
		                output += `<p>${item.fpmenu_price}원</p>`;

                    // 관리자만 수정, 삭제 버튼 표시
                    if (param.user_num) {
                        output += '<input type="button" value="수정" class="modify-menu-btn" data-renum= "'+ item.fpmenu_num + '">';
                        output += '<input type="button" value="삭제" class="delete-menu-btn" data-renum= "'+ item.fpmenu_num + '">';
                    }
					output += '<hr size = "1" noshade width="100%">';
                    output += '</div>';
                    output += '</div>';

                    // 메뉴 출력
                    $('#menu_output').append(output);
                });

                 //페이지 버튼 처리
                if (currentPage >= Math.ceil(param.count / rowCount)) {
                    $('.menu-paging-button').hide();
                } else {
                   $('.menu-paging-button').show();
                }
				
				//updateSlider();//슬라이더 업데이트
            },
            error: function () {
				$('#loading').hide();
                alert('네트워크 오류 발생1');
            },
        });
    }
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
			$('.menu-paging-button input').click(function(){
				selectMenuList(currentPage+1);
			});


	
	
	//슬라이더 함수
	//function updateSlider(){
		//const slideWrapper = document.querySelector('#menu_output');
	//	const itemWidth = doucument.quertySelector('.menu-item').offsetWidth;
		//slideWrapper.style.transform = 'translateX(-${currentIndex * itemWidth}px)';
//	}
                
		/* 이전 버튼 클릭 이벤트 추가 확인 필요함;;;;
		//$('.slide_prev').click(function () {
		    const totalItems = $('.menu-item').length;
		    if (currentIndex > 0) {
		        currentIndex--;
		        updateSlider();
		    }
		});

		= 다음 버튼 클릭 이벤트
		$('.slide_next').click(function () {
		    const totalItems = $('.menu-item').length;
		    if (currentIndex < totalItems - 3) {
		        currentIndex++;
		        updateSlider();
		    }
		});

		// 초기 데이터 로드
		$(document).ready(function () {
		    selectMenuList(1);
		});


    /*===========================
     * 메뉴 등록
    ============================*/
	$('#fpmenu_form').submit(function (event) {
		
		const form_data = new FormData($(this)[0]);

	    $.ajax({
	        url: 'writeFpmenu.do',
	        type: 'post',
	        data: form_data,
	        processData: false,
	        contentType: false,
	        dataType: 'json',
	        success: function (param) {
	            if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result === 'success') {
					//폼 초기화
					$('#fpmenu_name').val('');
					$('#fpmenu_price').val('');
					$('#fpmenu_img').val('');
										
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글 목록을 다시 호출함
					selectMenuList(1);
				} else {
	                alert('메뉴 등록에 실패했습니다.');
	            }
	        },
	        error: function () {
	            alert('서버와 통신 중 오류가 발생했습니다.2');
	        }
	    });
		//기본 이벤트 제거
		event.preventDefault();
		});

    /*===========================
     * 메뉴 수정 (관리자만 사용 가능)
    ============================*/
	//메뉴 수정
    $(document).on('click', '.modify-menu-btn', function () {
        if ($('#user_auth').val() == '9') return;
		
        let fpmenu_num = $(this).attr('data-renum');
        let menuItem = $(this).closest('.menu-item');
        let menuName = menuItem.find('h4').text();
        let menuPrice = menuItem.find('p').text().replace('원', '');

        let modifyUI = '<form id="modify_menu_form">';
        modifyUI += '<input type="hidden" name="fpmenu_num" value="' + fpmenu_num + '">';
        modifyUI += '<input type="text" name="fpmenu_name" value="' + menuName + '">';
        modifyUI += '<input type="number" name="fpmenu_price" value="' + menuPrice + '">';
        modifyUI += '<input type="file" name="fpmenu_img" accept="image/*" >';
		modifyUI += '<input type="submit" value="수정">';
        modifyUI += '<input type="button" value="취소"" class="cancel-modify-menu">';
        modifyUI += '</form>';
		
		//이전에 이미 수정하는 메뉴가 있을 경우 수정버튼을 클릭하면 숨김 sub-item 클래스로 지정한 dic를 환원시키고 수정폼을 제거
		initModifyForm();
	
		// 지금 클릭해서 수정하고자하는 데이터는 감추기 수정버튼을 감싸고 있는 div
		$(this).parent().hide();
		        
        // 수정폼 추가
        menuItem.append(modifyUI);
    });
	
	// 댓글 수정폼 초기화
	    function initModifyForm(){
	        $('.menu-info').show();
	        $('#modify_menu_form').remove();
	    }
	// 수정폼에서 취소 버튼 클릭시 수정폼 초기화
		$(document).on('click','.cancel-modify-menu',function(){
		 initModifyForm();
		 });

	
	//메뉴 수정---여기서부터 수정 
    $(document).on('submit', '#modify_menu_form', function (event) {
        if ($('#user_auth').val() == '9') return;
		
       const form_data = new FormData($(this)[0]);
		
        $.ajax({
            url: 'updateFpmenu.do',
            type: 'post',
            data: form_data,
			processData: false, // FormData 사용 시 필수
			contentType: false, // FormData 사용 시 필수
            dataType: 'json',
            success: function (param) {
                if (param.result === 'logout') {
                    alert('로그인해야 수정할 수 있습니다.');
                }else if(param.result == 'success'){
					//수정폼 삭제 및 초기화
					initModifyForm();
			}else if(param.result == 'wrongAccess'){
				alert('메뉴를 수정할 수 없습ㄴ디ㅏ.');
			}else{
				alert('댓글 수정 오류 발생');
			}
			
            }, error: function () {
                alert('네트워크 오류 발생3');
            }
        });
		//기본이벤트 제거
		event.preventDefault();
    });

    $(document).on('click', '.cancel-modify-menu', function () {
        $(this).closest('.menu-item').find('.menu-info').show();
        $('#modify-menu-form').remove();
    });

    /*===========================
     * 메뉴 삭제 (관리자만 사용 가능)//00
    ============================*/
    $(document).on('click', '.delete-menu-btn', function () {
		if ($('#user_auth').val() == '9') return;
		let fpmenu_num = $(this).attr('data-renum');
        $.ajax({
            url: 'deleteFpmenu.do',
            type: 'post',
            data: {fpmenu_num: fpmenu_num},
            dataType: 'json',
            success: function (param) {
                if (param.result === 'logout') {
                    alert('로그인해야 삭제할 수 있습니다.');
                } else if (param.result === 'success') {
                    alert('메뉴가 삭제되었습니다.');
                    selectMenuList(1);
                } else {
                    alert('메뉴 삭제 오류 발생');
                }
            },
            error: function () {
                alert('네트워크 오류 발생4');
            }
        })
		
});
		// 초기 데이터 호출
		selectMenuList(1);
});