package kr.dopamine.vo;

public class DopamineVO {
	private long dp_num; //글번호
	private int dp_category; //글 카테고리
	private String dp_title; //글 제목
	private String dp_content; //글 내용
	private int mem_auth; //회원번호
	
	public long getDp_num() {
		return dp_num;
	}
	public void setDp_num(long dp_num) {
		this.dp_num = dp_num;
	}
	public int getDp_category() {
		return dp_category;
	}
	public void setDp_category(int dp_category) {
		this.dp_category = dp_category;
	}
	public String getDp_title() {
		return dp_title;
	}
	public void setDp_title(String dp_title) {
		this.dp_title = dp_title;
	}
	public String getDp_content() {
		return dp_content;
	}
	public void setDp_content(String dp_content) {
		this.dp_content = dp_content;
	}
	public int getMem_auth() {
		return mem_auth;
	}
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
	
}
