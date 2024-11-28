package kr.reserv.vo;

import kr.fplace.vo.FplaceVO;

public class ReservVO {

	private Long rs_num; //예약번호
	private int rs_status; //예약상태
	private Long fp_num; //예약 식당번호
	private Long mem_num; //예약자 회원번호
	private String rs_time; //예약시간
	private int rs_cnt; //예약인원
	
	private FplaceVO fplaceVO;
	private String fp_name;
	
	public String getFp_name() {
		return fp_name;
	}
	public void setFp_name(String fp_name) {
		this.fp_name = fp_name;
	}
	public FplaceVO getFplaceVO() {
		return fplaceVO;
	}
	public void setFplaceVO(FplaceVO fplaceVO) {
		this.fplaceVO = fplaceVO;
	}
	
	public Long getRs_num() {
		return rs_num;
	}
	public void setRs_num(Long rs_num) {
		this.rs_num = rs_num;
	}
	public int getRs_status() {
		return rs_status;
	}
	public void setRs_status(int rs_status) {
		this.rs_status = rs_status;
	}
	public Long getFp_num() {
		return fp_num;
	}
	public void setFp_num(Long fp_num) {
		this.fp_num = fp_num;
	}
	public Long getMem_num() {
		return mem_num;
	}
	public void setMem_num(Long mem_num) {
		this.mem_num = mem_num;
	}
	public String getRs_time() {
		return rs_time;
	}
	public void setRs_time(String rs_time) {
		this.rs_time = rs_time;
	}
	public int getRs_cnt() {
		return rs_cnt;
	}
	public void setRs_cnt(int rs_cnt) {
		this.rs_cnt = rs_cnt;
	}
}
