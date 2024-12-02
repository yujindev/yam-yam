package kr.board.vo;

import java.sql.Date;

public class CBOB_BoardVO {
	private long cbob_num;
	private String cbob_title;
	private String cbob_article;
	private Date cbob_date;
	private String cbob_menu;
	private String cbob_gender1;
	private String cbob_gender2;
	private String cbob_meet;
	private int cbob_hit;	
	private long mem_num;
	private String mem_nickname;
	
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public long getCbob_num() {
		return cbob_num;
	}
	public void setCbob_num(long cbob_num) {
		this.cbob_num = cbob_num;
	}
	public String getCbob_title() {
		return cbob_title;
	}
	public void setCbob_title(String cbob_title) {
		this.cbob_title = cbob_title;
	}
	public String getCbob_article() {
		return cbob_article;
	}
	public void setCbob_article(String cbob_article) {
		this.cbob_article = cbob_article;
	}
	public Date getCbob_date() {
		return cbob_date;
	}
	public void setCbob_date(Date cbob_date) {
		this.cbob_date = cbob_date;
	}
	public String getCbob_menu() {
		return cbob_menu;
	}
	public void setCbob_menu(String cbob_menu) {
		this.cbob_menu = cbob_menu;
	}
	public String getCbob_gender1() {
		return cbob_gender1;
	}
	public void setCbob_gender1(String cbob_gender1) {
		this.cbob_gender1 = cbob_gender1;
	}
	public String getCbob_gender2() {
		return cbob_gender2;
	}
	public void setCbob_gender2(String cbob_gender2) {
		this.cbob_gender2 = cbob_gender2;
	}
	public String getCbob_meet() {
		return cbob_meet;
	}
	public void setCbob_meet(String cbob_meet) {
		this.cbob_meet = cbob_meet;
	}
	public int getCbob_hit() {
		return cbob_hit;
	}
	public void setCbob_hit(int cbob_hit) {
		this.cbob_hit = cbob_hit;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}

}
