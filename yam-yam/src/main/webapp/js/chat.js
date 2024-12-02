$(function() {
		
	
	//웹소켓 생성
	const message_socket = new WebSocket("ws://localhost:8080/yam-yam/webSocket");
	message_socket.onopen = function(evt){
		message_socket.send('one:');
	};
	//서버로부터 메시지를 받으면 호출되는 함수 지정
	message_socket.onmessage = function(evt){
		//메시지 알림
		let data = evt.data;
		if(data.substring(0,4) == 'one:') {
			selectData();
		}
	};
	
	
	//엔터키 처리 이벤트 연결 
	$('#message').keydown(function(event){
		if(event.keyCode == 13 && !event.shiftKey){
			$('#chatting_form').trigger('submit');
		}
	});

	//채팅 내역
	function selectData() {
		//서버와 통신
		$.ajax({
			url: 'chatHistory.do',
			type: 'post',
			data: {chat_receiver_num: $('#chat_receiver_num').val()},
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 후 사용하세요');
				} else if (param.result == 'success') {
					$('#chatting_message').empty();
					let chat_sent_at = '';
					$(param.list).each(function(index, item) {
						let output = '';
						//날짜 추출
						if (chat_sent_at != item.chat_sent_at.split('')[0]) {
							chat_sent_at = item.chat_sent_at.split('')[0];
							output += '<p class="date-position w-50">' + item.chat_sent_at.split(' ')[0] + '</p>';
						}

						if (item.chat_sender_num == $('#user_num').val()) {
							output += '<div class="from-position ml-auto"><p class="fw-700">' + item.sender_nickname+'</p>';
						} else {
							output += '<div class="to-position"><p class="fw-700">' + item.sender_nickname+'</p>';
						}

						output += '<div class="item">';
						output += ' <p>' + item.chat_message + '</p>';
						//시간표시
						output += '<p class="text-r fs-08">' + item.chat_sent_at.split(' ')[1] + '</p>';
						output += (item.chat_read != 0 ? '<span class="fw-800 text-main">1</span>' : '');
						output += '</div>';
						output += '</div>';

						//문서 객체에 추가
						$('#chatting_message').append(output);
						//스크롤을 하단으로 위치시킴
						$('#chatting_message').scrollTop($('#chatting_message')[0].scrollHeight);
					});
				}else {
					alert('채팅 읽기 오류 발생');
					message_socket.close();
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
				message_socket.close();
			}

		});
	}

	//채팅 등록 

	$('#chatting_form').submit(function(event) {
		if ($('#message').val().trim() == '') {
			alert('내용을 입력하세요!');
			$('#message').val('').focus();
			return false;
		}

		let form_data = $(this).serialize();

		//서버와 통신
		$.ajax({
			url: 'writeChatMsg.do',
			type: 'post',
			data: form_data,
			dataType: 'json',
			success: function(param) {
				if (param.result == 'logout') {
					alert('로그인 해야 작성할 수 있습니다.');
				} else if (param.result = 'success') {
					//폼초기화
					$('#message').val('').focus();
					message_socket.send('one:');
				} else {
					alert('채팅 등록 오류 발생');
					message_socket.close();
				}
			},
			error: function() {
				alert('네트워크 오류 발생');
				message_socket.close();
			}
		});
		
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	//채팅 메시지 글자 수 체크
			$(document).on('keyup', 'textarea', function() {
			    // 입력한 글자수 구하기
			    let inputLength = $(this).val().length;

			    if (inputLength > 300) { // 300자를 넘어선 경우
			        $(this).val($(this).val().substring(0, 300)); // 초과한 부분 제거
							alert('300자를 초과할 수 없습니다.');
			        inputLength = 300; // 글자 수를 300으로 고정
			    }

			    // 남은 글자 수 계산
			    let remain = 300 - inputLength;

			    // 남은 글자 수 표시
			    if ($(this).attr('id') === 'chatting_form') {
			        $('#letter-count').text(remain + '/300'); // 등록폼 글자수
			    } else {
			        $('#letter-count').text(remain + '/300'); // 기본 글자수
			    }
			});
	
	
	
});