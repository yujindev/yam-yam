package kr.fplace.vo;

public class FplaceVO {
	private long fp_num;//식당 식별번호
	private String fp_name;	//식당 이름
	private String fp_phone;//식당 연락처
	private String fp_time;//식당의 영업시간
	private String fp_storeimg; //식당이미지
	private String fp_loc ;//위치
	private String fp_filter1;// 필터1
	private String fp_filter2;// 필터2
	private String fp_filter3;// 필터3
	private long mem_num;//회원번호
	
	public long getFp_num() {
		return fp_num;
	}
	public void setFp_num(long fp_num) {
		this.fp_num = fp_num;
	}
	public String getFp_name() {
		return fp_name;
	}
	public void setFp_name(String fp_name) {
		this.fp_name = fp_name;
	}
	public String getFp_phone() {
		return fp_phone;
	}
	public void setFp_phone(String fp_phone) {
		this.fp_phone = fp_phone;
	}
	public String getFp_time() {
		return fp_time;
	}
	public void setFp_time(String fp_time) {
		this.fp_time = fp_time;
	}
	public String getFp_storeimg() {
		return fp_storeimg;
	}
	public void setFp_storeimg(String fp_storeimg) {
		this.fp_storeimg = fp_storeimg;
	}
	public String getFp_loc() {
		return fp_loc;
	}
	public void setFp_loc(String fp_loc) {
		this.fp_loc = fp_loc;
	}
	public String getFp_filter1() {
		return fp_filter1;
	}
	public void setFp_filter1(String fp_filter1) {
		this.fp_filter1 = fp_filter1;
	}
	public String getFp_filter2() {
		return fp_filter2;
	}
	public void setFp_filter2(String fp_filter2) {
		this.fp_filter2 = fp_filter2;
	}
	public String getFp_filter3() {
		return fp_filter3;
	}
	public void setFp_filter3(String fp_filter3) {
		this.fp_filter3 = fp_filter3;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	
}
