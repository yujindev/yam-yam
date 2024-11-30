package kr.board.vo;

import java.sql.Date;

public class CTALK_BoardVO {
	
	private long ctalk_num;
	private String ctalk_title;
	private String ctalk_article;
	private Date ctalk_date;
	private int ctalk_hit;

	private long mem_num;

	public long getCtalk_num() {
		return ctalk_num;
	}

	public void setCtalk_num(long ctalk_num) {
		this.ctalk_num = ctalk_num;
	}

	public String getCtalk_title() {
		return ctalk_title;
	}

	public void setCtalk_title(String ctalk_title) {
		this.ctalk_title = ctalk_title;
	}

	public String getCtalk_article() {
		return ctalk_article;
	}

	public void setCtalk_article(String ctalk_article) {
		this.ctalk_article = ctalk_article;
	}

	public Date getCtalk_date() {
		return ctalk_date;
	}

	public void setCtalk_date(Date ctalk_date) {
		this.ctalk_date = ctalk_date;
	}

	public int getCtalk_hit() {
		return ctalk_hit;
	}

	public void setCtalk_hit(int ctalk_hit) {
		this.ctalk_hit = ctalk_hit;
	}

	public long getMem_num() {
		return mem_num;
	}

	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}

}
