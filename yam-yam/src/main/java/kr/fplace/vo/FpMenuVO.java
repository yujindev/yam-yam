package kr.fplace.vo;

public class FpMenuVO {
	private long fpmenu_num;//메뉴 식별번호 
	private String fpmenu_img;//메뉴 이미지
	private String fpmenu_name;//메뉴 이름
	private int fpmenu_price;//메뉴 가격
	private long fp_num;//식당 식별번호
	private long mem_num;//회원 식별번호
	
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public long getFpmenu_num() {
		return fpmenu_num;
	}
	public void setFpmenu_num(long fpmenu_num) {
		this.fpmenu_num = fpmenu_num;
	}
	public String getFpmenu_img() {
		return fpmenu_img;
	}
	public void setFpmenu_img(String fpmenu_img) {
		this.fpmenu_img = fpmenu_img;
	}
	public String getFpmenu_name() {
		return fpmenu_name;
	}
	public void setFpmenu_name(String fpmenu_name) {
		this.fpmenu_name = fpmenu_name;
	}
	public int getFpmenu_price() {
		return fpmenu_price;
	}
	public void setFpmenu_price(int fpmenu_price) {
		this.fpmenu_price = fpmenu_price;
	}
	public long getFp_num() {
		return fp_num;
	}
	public void setFp_num(long fp_num) {
		this.fp_num = fp_num;
	}
	
}
