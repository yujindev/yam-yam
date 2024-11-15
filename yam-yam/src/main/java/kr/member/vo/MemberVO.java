package kr.member.vo;

import java.sql.Date;

public class MemberVO {
	private long mem_num; //회원번호
	private String mem_id; //회원 아이디
	private String mem_nickname; //회원 닉네임
	private String mem_pw; //비밀번호
	private String mem_phone; //전화번호
	private String mem_img; //프로필 이미지
	private Date mem_date; //가입일
	private Date mem_mdate; //개인정보 수정일
	private int mem_auth; //회원상태
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPw(String mem_pw) {
		//회원등급(auth):0탈퇴,1경고,2일반,9관리자
		if(mem_auth > 0 && this.mem_pw.equals(mem_pw)) {
			return true;
		}
		return false;
	}
	
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_img() {
		return mem_img;
	}
	public void setMem_img(String mem_img) {
		this.mem_img = mem_img;
	}
	public Date getMem_date() {
		return mem_date;
	}
	public void setMem_date(Date mem_date) {
		this.mem_date = mem_date;
	}
	public Date getMem_mdate() {
		return mem_mdate;
	}
	public void setMem_mdate(Date mem_mdate) {
		this.mem_mdate = mem_mdate;
	}
	public int getMem_auth() {
		return mem_auth;
	}
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
}
