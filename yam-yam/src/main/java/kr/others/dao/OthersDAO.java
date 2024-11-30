package kr.others.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fplace.vo.FplaceVO;
import kr.fplace.vo.ReviewsVO;
import kr.member.vo.MemberVO;
import kr.others.vo.OthersVO;
import kr.util.DBUtil;

public class OthersDAO {
	//싱글턴패턴
	private static OthersDAO instance = new OthersDAO();
	public static OthersDAO getInstance() {
		return instance;
	}
	
	private OthersDAO() {}

	// 멤버정보 읽기
	public MemberVO getMember(String mem_id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM member JOIN member_detail USING(mem_num) WHERE mem_id=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, mem_id);
			// sql문 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_nickname(rs.getString("mem_nickname"));
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return member;
	}


	// 특정회원이 북마크한 가게 목록
	public List<FplaceVO> getMemberStoreBookmarks(int start, int end, long mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FplaceVO> list = null;
		String sql = null;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			
			sql ="SELECT * FROM (SELECT a.*, rownum rnum "
			+ "FROM (SELECT * FROM bmstore b JOIN fplace USING(fp_num) WHERE b.mem_num=? "
			+ "ORDER BY reg_date DESC)a) WHERE rnum>=? AND rnum<=?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			// SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<FplaceVO>();
			while (rs.next()) {
				FplaceVO fplace = new FplaceVO();
				fplace.setFp_name(rs.getString("fp_name")); // 식당 이름
				fplace.setFp_loc(rs.getString("fp_loc")); // 식당 위치
				fplace.setFp_storeimg(rs.getString("fp_storeimg"));// 식당 이미지
				fplace.setFp_time(rs.getString("fp_time")); // 식당 영업시간
				fplace.setFp_phone(rs.getString("fp_phone")); // 식당 전화번호

				list.add(fplace);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//특정 회원이 북마크한 식당 수
	public int countMemberStoreBookmarks(long mem_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		try {
			//커넥션 할당
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "SELECT COUNT(*) FROM bmstore b JOIN fplace USING(fp_num) WHERE b.mem_num=?";
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			//sql문 실행 
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	
	// 특정회원이 북마크한 리뷰 목록
	public List<ReviewsVO> getMemberReviewBookmarks(int start, int end, long mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReviewsVO> list = null;
		String sql = null;
		try {
			// 커넥션 할당
			conn = DBUtil.getConnection();

			// SQL작성
			sql = "SELECT * FROM ("
					+ "SELECT a.*, rownum rnum "
					+ "FROM ("
					+ "SELECT r.reviews_num, r.mem_num AS reviews_memnum, r.reviews_date, r.reviews_score, r.reviews_con, r.reviews_img1, "
					+ "b.reg_date AS bmdate, b.mem_num AS bm_num,f.fp_num, f.fp_name ,m.mem_nickname "
					+ "FROM bmreviews b "
					+ "JOIN reviews r ON r.reviews_num = b.reviews_num "
					+ "JOIN fplace f ON r.fp_num = f.fp_num "
					+ "JOIN member m ON r.mem_num = m.mem_num "
					+ "WHERE b.mem_num = ? "
					+ "ORDER BY b.reg_date DESC) a)"
					+ "WHERE rnum BETWEEN ? AND ?";

			// PreparedStatement 객체 생성 
			pstmt = conn.prepareStatement(sql);

			// ?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			// SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<>();

			// 결과
			while (rs.next()) {
				ReviewsVO reviews = new ReviewsVO();
				reviews.setReviews_num(rs.getLong("reviews_num")); 
				reviews.setMem_num(rs.getLong("reviews_memnum")); //작성자
				reviews.setMem_nickname(rs.getString("mem_nickname")); //작성자 닉네임
				reviews.setFp_num(rs.getLong("fp_num"));//가게num
				reviews.setFp_name(rs.getString("fp_name"));// 가게이름
				reviews.setReviews_score(rs.getInt("reviews_score")); // 별점
				reviews.setReviews_con(rs.getString("reviews_con")); // 리뷰 내용
				reviews.setReviews_date(rs.getDate("reviews_date")); // 작성일
				reviews.setReviews_img1(rs.getString("reviews_img1")); // 리뷰 이미지

				list.add(reviews);

			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//특정 회원이 북마크한 리뷰 수
	public int countMemberReviewBookmarks(long mem_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		try {
			//커넥션 할당
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "SELECT COUNT(*) FROM bmreviews b JOIN reviews USING(reviews_num) WHERE b.mem_num=?";
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			//sql문 실행 
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	



}
