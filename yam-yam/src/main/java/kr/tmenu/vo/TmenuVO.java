package kr.tmenu.vo;

public class TmenuVO {
	private long tm_num;
	private String tm_menu;
	private long mem_num;
	
	
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public long getTm_num() {
		return tm_num;
	}
	public void setTm_num(long tm_num) {
		this.tm_num = tm_num;
	}
	public String getTm_menu() {
		return tm_menu;
	}
	public void setTm_menu(String tm_menu) {
		this.tm_menu = tm_menu;
	}

}
