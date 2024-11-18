package kr.others.vo;

public class OthersVO {
	private long other_num; //내가 구독할 상대의 회원번호 
	private long fp_name; //fplace의 식당 식별번
	private long mem_num; //회원번호 
	private long save_num; //저장한 북마크 번호 
	private String save_name; //저장한 북마크 가게 이름 
	private long rest_num; // 저장한 리뷰 북마크번
	private String rest_name; // 저장한 리뷰 북마크번
	private int rest_hit;
	private String rest_con;
	private int rest_score;
	
	public OthersVO() {}
	public OthersVO(long other_num, long mem_num) {
		this.other_num = other_num;
		this.mem_num = mem_num;
	}
	
	public long getOther_num() {
		return other_num;
	}
	public void setOther_num(long other_num) {
		this.other_num = other_num;
	}
	public long getFp_name() {
		return fp_name;
	}
	public void setFp_name(long fp_name) {
		this.fp_name = fp_name;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public long getSave_num() {
		return save_num;
	}
	public void setSave_num(long save_num) {
		this.save_num = save_num;
	}
	public String getSave_name() {
		return save_name;
	}
	public void setSave_name(String save_name) {
		this.save_name = save_name;
	}
	public long getRest_num() {
		return rest_num;
	}
	public void setRest_num(long rest_num) {
		this.rest_num = rest_num;
	}
	public String getRest_name() {
		return rest_name;
	}
	public void setRest_name(String rest_name) {
		this.rest_name = rest_name;
	}
	public int getRest_hit() {
		return rest_hit;
	}
	public void setRest_hit(int rest_hit) {
		this.rest_hit = rest_hit;
	}
	public String getRest_con() {
		return rest_con;
	}
	public void setRest_con(String rest_con) {
		this.rest_con = rest_con;
	}
	public int getRest_score() {
		return rest_score;
	}
	public void setRest_score(int rest_score) {
		this.rest_score = rest_score;
	}
	
	
}
