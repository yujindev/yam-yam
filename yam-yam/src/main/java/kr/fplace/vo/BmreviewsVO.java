package kr.fplace.vo;

public class BmreviewsVO {
	private long mem_num; 
	private long reviews_num;
	
	public BmreviewsVO() {}
	
	public BmreviewsVO(long reviews_num, long mem_num) {
		this.reviews_num = reviews_num;
		this.mem_num = mem_num;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}
	public long getReviews_num() {
		return reviews_num;
	}
	public void setReviews_num(long reviews_num) {
		this.reviews_num = reviews_num;
	}
	
	
}

