package kr.fplace.vo;

import java.sql.Date;

public class ReviewsVO {
	private long reviews_num;
	private String reviews_con;
	private int reviews_score;
	private String reviews_img1;
	private Date reviews_date;
	
	private long mem_num;
	private long fp_num;
	
	private String mem_id;
	private String fp_name;       // 가게 이름
	
	private boolean isBookmarked; 
	private int reviews_count;
	
	private String mem_nickname;
	
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
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
	public Date getReviews_date() {
		return reviews_date;
	}
	public void setReviews_date(Date reviews_date) {
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
	public String getFp_name() {
		return fp_name;
	}
	public void setFp_name(String fp_name) {
		this.fp_name = fp_name;
	}
	
	public boolean isBookmarked() { //bookmarked를 따로 적어서 나중에 데이터에 bookmarked로 데이터 넘어감
        return isBookmarked;
    }
    public void setIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }
	public int getReviews_count() {
		return reviews_count;
	}
	public void setReviews_count(int reviews_count) {
		this.reviews_count = reviews_count;
	}
	public void setBookmarked(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
	}
    
}
