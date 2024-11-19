package kr.fplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fplace.vo.BmreviewsVO;
import kr.fplace.vo.BmstoreVO;
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
		public int getFplaceCount(String[] filter1, String[] filter2, String[] filter3) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			
			try {
			     conn = DBUtil.getConnection();

	           /* if(keyword != null && !"".equals(keyword)) {
	                 //검색처리
	                 if(keyfield.equals("1")) sub_sql += "where title like '%' || ? || '%'";
	                 else if(keyfield.equals("2")) sub_sql += "where id like '%' || ? || '%'";
	                 else if(keyfield.equals("3")) sub_sql += "where content like '%' || ? || '%'";
	             }
	          	*/
	             //sql 문 작성
	             sql = "select count(*) from fplace join "
	                     + "member using(mem_num) "; //+sub_sql
	             //pre어쩌구 생성
	             pstmt = conn.prepareStatement(sql);
	             
	           /* if(keyword != null && !"".equals(keyword)) {
	                 pstmt.setString(1, keyword);
	             }
	            */
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
		public List<FplaceVO> getListFplace(int startRow, int endRow, String[] filter1, String[] filter2, String[] filter3) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<FplaceVO> list =null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
	            conn = DBUtil.getConnection();

	            /*if(keyword != null && !"".equals(keyword)) {
	                //검색처리
	                if(keyfield.equals("1")) sub_sql += "where title like '%' || ? || '%'";
	                else if(keyfield.equals("2")) sub_sql += "where id like '%' || ? || '%'";
	                else if(keyfield.equals("3")) sub_sql += "where content like '%' || ? || '%'";
	            }
	            */
	            sql = "select * from (select a.*, rownum rnum from "
	                    + "(select * from fplace join member using(mem_num) "//sub_sql
	                    + " order by fp_num desc)a) where rnum >= ? and rnum <= ?";
	            pstmt = conn.prepareStatement(sql);

	           /* if(keyword != null && !"".equals(keyword)) {
	                pstmt.setString(++cnt, keyword);
	            }
	            */
	            pstmt.setInt(++cnt, startRow); // 번호가 달라지기 때문에 1,2,3으로 지정하면 안됨
	            pstmt.setInt(++cnt, endRow); // 번호가 달라지기 때문에 1,2,3으로 지정하면 안됨
	            rs = pstmt.executeQuery();
	            list = new ArrayList<FplaceVO>();

	            while (rs.next()) {
	            	FplaceVO fplace = new FplaceVO();
	                fplace.setFp_num(rs.getInt("fp_num"));
	                fplace.setFp_name(rs.getString("fp_name"));
	                fplace.setFp_phone(rs.getString("fp_phone"));
	                fplace.setFp_time(rs.getString("fp_time"));
	                fplace.setFp_loc(rs.getString("fp_loc"));
	                fplace.setFp_storeimg(rs.getString("fp_storeimg"));
	                fplace.setFp_filter1(rs.getString("fp_filter1"));
	                fplace.setFp_filter2(rs.getString("fp_filter2"));
	                fplace.setFp_filter3(rs.getString("fp_filter3"));
	                fplace.setMem_num(rs.getLong("mem_num"));

	                list.add(fplace);
	            }
			}catch (Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		//식당상세
		public FplaceVO getFplace(long fp_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			FplaceVO fplace = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				sql = "select * from fplace where fp_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, fp_num);
				rs = pstmt.executeQuery();
				if(rs.next()) {
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
	                
				}
			}catch (Exception e) {
				throw new Exception(e);
			}finally {
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
			int cnt = 0;
			
			try {
				conn =DBUtil.getConnection();

				sql = "update fplace set fp_name=?, fp_phone=?, fp_time=?, fp_loc=?, "
						+ "	fp_filter1=?, fp_filter2=?,fp_filter3=?, fp_storeimg=? "
						+" where fp_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(++cnt, fplace.getFp_name());
				pstmt.setString(++cnt, fplace.getFp_phone());
				pstmt.setString(++cnt, fplace.getFp_time());
				pstmt.setString(++cnt, fplace.getFp_loc());
				pstmt.setString(++cnt, fplace.getFp_filter1());
				pstmt.setString(++cnt, fplace.getFp_filter2());
				pstmt.setString(++cnt, fplace.getFp_filter3());
				pstmt.setString(++cnt, fplace.getFp_storeimg());
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
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				
				/* 북마크 삭제
				sql = "delete from zboard_fav where board_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, board_num);
				pstmt.executeUpdate();
				
				//댓글 삭제
				sql = "delete from zboard_reply where board_num=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setLong(1, board_num);
				pstmt2.executeUpdate();
				*/
				
				//식당메뉴 삭제
				
				//부모글 삭제
				sql = "delete from fplace where fp_num=?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setLong(1, fp_num);
				pstmt3.executeUpdate();
				conn.commit();

			}catch (Exception e) {
				//sql문이 하나라도 예외가 발생하면 롤백 처리
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//별점-->순위를 매길수있음... 
				//회원번호와 식당 번호를 이용한 북마크 정보
				//(회원이 식당정보를 호출했을 때 북마크 선택 여부 표시)
				
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
				public List<FplaceVO> getListBmreviews(int start, int end, long mem_num)throws Exception{
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
							fplace.setFp_num(rs.getLong("board_num"));
							fplace.setFp_name(StringUtil.useNoHtml(rs.getString("fp_name")));
							//fplace.setReg_date(rs.getDate("reg_date"));
							//fplace.setId(rs.getString("id"));
							//list.add(board);
						}
					}catch(Exception e) {
						throw new Exception(e);
					}finally {
						DBUtil.executeClose(rs, pstmt, conn);
					}
					return list;
				}
				//가게
				
				//리뷰 등록
				public void insertReviews(ReviewsVO reviews) throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = null;
					try {
						conn = DBUtil.getConnection();
						sql = "INSERT INTO reviews (reviews_num, reviews_score, reviews_con, reviews_img1, mem_num, fp_num) VALUES(reviews_num.nextval,?,?,?,?,?,?)";
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
				//리뷰 목록
				public List<ReviewsVO> getListReviews(int start, int end, long fp_num) throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					List<ReviewsVO> list = null;
					String sql = null;
					try {
						conn = DBUtil.getConnection();
						sql = "SELECT * FROM(SELECT a.* , rownum rnum FROM (SELECT * FROM reviews JOIN member USING(mem_num) "
								+ "WHERE fp_num=? ORDER BY reviews_num DESC)a) WHERE rnum>=? AND rnum<=?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setLong(1, fp_num);
						pstmt.setInt(2, start);
						pstmt.setInt(3, end);
						
						rs = pstmt.executeQuery();
						list = new ArrayList<ReviewsVO>();
						while(rs.next()) {
							ReviewsVO reviews = new ReviewsVO();
							//목록에서 보일것만 써놓기
							reviews.setReviews_num(rs.getLong("review_num"));
							reviews.setReviews_date(rs.getString("reviews_date"));
							reviews.setReviews_con(StringUtil.useBrNoHtml(rs.getString("reviews_con")));
							reviews.setReviews_img1(rs.getString("reviews_img1"));
							//!!이미지 하나 더 넣을지...
							reviews.setReviews_score(rs.getInt("reviews_score"));
							reviews.setReviews_hit(rs.getInt("reviews_hit"));
							reviews.setId(rs.getString("id"));
							
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
				
				//조회수 증가
				public void updateReadcount(long reviews_num) throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = null;
					try {
						//커넥션풀로부터 커넥션을 할당
						conn = DBUtil.getConnection();
						//SQL문 작성
						sql = "UPDATE reviews SET reviews_hit=reviews_hit+1 WHERE reviews_num=?";
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


}
