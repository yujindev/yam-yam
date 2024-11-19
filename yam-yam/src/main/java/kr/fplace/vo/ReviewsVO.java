package kr.fplace.vo;

public class ReviewsVO {
	private long reviews_num;
	private String reviews_con;
	private int reviews_score;
	private String reviews_img1;
	private String reviews_img2;
	private int reviews_hit;
	private String reviews_date;
	
	private long mem_num;
	private long fp_num;
	
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getReviews_num() {
		return reviews_num;
	}
	public void setReviews_num(long reviews_num) {
		this.reviews_num = reviews_num;
	}
	public String getReviews_con() {
		return reviews_con;
	}
	public void setReviews_con(String reviews_con) {
		this.reviews_con = reviews_con;
	}
	public int getReviews_score() {
		return reviews_score;
	}
	public void setReviews_score(int reviews_score) {
		this.reviews_score = reviews_score;
	}
	public String getReviews_img1() {
		return reviews_img1;
	}
	public void setReviews_img1(String reviews_img1) {
		this.reviews_img1 = reviews_img1;
	}
	public String getReviews_img2() {
		return reviews_img2;
	}
	public void setReviews_img2(String reviews_img2) {
		this.reviews_img2 = reviews_img2;
	}
	public int getReviews_hit() {
		return reviews_hit;
	}
	public void setReviews_hit(int reviews_hit) {
		this.reviews_hit = reviews_hit;
	}
	public String getReviews_date() {
		return reviews_date;
	}
	public void setReviews_date(String reviews_date) {
		this.reviews_date = reviews_date;
	}
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
}
