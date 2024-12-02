package kr.board.vo;

public class Ctalk_Re_BoardVO {
	private long ctalk_re_num;
	private String ctalk_re_content;
	private String ctalk_re_date;
	private String ctalk_re_mdate;
	private long mem_num;
	private long ctalk_num;
	private String mem_nickname;
	
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	
	public long getCtalk_re_num() {
		return ctalk_re_num;
	}
	public void setCtalk_re_num(long ctalk_re_num) {
		this.ctalk_re_num = ctalk_re_num;
	}
	public String getCtalk_re_content() {
		return ctalk_re_content;
	}
	public void setCtalk_re_content(String ctalk_re_content) {
		this.ctalk_re_content = ctalk_re_content;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public long getCtalk_num() {
		return ctalk_num;
	}
	public void setCtalk_num(long ctalk_num) {
		this.ctalk_num = ctalk_num;
	}
	public String getCtalk_re_date() {
		return ctalk_re_date;
	}
	public void setCtalk_re_date(String ctalk_re_date) {
		this.ctalk_re_date = ctalk_re_date;
	}
	public String getCtalk_re_mdate() {
		return ctalk_re_mdate;
	}
	public void setCtalk_re_mdate(String ctalk_re_mdate) {
		this.ctalk_re_mdate = ctalk_re_mdate;
	}	
	
}
