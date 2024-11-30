package kr.board.vo;

import java.sql.Date;

public class CMOV_BoardVO {
	
	private long cmov_num;
	private String cmov_title;
	private String cmov_article;
	private Date cmov_date;
	private String cmov_link;
	private int cmov_hit;
	private long mem_num;
	
	public long getCmov_num() {
		return cmov_num;
	}
	public void setCmov_num(long cmov_num) {
		this.cmov_num = cmov_num;
	}
	public String getCmov_title() {
		return cmov_title;
	}
	public void setCmov_title(String cmov_title) {
		this.cmov_title = cmov_title;
	}
	public String getCmov_article() {
		return cmov_article;
	}
	public void setCmov_article(String cmov_article) {
		this.cmov_article = cmov_article;
	}
	public Date getCmov_date() {
		return cmov_date;
	}
	public void setCmov_date(Date cmov_date) {
		this.cmov_date = cmov_date;
	}
	public String getCmov_link() {
		return cmov_link;
	}
	public void setCmov_link(String cmov_link) {
		this.cmov_link = cmov_link;
	}
	public int getCmov_hit() {
		return cmov_hit;
	}
	public void setCmov_hit(int cmov_hit) {
		this.cmov_hit = cmov_hit;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	

}
