package kr.board.vo;

public class Cmov_Re_BoardVO {
	private long cmov_re_num;
	private String cmov_re_content;
	private String cmov_re_date;
	private String cmov_re_mdate;
	private long mem_num;
	private long cmov_num;
	private String mem_nickname;

	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}

	public long getCmov_re_num() {
		return cmov_re_num;
	}
	public void setCmov_re_num(long cmov_re_num) {
		this.cmov_re_num = cmov_re_num;
	}
	public String getCmov_re_content() {
		return cmov_re_content;
	}
	public void setCmov_re_content(String cmov_re_content) {
		this.cmov_re_content = cmov_re_content;
	}
	public String getCmov_re_date() {
		return cmov_re_date;
	}
	public void setCmov_re_date(String cmov_re_date) {
		this.cmov_re_date = cmov_re_date;
	}
	public String getCmov_re_mdate() {
		return cmov_re_mdate;
	}
	public void setCmov_re_mdate(String cmov_re_mdate) {
		this.cmov_re_mdate = cmov_re_mdate;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public long getCmov_num() {
		return cmov_num;
	}
	public void setCmov_num(long cmov_num) {
		this.cmov_num = cmov_num;
	}

}
