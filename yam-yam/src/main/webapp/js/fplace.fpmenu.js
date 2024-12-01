$(function () {
	let rowCount = 50;
    let currentPage;
	let count;
	let currentIndex = 0; // 현재 슬라이드 시작 인덱스
	let isLoading = false; // 로딩 중인지 여부

    // 관리자 여부 체크 (서버에서 전달하는 값 사용)
   // const isAdmin = $('#user_auth').val() == '9';

    /*===========================
     * 메뉴 목록 출력 00
    ============================*/

	function selectMenuList(pageNum) {
	    currentPage = pageNum;
		if (isLoading) return; // 이미 로딩 중이라면 실행하지 않음
		   isLoading = true; // 로딩 시작

	    // 로딩 이미지 노출
	    $('#loading').show();

	    // 서버와 통신
	    $.ajax({
	        url: 'listFpmenu.do',
	        type: 'post',
	        data: { pageNum: pageNum, rowCount: rowCount, fp_num: $('#fp_num').val() },
	        dataType: 'json',
	        success: function (param) {
	            // 로딩 이미지 감추기
	            $('#loading').hide();

	            count = param.count;//전체 항목 수 저장

	            if (pageNum === 1) {
	                // 처음 호출 시 목록 초기화
	                $('#menu_output').empty(); // 슬라이더 초기화
	                currentIndex = 0; // 초기화
	            }
				if (param.list && param.list.length > 0) {
					//목록이 있을때만 항목 추가
	            $(param.list).each(function (index, item) {
	                let output = '<div class="menu-item">';
	                output += '<div class="menu-info">';
	                output += '<img src="../upload/' + item.fpmenu_img + '" alt="메뉴 이미지" class="menu-img">';
	                output += `<h4>${item.fpmenu_name}</h4>`;
	                output += `<p>${item.fpmenu_price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원</p>`;

					// 관리자 또는 작성자에게만 수정/삭제 버튼 표시
					if (param.user_num && (param.user_auth == 9 || param.user_num == item.mem_num)) {
	                    output += '<input type="button" value="수정" class="modify-menu-btn" data-renum="' + item.fpmenu_num + '">';
	                    output += '<input type="button" value="삭제" class="delete-menu-btn" data-renum="' + item.fpmenu_num + '">';
	                }
	                output += '<hr size="1" noshade width="100%">';
	                output += '</div>';
	                output += '</div>';

	                // 메뉴 항목 추가
	                $('#post-scroll').append(output);
	            });
				
				} else {
               // 목록이 없을 때 메시지 출력 (선택 사항)
               if (pageNum === 1) {
                   $('#post-scroll').html('<h2s>등록된 메뉴가 없습니다.</h2>');
               }
           }
				
	        },
	        error: function () {
	            $('#loading').hide();
	            alert('네트워크 오류 발생');
	        },
	    });
	}


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
	$(document).on('click', '.modify-menu-btn', function () {
	        const userAuth = $('#user_auth').val();
	        const userNum = $('#user_num').val();
	        const memNum = $(this).data('mem-num'); // 작성자의 mem_num 가져오기

	        if (!(userAuth == 9 || userNum == memNum)) {
	            alert('수정 권한이 없습니다.');
	            return;
	        }

	        let fpmenu_num = $(this).attr('data-renum');
	        let menuItem = $(this).closest('.menu-item');
	        let menuName = menuItem.find('h4').text();
	        let menuPrice = menuItem.find('p').text().replace('원', '');

	        let modifyUI = '<form id="modify_menu_form">';
	        modifyUI += `<input type="hidden" name="fpmenu_num" value="${fpmenu_num}">`;
	        modifyUI += `<input type="text" name="fpmenu_name" value="${menuName}">`;
	        modifyUI += `<input type="number" name="fpmenu_price" value="${menuPrice}">`;
	        modifyUI += '<input type="file" name="fpmenu_img" accept="image/*">';
	        modifyUI += '<input type="submit" value="수정">';
	        modifyUI += '<input type="button" value="취소" class="cancel-modify-menu">';
	        modifyUI += '</form>';

	        initModifyForm();
	        $(this).parent().hide();
	        menuItem.append(modifyUI);
	    });

	    $(document).on('submit', '#modify_menu_form', function (event) {
	        event.preventDefault();

	        const form_data = new FormData($(this)[0]);
	        const userAuth = $('#user_auth').val();
	        const userNum = $('#user_num').val();
	        const memNum = $(this).data('mem-num');

			// 수정 권한 확인
	        if (!(userAuth == 9 || userNum == memNum)) {
	            alert('수정 권한이 없습니다.');
	            return;
	        }

	        $.ajax({
	            url: 'updateFpmenu.do',
	            type: 'post',
	            data: form_data,
	            processData: false,
	            contentType: false,
	            dataType: 'json',
	            success: function (param) {
	                if (param.result === 'logout') {
	                    alert('로그인해야 수정할 수 있습니다.');
	                } else if (param.result === 'success') {
	                    alert('메뉴가 수정되었습니다.');
	                    initModifyForm();
	                    selectMenuList(1); // 목록 새로고침
	                } else {
	                    alert('메뉴 수정 오류 발생');
	                }
	            },
	            error: function () {
	                alert('서버와 통신 중 오류가 발생했습니다.');
	            }
	        });
	    });
		
		
		/*===========================
		    * 메뉴 삭제
		   ============================*/

	    $(document).on('click', '.delete-menu-btn', function () {
	        const userAuth = $('#user_auth').val();
	        const userNum = $('#user_num').val();
	        const memNum = $(this).data('mem-num');

	        if (!(userAuth == 9 || userNum == memNum)) {
	            alert('삭제 권한이 없습니다.');
	            return;
	        }

	        let fpmenu_num = $(this).attr('data-renum');

	        $.ajax({
	            url: 'deleteFpmenu.do',
	            type: 'post',
	            data: { fpmenu_num: fpmenu_num },
	            dataType: 'json',
	            success: function (param) {
	                if (param.result === 'logout') {
	                    alert('로그인해야 삭제할 수 있습니다.');
	                } else if (param.result === 'success') {
	                    alert('메뉴가 삭제되었습니다.');
	                    selectMenuList(1); // 목록 새로고침
	                } else {
	                    alert('메뉴 삭제 오류 발생');
	                }
	            },
	            error: function () {
	                alert('서버와 통신 중 오류가 발생했습니다.');
	            }
	        });
	    });

	    function initModifyForm() {
	        $('.menu-info').show();
	        $('#modify_menu_form').remove();
	    }

	    selectMenuList(1); // 초기 데이터 로드
	});