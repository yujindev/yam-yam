package kr.board.vo;

import java.sql.Date;

public class CMENU_BoardVO {
	
	private long cmenu_num;
	private String cmenu_title;
	private String cmenu_article;
	private Date cmenu_date;
	private int cmenu_star;
	private String cmenu_filename;
	private String cmenu_filename2;
	private String cmenu_name;
	private String cmenu_loc;
	private int cmenu_hit;
	
	private long mem_num;
	private String mem_img2;
	private String mem_img3;
	private String mem_nickname;
		
	
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public String getCmenu_filename2() {
		return cmenu_filename2;
	}
	public void setCmenu_filename2(String cmenu_filename2) {
		this.cmenu_filename2 = cmenu_filename2;
	}
	public String getMem_img2() {
		return mem_img2;
	}
	public void setMem_img2(String mem_img2) {
		this.mem_img2 = mem_img2;
	}
	public String getMem_img3() {
		return mem_img3;
	}
	public void setMem_img3(String mem_img3) {
		this.mem_img3 = mem_img3;
	}
	public long getCmenu_num() {
		return cmenu_num;
	}
	public void setCmenu_num(long cmenu_num) {
		this.cmenu_num = cmenu_num;
	}
	public String getCmenu_title() {
		return cmenu_title;
	}
	public void setCmenu_title(String cmenu_title) {
		this.cmenu_title = cmenu_title;
	}
	public String getCmenu_article() {
		return cmenu_article;
	}
	public void setCmenu_article(String cmenu_article) {
		this.cmenu_article = cmenu_article;
	}
	public Date getCmenu_date() {
		return cmenu_date;
	}
	public void setCmenu_date(Date cmenu_date) {
		this.cmenu_date = cmenu_date;
	}
	public int getCmenu_star() {
		return cmenu_star;
	}
	public void setCmenu_star(int cmenu_star) {
		this.cmenu_star = cmenu_star;
	}
	
	public String getCmenu_filename() {
		return cmenu_filename;
	}
	public void setCmenu_filename(String cmenu_filename) {
		this.cmenu_filename = cmenu_filename;
	}
	public String getCmenu_name() {
		return cmenu_name;
	}
	public void setCmenu_name(String cmenu_name) {
		this.cmenu_name = cmenu_name;
	}
	public String getCmenu_loc() {
		return cmenu_loc;
	}
	public void setCmenu_loc(String cmenu_loc) {
		this.cmenu_loc = cmenu_loc;
	}
	public int getCmenu_hit() {
		return cmenu_hit;
	}
	public void setCmenu_hit(int cmenu_hit) {
		this.cmenu_hit = cmenu_hit;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	@Override
	public String toString() {
		return "CMENU_BoardVO [cmenu_num=" + cmenu_num + ", cmenu_title=" + cmenu_title + ", cmenu_article="
				+ cmenu_article + ", cmenu_date=" + cmenu_date + ", cmenu_star=" + cmenu_star + ", cmenu_filename="
				+ cmenu_filename + ", cmenu_filename2=" + cmenu_filename2 + ", cmenu_name=" + cmenu_name
				+ ", cmenu_loc=" + cmenu_loc + ", cmenu_hit=" + cmenu_hit + ", mem_num=" + mem_num + ", mem_img2="
				+ mem_img2 + ", mem_img3=" + mem_img3 + "]";
	}
	
	
	
	
}
