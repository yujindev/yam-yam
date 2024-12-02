package kr.fplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import kr.fplace.vo.BmreviewsVO;
import kr.fplace.vo.BmstoreVO;
import kr.fplace.vo.FpMenuVO;
import kr.fplace.vo.FplaceVO;
import kr.fplace.vo.ReviewsVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class FplaceDAO {
	//싱글턴 패턴
	private static FplaceDAO instance = new FplaceDAO();
	public static FplaceDAO getInstance() {
		return instance;
	}
	private FplaceDAO() {}
	//식당 등록
	public void insertFplace(FplaceVO fplace) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			//sql문
			sql ="insert into fplace (fp_num, fp_name, fp_phone, fp_time, fp_loc, fp_storeimg, "
					+ "fp_filter1, fp_filter2,fp_filter3, mem_num )  "
					+ "values (fplace_seq.nextval, ?,?,?,?,?,?,?,?,?)";
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, fplace.getFp_name());
			pstmt.setString(2, fplace.getFp_phone());
			pstmt.setString(3, fplace.getFp_time());
			pstmt.setString(4, fplace.getFp_loc());
			pstmt.setString(5, fplace.getFp_storeimg());
			pstmt.setString(6, fplace.getFp_filter1());
			pstmt.setString(7, fplace.getFp_filter2());
			pstmt.setString(8, fplace.getFp_filter3());
			pstmt.setLong(9, fplace.getMem_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//전체 식당 개수
	public int getFplaceCount(String fp_filter1, String fp_filter2, String fp_filter3) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;

		try {
			conn = DBUtil.getConnection();

			if(!"".equals(fp_filter1) || !"".equals(fp_filter2) || !"".equals(fp_filter3)) {
				sub_sql += " where ";
			}
			
			if(!"".equals(fp_filter1)) {
				sub_sql += " INSTR(?,fp_filter1) > 0";
			}

			if(!"".equals(fp_filter1) && !"".equals(fp_filter2)) {
				sub_sql += " OR ";
			}
			
			if(!"".equals(fp_filter2)) {
				sub_sql += " INSTR(?,fp_filter2) > 0 ";
			}

			if(!"".equals(fp_filter2) && !"".equals(fp_filter3)) {
				sub_sql += " OR ";
			}
			
			if(!"".equals(fp_filter1) && "".equals(fp_filter2) && !"".equals(fp_filter3)) {
				sub_sql += " OR ";
			}
			
			if(!"".equals(fp_filter3)) {
				sub_sql += " case when length(fp_filter3) >= length(?) then instr(fp_filter3,?) "
						+ "else instr(?,fp_filter3)  end > 0";
			}

			//sql 문 작성
			sql = "select count(*) from fplace join "
					+ "member using(mem_num) " +sub_sql;
			System.out.println("SQL : " + sql);
			
			//pre어쩌구 생성
			pstmt = conn.prepareStatement(sql);
			if(!"".equals(fp_filter1)) {
				pstmt.setString(++cnt, fp_filter1);		
			}

			if(!"".equals(fp_filter2)) {
				pstmt.setString(++cnt, fp_filter2);
			}

			if(!"".equals(fp_filter3)) {
				pstmt.setString(++cnt, fp_filter3);
				pstmt.setString(++cnt, fp_filter3);
				pstmt.setString(++cnt, fp_filter3);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}

	//전체 식당 목록
	public List<FplaceVO> getListFplace(int startRow, int endRow, String fp_filter1, String fp_filter2, String fp_filter3, long mem_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<FplaceVO> list = null;
	    String sql = null;
	    String sub_sql = "";
	    int cnt = 0;

	    try {
	        conn = DBUtil.getConnection();

	        if (!"".equals(fp_filter1) || !"".equals(fp_filter2) || !"".equals(fp_filter3)) {
	            sub_sql += " WHERE ";
	        }

	        if (!"".equals(fp_filter1)) {
	            sub_sql += " INSTR(?,fp_filter1) > 0";
	        }

	        if (!"".equals(fp_filter1) && !"".equals(fp_filter2)) {
	            sub_sql += " OR ";
	        }

	        if (!"".equals(fp_filter2)) {
	            sub_sql += " INSTR(?,fp_filter2) > 0 ";
	        }

	        if (!"".equals(fp_filter2) && !"".equals(fp_filter3)) {
	            sub_sql += " OR ";
	        }

	        if (!"".equals(fp_filter1) && "".equals(fp_filter2) && !"".equals(fp_filter3)) {
	            sub_sql += " OR ";
	        }

	        if (!"".equals(fp_filter3)) {
	            sub_sql += " CASE WHEN LENGTH(fp_filter3) >= LENGTH(?) THEN INSTR(fp_filter3,?) "
	                    + "ELSE INSTR(?,fp_filter3) END > 0";
	        }
	        sql ="SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT f.*,ROUND(COALESCE(r.avg_score, 0), 1) AS fp_avgscore, COALESCE(r.review_count, 0) AS fp_review_count "
	        		+ "FROM (SELECT fp_num, AVG(reviews_score) AS avg_score, COUNT(reviews_score) AS review_count "
	        		+ "FROM reviews GROUP BY fp_num) r RIGHT OUTER JOIN (SELECT * FROM fplace "
	        		+ "LEFT OUTER JOIN (SELECT fp_num,mem_num isBookmarked FROM bmstore WHERE mem_num=?) "
	        		+ "USING(fp_num))f ON r.fp_num = f.fp_num "
	        		+ sub_sql + " ORDER BY fp_avgscore DESC, f.fp_num DESC)a) "
	        		+ " WHERE rnum>=? AND rnum<=?";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(++cnt, mem_num);
	        
	        if (!"".equals(fp_filter1)) {
	            pstmt.setString(++cnt, fp_filter1);
	        }

	        if (!"".equals(fp_filter2)) {
	            pstmt.setString(++cnt, fp_filter2);
	        }

	        if (!"".equals(fp_filter3)) {
	            pstmt.setString(++cnt, fp_filter3);
	            pstmt.setString(++cnt, fp_filter3);
	            pstmt.setString(++cnt, fp_filter3);
	        }

	        pstmt.setInt(++cnt, startRow);
	        pstmt.setInt(++cnt, endRow);

	        rs = pstmt.executeQuery();
	        list = new ArrayList<>();

	        while (rs.next()) {
	            FplaceVO fplace = new FplaceVO();
	            fplace.setFp_num(rs.getLong("fp_num")); //11.28 고침
	            fplace.setFp_name(rs.getString("fp_name"));
	            fplace.setFp_phone(rs.getString("fp_phone"));
	            fplace.setFp_time(rs.getString("fp_time"));
	            fplace.setFp_loc(rs.getString("fp_loc"));
	            fplace.setFp_storeimg(rs.getString("fp_storeimg"));
	            fplace.setFp_filter1(rs.getString("fp_filter1"));
	            fplace.setFp_filter2(rs.getString("fp_filter2"));
	            fplace.setFp_filter3(rs.getString("fp_filter3"));
	            fplace.setMem_num(rs.getLong("mem_num"));
	            fplace.setFp_avgscore(rs.getDouble("fp_avgscore")); // 평균 별점 설정
	            fplace.setReviews_count(rs.getInt("fp_review_count")); /*rs.getInt()안에 sql문의 알리아스 넣어주기*/
	            fplace.setBookmarked(rs.getInt("isBookmarked"));
	            list.add(fplace);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return list;
	}




	// 식당상세
	public FplaceVO getFplace(long fp_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    FplaceVO fplace = null;
	    String sql = null;

	    try {
	        conn = DBUtil.getConnection();
	        
	        // SQL문에 mem_num 추가
	        sql = "SELECT fp_num, fp_name, fp_phone, fp_time, fp_loc, fp_storeimg, " +
	              "fp_filter1, fp_filter2, fp_filter3, mem_num " +
	              "FROM fplace WHERE fp_num = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, fp_num);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            fplace = new FplaceVO();
	            fplace.setFp_num(rs.getInt("fp_num"));
	            fplace.setFp_name(rs.getString("fp_name"));
	            fplace.setFp_phone(rs.getString("fp_phone"));
	            fplace.setFp_time(rs.getString("fp_time"));
	            fplace.setFp_loc(rs.getString("fp_loc"));
	            fplace.setFp_storeimg(rs.getString("fp_storeimg"));
	            fplace.setFp_filter1(rs.getString("fp_filter1"));
	            fplace.setFp_filter2(rs.getString("fp_filter2"));
	            fplace.setFp_filter3(rs.getString("fp_filter3"));
	            fplace.setMem_num(rs.getLong("mem_num")); // 작성자 정보 추가
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return fplace;
	}

	//파일 삭제
	public void deleteFile(long fp_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn =DBUtil.getConnection();
			sql = "update fplace set fp_storeimg='' where fp_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, fp_num);
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//식당 정보 수정
	public void updateFplace(FplaceVO fplace) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			conn =DBUtil.getConnection();
			if(fplace.getFp_storeimg()!=null && !"".equals(fplace.getFp_storeimg())) {
				sub_sql += ",fp_storeimg=?";
			}
			sql = "update fplace set fp_name=?, fp_phone=?, fp_time=?, fp_loc=?, "
					+ "	fp_filter1=?, fp_filter2=?,fp_filter3=?" +sub_sql
					+" where fp_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, fplace.getFp_name());
			pstmt.setString(++cnt, fplace.getFp_phone());
			pstmt.setString(++cnt, fplace.getFp_time());
			pstmt.setString(++cnt, fplace.getFp_loc());
			pstmt.setString(++cnt, fplace.getFp_filter1());
			pstmt.setString(++cnt, fplace.getFp_filter2());
			pstmt.setString(++cnt, fplace.getFp_filter3());
			
			if(fplace.getFp_storeimg()!=null && !"".equals(fplace.getFp_storeimg())) {
				pstmt.setString(++cnt, fplace.getFp_storeimg());
			}
			pstmt.setLong(++cnt, fplace.getFp_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//식당 정보 삭제
	public void deleteFplace(long fp_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt6 = null;
		PreparedStatement pstmt7 = null;
		
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			//북마크 삭제
			sql = "DELETE FROM bmstore WHERE fp_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, fp_num);
			pstmt.executeUpdate();
		

			//댓글 삭제
			sql ="DELETE FROM reviews WHERE fp_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, fp_num);
			pstmt2.executeUpdate();
			

			//식당메뉴 삭제
			sql = "delete from fpmenu where fp_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, fp_num);
			pstmt3.executeUpdate();
			
			//예약시간 삭제
			sql = "delete from fplace_time where fp_num=?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setLong(1, fp_num);
			pstmt4.executeUpdate();
			
			//예약건 삭제 
			sql = "delete from reserv where fp_num=?";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setLong(1, fp_num);
			pstmt5.executeUpdate();

			
			//others 삭제
			sql = "delete from others where fp_num=?";
			pstmt6 = conn.prepareStatement(sql);
			pstmt6.setLong(1, fp_num);
			pstmt6.executeUpdate();
			
			//부모글 삭제
			sql = "delete from fplace where fp_num=?";
			pstmt7 = conn.prepareStatement(sql);
			pstmt7.setLong(1, fp_num);
			pstmt7.executeUpdate();
			conn.commit();

		}catch (Exception e) {
			//sql문이 하나라도 예외가 발생하면 롤백 처리
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt7, null);
			DBUtil.executeClose(null, pstmt6, null);
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//메뉴등록
	public void insertFpMenu(FpMenuVO fpMenu) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="insert into fpmenu (fpmenu_num, fpmenu_name, fpmenu_img, fpmenu_price, fp_num, mem_num) values(fpmenu_seq.nextval,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fpMenu.getFpmenu_name());
			pstmt.setString(2, fpMenu.getFpmenu_img());
			pstmt.setInt(3, fpMenu.getFpmenu_price());
			pstmt.setLong(4, fpMenu.getFp_num());
			pstmt.setLong(5, fpMenu.getMem_num());
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//메뉴개수
	public int getFpMenuCount(long fp_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count =0;
		try {
			conn = DBUtil.getConnection();
			sql ="select count(*) from fpmenu where fp_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, fp_num);
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
	
	//메뉴목록
	public List<FpMenuVO> getListFpMenu(int start, int end, long fp_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FpMenuVO> list = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select * from (select a.*, rownum rnum from (SELECT * FROM fpmenu JOIN member "
					+ "USING(mem_num) WHERE fp_num = ? ORDER BY  fpmenu_num desc)a) "
					+ "WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, fp_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<FpMenuVO>();
			while(rs.next()) {
				FpMenuVO fpmenu = new FpMenuVO();
				fpmenu.setFpmenu_num(rs.getLong("fpmenu_num"));
				fpmenu.setFpmenu_name(rs.getString("fpmenu_name"));
				fpmenu.setFpmenu_img(rs.getString("fpmenu_img"));
				fpmenu.setFpmenu_price(rs.getInt("fpmenu_price"));
				fpmenu.setFp_num(rs.getLong("fp_num"));
				fpmenu.setMem_num(rs.getLong("mem_num"));
				list.add(fpmenu);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//메뉴상세(댓글 수정, 삭제 시 어드민 체크용도로 사용)
	public FpMenuVO getFpMenuDetail(long fpmenu_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FpMenuVO fpmenu = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "select * from fpmenu where fpmenu_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, fpmenu_num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				fpmenu = new FpMenuVO();
				fpmenu.setFpmenu_num(rs.getLong("fpmenu_num"));
				fpmenu.setMem_num(rs.getLong("mem_num"));
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return fpmenu;
	}
	
	//메뉴수정
	public void updateFpMenu(FpMenuVO fpMenu) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			// SQL 작성
			sql = "update fpmenu set fpmenu_name = ?, fpmenu_img = ?, fpmenu_price = ? WHERE fpmenu_num = ?";
			pstmt = conn.prepareStatement(sql);

			// 매개변수 설정
			pstmt.setString(1, fpMenu.getFpmenu_name());
			pstmt.setString(2, fpMenu.getFpmenu_img());
			pstmt.setInt(3, fpMenu.getFpmenu_price());
			pstmt.setLong(4, fpMenu.getFpmenu_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//메뉴삭제
	public void deleteFpMenu(long fpmenu_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "delete from fpmenu where fpmenu_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, fpmenu_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}


	//북마크 개수
		//리뷰
		public int selectBmreviewsCount(long reviews_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM bmreviews WHERE reviews_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, reviews_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		//가게
		public int selectBmstoreCount(long fp_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM bmstore WHERE fp_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, fp_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		//회원번호와 식당 또는 리뷰 번호를 이용한 북마크 정보 (회원이 식당정보를 호출했을 때 북마크 선택 여부 표시)
		//리뷰
		public BmreviewsVO selectBmreviews(BmreviewsVO bmreviewsVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BmreviewsVO bm = null;
			String sql = null;

			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM bmreviews WHERE reviews_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, bmreviewsVO.getReviews_num());
				pstmt.setLong(2, bmreviewsVO.getMem_num());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					bm = new BmreviewsVO();
					bm.setReviews_num(rs.getLong("reviews_num"));
					bm.setMem_num(rs.getLong("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}		
			return bm;
		}
		//가게
		public BmstoreVO selectBmstore(BmstoreVO bmstoreVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BmstoreVO bm = null;
			String sql = null;

			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM bmstore WHERE fp_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, bmstoreVO.getFp_num());
				pstmt.setLong(2, bmstoreVO.getMem_num());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					bm = new BmstoreVO();
					bm.setFp_num(rs.getLong("fp_num"));
					bm.setMem_num(rs.getLong("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}		
			return bm;
		}
		//북마크 등록
		//리뷰
		public void insertBmreviews(BmreviewsVO bmreviewsVO)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO bmreviews (reviews_num,mem_num) VALUES (?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, bmreviewsVO.getReviews_num());
				pstmt.setLong(2, bmreviewsVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//가게
		public void insertBmstore(BmstoreVO bmstoreVO)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO bmstore (fp_num,mem_num) VALUES (?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, bmstoreVO.getFp_num());
				pstmt.setLong(2, bmstoreVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//북마크 삭제
		//리뷰
		public void deleteBmreviews(BmreviewsVO bmreviewsVO)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM bmreviews WHERE reviews_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, bmreviewsVO.getReviews_num());
				pstmt.setLong(2, bmreviewsVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();			
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//가게
		public void deleteBmstore(BmstoreVO bmstoreVO)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM bmstore WHERE fp_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, bmstoreVO.getFp_num());
				pstmt.setLong(2, bmstoreVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();			
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//내가 선택한 북마크 목록
		//리뷰
		public List<ReviewsVO> getListBmreviews(int start, int end, long mem_num) throws Exception {
		    Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    List<ReviewsVO> list = null;
		    String sql = null;

		    try {
		        // 커넥션 할당
		        conn = DBUtil.getConnection();

		        // SQL 작성
		        sql = "SELECT * FROM ( " +
		              "    SELECT a.*, rownum rnum " +
		              "    FROM ( " +
		              "        SELECT f.fp_name, r.reviews_score, r.reviews_con, r.reviews_date, r.reviews_img1 " +
		              "        FROM bmreviews b " +
		              "        JOIN reviews r ON b.reviews_num = r.reviews_num " +
		              "        JOIN fplace f ON r.fp_num = f.fp_num " +
		              "        WHERE b.mem_num = ? " +
		              "        ORDER BY r.reviews_date DESC " +
		              "    ) a " +
		              ") " +
		              "WHERE rnum >= ? AND rnum <= ?";

		        // PreparedStatement 객체 생성
		        pstmt = conn.prepareStatement(sql);

		        // ?에 데이터 바인딩
		        pstmt.setLong(1, mem_num);
		        pstmt.setInt(2, start);
		        pstmt.setInt(3, end);

		        // SQL 실행
		        rs = pstmt.executeQuery();
		        list = new ArrayList<>();

		        // 결과 처리
		        while (rs.next()) {
		            ReviewsVO review = new ReviewsVO();
		            review.setReviews_num(rs.getLong("reviews_num"));
		            review.setMem_num(rs.getLong("mem_num"));
		            review.setFp_name(rs.getString("fp_name"));           // 가게 이름
		            review.setReviews_score(rs.getInt("reviews_score"));  // 별점
		            review.setReviews_con(rs.getString("reviews_con"));   // 리뷰 내용
		            review.setReviews_date(rs.getDate("reviews_date"));   // 작성일
		            review.setReviews_img1(rs.getString("reviews_img1")); // 리뷰 이미지

		            list.add(review);
		        }
		    } catch (Exception e) {
		        throw new Exception(e);
		    } finally {
		        // 자원 해제
		        DBUtil.executeClose(rs, pstmt, conn);
		    }

		    return list;
		}
		
		//가게
		public List<FplaceVO> getListBmstore(int start, int end, long mem_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<FplaceVO> list = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				//(주의)zboard_fav의 회원번호(좋아요 클릭한 회원번호)로 검색되어야
				//하기 때문에 f.mem_num으로 표기함
				sql = "SELECT * FROM (SELECT a.*,rownum rnum "
						+ "FROM (SELECT * FROM fplace f JOIN member m "
						+ "USING(mem_num) JOIN bmreviews b USING(reviews_num) "
						+ "WHERE b.mem_num = ? ORDER BY reviews_num DESC)a) "
						+ "WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, mem_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<FplaceVO>();
				while(rs.next()) {
					FplaceVO fplace = new FplaceVO();
					  fplace.setFp_name(rs.getString("fp_name"));        // 식당 이름
			            fplace.setFp_loc(rs.getString("fp_loc"));          // 식당 위치
			            fplace.setFp_storeimg(rs.getString("fp_storeimg"));// 식당 이미지
			            fplace.setFp_time(rs.getString("fp_time"));        // 식당 영업시간
			            fplace.setFp_phone(rs.getString("fp_phone"));      // 식당 전화번호

			            list.add(fplace);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		
		//리뷰 등록
		public void insertReviews(ReviewsVO reviews) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				sql = "INSERT INTO reviews (reviews_num, reviews_score, reviews_con, reviews_img1, mem_num, fp_num) VALUES(reviews_num.nextval,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, reviews.getReviews_score());
				pstmt.setString(2, reviews.getReviews_con());
				pstmt.setString(3, reviews.getReviews_img1());
				//pstmt.setString(4, reviews.getReviews_img2());
				pstmt.setLong(4,reviews.getMem_num());
				pstmt.setLong(5,reviews.getFp_num());
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//리뷰 개수
			public int getReviewsCount(long fp_num) throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = null;
				int count = 0;
				try {
					conn = DBUtil.getConnection();
					sql = "SELECT COUNT(*) FROM reviews WHERE fp_num=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setLong(1, fp_num);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						count = rs.getInt(1);
					}
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
				return count;
			}
			
			//리뷰 목록!!
			public List<ReviewsVO> getListReviews(int start, int end, long fp_num, long mem_num) throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				List<ReviewsVO> list = null;
				String sql = null;
				try {
					conn = DBUtil.getConnection();
					 sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM reviews "
					 	+ "JOIN member USING(mem_num) "
					 	+ "LEFT OUTER JOIN (SELECT reviews_num,mem_num isBookmarked FROM bmreviews WHERE mem_num=?) USING(reviews_num) "
					 	+ "LEFT OUTER JOIN fplace USING(fp_num) " //fp_num, fp_name 가져오려구
					 	+ "LEFT OUTER JOIN (SELECT reviews_num, COUNT(*) reviews_count FROM bmreviews GROUP BY reviews_num) USING(reviews_num) "
					 	+ "WHERE fp_num=? ORDER BY reviews_num DESC)a) WHERE rnum>=? AND rnum<=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setLong(1, mem_num);
					pstmt.setLong(2, fp_num);
					pstmt.setInt(3, start);
					pstmt.setInt(4, end);
					
					rs = pstmt.executeQuery();
					list = new ArrayList<ReviewsVO>();
					while(rs.next()) {
						ReviewsVO reviews = new ReviewsVO();
						reviews.setReviews_num(rs.getLong("reviews_num"));
						reviews.setReviews_date(rs.getDate("reviews_date"));
						reviews.setReviews_con(StringUtil.useBrNoHtml(rs.getString("reviews_con")));
						reviews.setReviews_img1(rs.getString("reviews_img1"));
						reviews.setReviews_score(rs.getInt("reviews_score"));
						reviews.setMem_id(rs.getString("mem_id"));
						reviews.setMem_num(rs.getLong("mem_num"));
						reviews.setFp_num(rs.getLong("fp_num"));
						reviews.setFp_name(rs.getString("fp_name"));
						reviews.setMem_nickname(rs.getString("mem_nickname"));
						
						// 북마크 여부 설정
			            reviews.setIsBookmarked(rs.getInt("isBookmarked") != 0);
			            reviews.setReviews_count(rs.getInt("reviews_count"));
			            
						list.add(reviews);
					}
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
				return list;
			}
		
		//리뷰 상세(댓글 삭제시 작성자 회원번호 체크용도로 사용)
		public ReviewsVO getReviews(long reviews_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ReviewsVO reviews = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM reviews WHERE reviews_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, reviews_num);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					reviews = new ReviewsVO();
					reviews.setReviews_num(rs.getLong("reviews_num"));
					reviews.setMem_num(rs.getLong("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return reviews;
		}
		
		//리뷰 삭제
		public void deleteReviews(long reviews_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM reviews WHERE reviews_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, reviews_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//관리자 리뷰
		//리뷰 개수
			public int getReviewsCountByAdmin(String keyfield,String keyword) throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = null;
				String sub_sql = "";
				int count = 0;
				try {
					conn = DBUtil.getConnection();
					
					if(keyword != null && !"".equals(keyword)) {
						//검색 처리
						if(keyfield.equals("1")) sub_sql += "WHERE mem_nickname LIKE '%' || ? || '%'";
						else if(keyfield.equals("2")) sub_sql += "WHERE fp_name LIKE '%' || ? || '%'";
						else if(keyfield.equals("3")) sub_sql += "WHERE reviews_con LIKE '%' || ? || '%'";
					}
					
					sql = "SELECT COUNT(*) FROM reviews JOIN member USING(mem_num) "+ sub_sql;
					pstmt = conn.prepareStatement(sql);
					if(keyword !=null && !"".equals(keyword)) {
						pstmt.setString(1, keyword);
					}	
					rs = pstmt.executeQuery();
					if(rs.next()) {
						count = rs.getInt(1);
					}
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
				return count;
			}
			
			//리뷰 목록
			public List<ReviewsVO> getListReviewsByAdmin(int start, int end, String keyfield,String keyword) throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				List<ReviewsVO> list = null;
				String sql = null;
				String sub_sql = "";
				int cnt = 0;
				try {
					conn = DBUtil.getConnection();
					
					if(keyword != null && !"".equals(keyword)) {
						//검색 처리
						if(keyfield.equals("1")) sub_sql += "WHERE mem_nickname LIKE '%' || ? || '%'";
						else if(keyfield.equals("2")) sub_sql += "WHERE fp_name LIKE '%' || ? || '%'";
						else if(keyfield.equals("3")) sub_sql += "WHERE reviews_con LIKE '%' || ? || '%'";
					}
					
					 sql = "SELECT * FROM ( "
					            + "SELECT a.*, rownum rnum FROM ( "
					            + "    SELECT r.reviews_num, r.reviews_date, r.reviews_con, r.reviews_img1, "
					            + "           r.reviews_score, fp.fp_name, m.mem_nickname "
					            + "    FROM reviews r "
					            + "    JOIN member m ON r.mem_num = m.mem_num "
					            + "    JOIN fplace fp ON r.fp_num = fp.fp_num "
					            + sub_sql         // 동적 검색 조건 추가
					            + "    ORDER BY r.reviews_num DESC "
					            + ") a "
					            + ") WHERE rnum >= ? AND rnum <= ?";

					pstmt = conn.prepareStatement(sql);
					
					if(keyword != null && !"".equals(keyword)) {
						pstmt.setString(++cnt, keyword);
					}
					pstmt.setInt(++cnt, start);
					pstmt.setInt(++cnt, end);
					
					rs = pstmt.executeQuery();
					list = new ArrayList<ReviewsVO>();
					while(rs.next()) {
						ReviewsVO reviews = new ReviewsVO();
						reviews.setReviews_num(rs.getLong("reviews_num"));
						reviews.setReviews_date(rs.getDate("reviews_date"));
						reviews.setReviews_con(StringUtil.useBrNoHtml(rs.getString("reviews_con")));
						reviews.setReviews_img1(rs.getString("reviews_img1"));
						reviews.setReviews_score(rs.getInt("reviews_score"));
						//reviews.setMem_id(rs.getString("mem_id"));
						//reviews.setMem_num(rs.getLong("mem_num"));
						//reviews.setFp_num(rs.getLong("fp_num"));
						reviews.setFp_name(rs.getString("fp_name"));
						reviews.setMem_nickname(rs.getString("mem_nickname"));
			            
						list.add(reviews);
					}
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
				return list;
			}
		
			public List<FplaceVO> getMainListFplace() throws Exception {
			    Connection conn = null;
			    PreparedStatement pstmt = null;
			    ResultSet rs = null;
			    List<FplaceVO> list = null;
			    String sql = null;

			    try {
			        conn = DBUtil.getConnection();

			        sql ="SELECT * FROM (SELECT f.*, ROUND(COALESCE(r.avg_score, 0), 1) AS fp_avgscore "
			        		+ "FROM (SELECT fp_num,AVG(reviews_score) AS avg_score "
			        		+ "FROM reviews GROUP BY fp_num) r "
			        		+ "RIGHT OUTER JOIN fplace f ON r.fp_num = f.fp_num "
			        		+ "ORDER BY fp_avgscore DESC, f.fp_num DESC) t";

			        pstmt = conn.prepareStatement(sql);
			        rs = pstmt.executeQuery();
			        list = new ArrayList<>();

			        while (rs.next()) {
			            FplaceVO fplace = new FplaceVO();
			            fplace.setFp_num(rs.getLong("fp_num")); 
			            fplace.setFp_name(rs.getString("fp_name"));
			            fplace.setFp_storeimg(rs.getString("fp_storeimg"));
			            fplace.setFp_avgscore(rs.getDouble("fp_avgscore"));
			            list.add(fplace);
			        }
			        System.out.println("Retrieved list size: " + list.size()); // 로깅
			    } catch (Exception e) {
			        throw new Exception(e);
			    } finally {
			        DBUtil.executeClose(rs, pstmt, conn);
			    }

			    return list;
			}
			
			public int getMainFplaceCount() throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = null;
				int count = 0;

				try {
					conn = DBUtil.getConnection();

					//sql 문 작성
					sql = "select count(*) from fplace";
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						count = rs.getInt(1);
					}
				}catch (Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}

				return count;
			}
	
	/*---------------식당 리스트 관리-------------------*/
	//사용자(식당목록 맛집랭킹X)
	public int getFplaceCountByUser(String keyfield, String keyword) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    String sub_sql = "";
	    int count = 0;

	    try {
	        conn = DBUtil.getConnection();

	        if (keyword != null && !"".equals(keyword)) {
	            if (keyfield.equals("1")) sub_sql += "WHERE fp_name LIKE '%' || ? || '%'";
	            else if (keyfield.equals("2")) sub_sql += "WHERE fp_loc LIKE '%' || ? || '%'";
	        }
	        
	        sql = "select count(*) from fplace " + sub_sql ;

	        pstmt = conn.prepareStatement(sql);
	        if (keyword != null && !"".equals(keyword)) {
	            pstmt.setString(1, keyword);
	        }

	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return count;
	}
	//사용자
	//식당 목록,검색 목록
	public List<FplaceVO> getListFplaceByUser(int startRow, int endRow, String keyfield, String keyword) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<FplaceVO> list = null;
	    String sql = null;
	    String sub_sql = "";
	    int cnt = 0;

	    try {
	        conn = DBUtil.getConnection();

	        if (keyword != null && !"".equals(keyword)) {
	            if (keyfield.equals("1")) sub_sql += "WHERE f.fp_name LIKE '%' || ? || '%'";
	            else if (keyfield.equals("2")) sub_sql += "WHERE f.fp_loc LIKE '%' || ? || '%'";
	        }
	        

	        sql = "SELECT * FROM (" +
	              " SELECT a.*, rownum rnum " +
	              " FROM (" +
	              "   SELECT f.*, COALESCE(r.avg_score, 0) AS fp_avgscore, COALESCE(r.review_count, 0) AS fp_review_count " +
	              "   FROM (" +
	              "       SELECT fp_num, AVG(reviews_score) AS avg_score, COUNT(reviews_score) AS review_count " +
	              "       FROM reviews " +
	              "       GROUP BY fp_num" +
	              "   ) r " +
	              "   RIGHT OUTER JOIN fplace f ON r.fp_num = f.fp_num " + sub_sql +
	              "   ORDER BY f.fp_num DESC" +  // 등록 순으로 정렬 (최신 등록 순)
	              " ) a" +
	              ") WHERE rnum >= ? AND rnum <= ?";

	        pstmt = conn.prepareStatement(sql);

	        if (keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, keyword);
	        }
	        pstmt.setInt(++cnt, startRow);
	        pstmt.setInt(++cnt, endRow);

	        rs = pstmt.executeQuery();
	        list = new ArrayList<>();
	        while (rs.next()) {
	            FplaceVO fplace = new FplaceVO();
	            fplace.setFp_num(rs.getInt("fp_num"));
	            fplace.setFp_name(rs.getString("fp_name"));
	            fplace.setFp_phone(rs.getString("fp_phone"));
	            fplace.setFp_time(rs.getString("fp_time"));
	            fplace.setFp_loc(rs.getString("fp_loc"));
	            fplace.setFp_filter1(rs.getString("fp_filter1"));
	            fplace.setFp_filter2(rs.getString("fp_filter2"));
	            fplace.setFp_filter3(rs.getString("fp_filter3"));
	            fplace.setFp_storeimg(rs.getString("fp_storeimg"));
	            fplace.setFp_avgscore(rs.getDouble("fp_avgscore")); // 평균 평점
	            fplace.setReviews_count(rs.getInt("fp_review_count")); // 리뷰 수
	            list.add(fplace);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}


	//관리자
	//식당 전체내용 개수, 검색 내용 개수
	public int getFplaceCountByAdmin(String keyfield, String keyword)throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		try {
			//커넥션 풀로부터 커넥셔능ㄹ 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where fp_name like '%' || ? || '%'";
				 else if (keyfield.equals("2")) sub_sql += "WHERE mem_num LIKE '%' || ? || '%'";
			}
		
			//sql 문 작성
			sql = "select count(*) from fplace " + sub_sql ;
			//pre어쩌구 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count =rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}
	
	//식당 목록,검색 목록
	public List<FplaceVO> getListFplaceByAdmin(int start,int end, String keyfield,String keyword) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<FplaceVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where fp_name like '%' || ? || '%'";
				else if (keyfield.equals("2")) sub_sql += "WHERE mem_num LIKE '%' || ? || '%'";
			}
		
			//sql 문 작성
					sql = "SELECT * FROM (" +
			        	      "   SELECT a.*, rownum rnum " +
			        	      "   FROM (" +
			        	      "       SELECT f.*, COALESCE(r.avg_score, 0) AS fp_avgscore, COALESCE(r.review_count, 0) AS fp_review_count " +
			        	      "       FROM (" +
			        	      "           SELECT fp_num, AVG(reviews_score) AS avg_score, COUNT(reviews_score) AS review_count " +
			        	      "           FROM reviews " +
			        	      "           GROUP BY fp_num" +
			        	      "       ) r " +
			        	      "       RIGHT OUTER JOIN fplace f ON r.fp_num = f.fp_num " + sub_sql +
			        	      "       ORDER BY fp_avgscore DESC, f.fp_num DESC" + // 평균 별점 우선 정렬 후, 같은 별점일 경우 식당 번호로 정렬
			        	      "   ) a" +
			        	      ") WHERE rnum >= ? AND rnum <= ?";


			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//sql문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<FplaceVO>();
			
			while(rs.next()) {
				FplaceVO fplace = new FplaceVO();
				fplace.setFp_num(rs.getInt("fp_num"));
	            fplace.setFp_name(rs.getString("fp_name"));
	            fplace.setFp_filter1(rs.getString("fp_filter1"));
	            fplace.setFp_filter2(rs.getString("fp_filter2"));
	            fplace.setFp_filter3(rs.getString("fp_filter3"));
	            fplace.setMem_num(rs.getLong("mem_num"));
	            fplace.setFp_avgscore(rs.getDouble("fp_avgscore")); // 평균 별점 설정
	            fplace.setReviews_count(rs.getInt("fp_review_count")); /*rs.getInt()안에 sql문의 알리아스 넣어주기*/
	            list.add(fplace);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}
	return list;
}
	//식당관리자
	//식당관리자 식당 개수
	public int getFplaceCountByManager(Long userNum, String keyfield, String keyword) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    String sub_sql = "";
	    int count = 0;

	    try {
	        conn = DBUtil.getConnection();

	        if (keyword != null && !"".equals(keyword)) {
	            if (keyfield.equals("1")) sub_sql += "AND fp_name LIKE '%' || ? || '%'";
	        }

	        sql = "SELECT COUNT(*) FROM fplace WHERE mem_num = ? " + sub_sql;

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, userNum);
	        int cnt = 1;

	        if (keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, keyword);
	        }

	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return count;
	}
	//식당관리자 식당리스트
	public List<FplaceVO> getListFplaceByManager(Long userNum, int start, int end, String keyfield, String keyword) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<FplaceVO> list = null;
	    String sql = null;
	    String sub_sql = "";
	    int cnt = 0;

	    try {
	        conn = DBUtil.getConnection();

	        if (keyword != null && !"".equals(keyword)) {
	            if (keyfield.equals("1")) sub_sql += "AND fp_name LIKE '%' || ? || '%'";
	        }

	        sql = "SELECT * FROM (" +
	              "    SELECT a.*, rownum rnum " +
	              "    FROM (" +
	              "        SELECT f.*, COALESCE(r.avg_score, 0) AS fp_avgscore, COALESCE(r.review_count, 0) AS fp_review_count " +
	              "        FROM (" +
	              "            SELECT fp_num, AVG(reviews_score) AS avg_score, COUNT(reviews_score) AS review_count " +
	              "            FROM reviews " +
	              "            GROUP BY fp_num" +
	              "        ) r " +
	              "        RIGHT OUTER JOIN fplace f ON r.fp_num = f.fp_num " +
	              "        WHERE f.mem_num = ? " + sub_sql +
	              "        ORDER BY fp_avgscore DESC, f.fp_num DESC" +
	              "    ) a" +
	              ") WHERE rnum >= ? AND rnum <= ?";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(++cnt, userNum);

	        if (keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, keyword);
	        }
	        pstmt.setInt(++cnt, start);
	        pstmt.setInt(++cnt, end);

	        rs = pstmt.executeQuery();
	        list = new ArrayList<FplaceVO>();

	        while (rs.next()) {
	            FplaceVO fplace = new FplaceVO();
	            fplace.setFp_num(rs.getInt("fp_num"));
	            fplace.setFp_name(rs.getString("fp_name"));
	            fplace.setFp_filter1(rs.getString("fp_filter1"));
	            fplace.setFp_filter2(rs.getString("fp_filter2"));
	            fplace.setFp_filter3(rs.getString("fp_filter3"));
	            fplace.setFp_avgscore(rs.getDouble("fp_avgscore"));
	            fplace.setReviews_count(rs.getInt("fp_review_count"));
	            list.add(fplace);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}

	
	
	
}