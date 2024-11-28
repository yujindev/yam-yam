package kr.chat.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class ChatVO {
	private long chat_num; // 채팅방 번호
	private long chat_sender_num; // 보낸 사람 회원번호
	private long chat_receiver_num; // 받는 사람 회원번호
	private String chat_message;// 메시지
	private String chat_sent_at; // 메시지 발송시각
	private int chat_read; // 메시지 읽기여부 확인(0:읽음 1: 읽지않음)
	private String sender_nickname; // 보낸사람 닉네임
	private String receiver_nickname; // 받는사람 닉네임

	private String id;
	private int cnt;

	public long getChat_num() {
		return chat_num;
	}

	public void setChat_num(long chat_num) {
		this.chat_num = chat_num;
	}

	public long getChat_sender_num() {
		return chat_sender_num;
	}

	public void setChat_sender_num(long chat_sender_num) {
		this.chat_sender_num = chat_sender_num;
	}

	public long getChat_receiver_num() {
		return chat_receiver_num;
	}

	public void setChat_receiver_num(long chat_receiver_num) {
		this.chat_receiver_num = chat_receiver_num;
	}

	public String getChat_message() {
		return chat_message;
	}

	public void setChat_message(String chat_message) {
		this.chat_message = chat_message;
	}

	public String getChat_sent_at() {
		return chat_sent_at;
	}

	public void setChat_sent_at(String chat_sent_at) {
		this.chat_sent_at = chat_sent_at;
	}

	public int getChat_read() {
		return chat_read;
	}

	public void setChat_read(int chat_read) {
		this.chat_read = chat_read;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getSender_nickname() {
		return sender_nickname;
	}

	public void setSender_nickname(String sender_nickname) {
		this.sender_nickname = sender_nickname;
	}

	public String getReceiver_nickname() {
		return receiver_nickname;
	}

	public void setReceiver_nickname(String receiver_nickname) {
		this.receiver_nickname = receiver_nickname;
	}

	@Override
	public String toString() {
		return "ChatVO [chat_num=" + chat_num + ", chat_sender_num=" + chat_sender_num + ", chat_receiver_num="
				+ chat_receiver_num + ", chat_message=" + chat_message + ", chat_sent_at=" + chat_sent_at
				+ ", chat_read=" + chat_read + ", sender_nickname=" + sender_nickname + ", receiver_nickname="
				+ receiver_nickname + ", id=" + id + ", cnt=" + cnt + "]";
	}

}
