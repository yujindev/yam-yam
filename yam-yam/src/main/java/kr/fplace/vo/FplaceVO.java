package kr.fplace.vo;

public class FplaceVO {
	private long fp_num;//식당 식별번호
	private String fp_name;	//식당 이름
	private String fp_phone;//식당 연락처
	private String fp_time;//식당의 영업시간
	private String fp_storeimg; //식당이미지
	private String fp_menuimg1;//대표메뉴사진1
	private String fp_menuimg2;//대표메뉴사진2
	private String fp_menuimg3;//대표메뉴사진3
	private String fp_menuimg4;//대표메뉴사진4
	private String fp_loc ;//위치
	private long mem_num;//회원번호
	
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
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
	public String getFp_menuimg1() {
		return fp_menuimg1;
	}
	public void setFp_menuimg1(String fp_menuimg1) {
		this.fp_menuimg1 = fp_menuimg1;
	}
	public String getFp_menuimg2() {
		return fp_menuimg2;
	}
	public void setFp_menuimg2(String fp_menuimg2) {
		this.fp_menuimg2 = fp_menuimg2;
	}
	public String getFp_menuimg3() {
		return fp_menuimg3;
	}
	public void setFp_menuimg3(String fp_menuimg3) {
		this.fp_menuimg3 = fp_menuimg3;
	}
	public String getFp_menuimg4() {
		return fp_menuimg4;
	}
	public void setFp_menuimg4(String fp_menuimg4) {
		this.fp_menuimg4 = fp_menuimg4;
	}
	public String getFp_loc() {
		return fp_loc;
	}
	public void setFp_loc(String fp_loc) {
		this.fp_loc = fp_loc;
	}
	
}
