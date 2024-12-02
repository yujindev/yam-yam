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
	
	//
	//message_socket.onclose = function(evt){
		//alert('채팅이 종료되었습니다.');
	//};
	
	
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
							output += '<div class="date-position"><span>' + item.chat_sent_at + '</span></div>';
						}

						if (item.chat_sender_num == $('#user_num').val()) {
							output += '<div class="from-position ml-auto">' + item.sender_nickname;
						} else {
							output += '<div class="to-position">' + item.sender_nickname;
						}

						output += '<div class="item">';
						output += (item.chat_read != 0 ? '<b>①</b>' : '') + ' <span>' + item.chat_message + '</span>';
						//시간표시
						output += '<div class="align-right">' + item.chat_sent_at.split(' ')[1] + '</div>';
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
	
	
	//채팅방 삭제
	const chatDeleteBtn = document.getElementById('chatDeleteBtn');
	
	chatDeleteBtn.onclick = function(){
		let notice = confirm('채팅방을 나가시겠습니까? 채팅방을 나가신 후에는 채팅내역이 복구되지 않습니다.');
		if(notice) {
			location.replace('delete.do?')
		}
	}
	
	//초기 데이터 호출
	selectData();		

});