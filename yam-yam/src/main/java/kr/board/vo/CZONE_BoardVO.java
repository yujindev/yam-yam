package kr.board.vo;

import java.sql.Date;

public class CZONE_BoardVO {
	private long czone_num;
	private String czone_title;
	private String czone_article;
	private Date czone_date;
	private String czone_loc;
	private int czone_hit;
	private long mem_num;
	private String czone_filename;	//파일명

	private String mem_img;
	
	public String getMem_img() {
		return mem_img;
	}
	public void setMem_img(String mem_img) {
		this.mem_img = mem_img;
	}
	public String getCzone_filename() {
		return czone_filename;
	}
	public void setCzone_filename(String czone_filename) {
		this.czone_filename = czone_filename;
	}
	public long getCzone_num() {
		return czone_num;
	}
	public void setCzone_num(long czone_num) {
		this.czone_num = czone_num;
	}
	public String getCzone_title() {
		return czone_title;
	}
	public void setCzone_title(String czone_title) {
		this.czone_title = czone_title;
	}
	public String getCzone_article() {
		return czone_article;
	}
	public void setCzone_article(String czone_article) {
		this.czone_article = czone_article;
	}
	public Date getCzone_date() {
		return czone_date;
	}
	public void setCzone_date(Date czone_date) {
		this.czone_date = czone_date;
	}
	public String getCzone_loc() {
		return czone_loc;
	}
	public void setCzone_loc(String czone_loc) {
		this.czone_loc = czone_loc;
	}
	
	public int getCzone_hit() {
		return czone_hit;
	}
	public void setCzone_hit(int czone_hit) {
		this.czone_hit = czone_hit;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	
}
