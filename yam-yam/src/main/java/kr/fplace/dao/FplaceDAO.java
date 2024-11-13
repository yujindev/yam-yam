package kr.fplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.fplace.vo.FplaceVO;
import kr.util.DBUtil;

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
						+ "fp_menuimg1, fp_menuimg2, fp_menuimg3, fp_menuimg4, fp_filter1, fp_filter2,fp_filter3, mem_num )  "
						+ "values (fplace_seq.nextval, ?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt= conn.prepareStatement(sql);
				pstmt.setString(1, fplace.getFp_name());
				pstmt.setString(2, fplace.getFp_phone());
				pstmt.setString(3, fplace.getFp_time());
				pstmt.setString(4, fplace.getFp_loc());
				pstmt.setString(5, fplace.getFp_storeimg());
				pstmt.setString(6, fplace.getFp_menuimg1());
				pstmt.setString(7, fplace.getFp_menuimg2());
				pstmt.setString(8, fplace.getFp_menuimg3());
				pstmt.setString(9, fplace.getFp_menuimg4());
				pstmt.setString(10, fplace.getFp_filter1());
				pstmt.setString(11, fplace.getFp_filter2());
				pstmt.setString(12, fplace.getFp_filter3());
				pstmt.setLong(13, fplace.getMem_num());
				pstmt.executeUpdate();
			}catch (Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//전체 식당 개수
		
		//전체 식당 목록
		//식당상세
		//식당 정보 수정
		//식당 정보 삭제
		//북마크 개수
		
		//별점-->순위를 매길수있음... 
		//회원번호와 식당 번호를 이용한 북마크 정보
		//(회원이 식당정보를 호출했을 때 북마크 선택 여부 표시)
		//북마크 등록
		//북마크 삭제
		//내가 선택한 북마크 목록
		//리뷰 등록
		//리뷰 개수
		//리뷰 목록
		//리뷰 상세(댓글수정, 삭제시 작성자 회원번호 체크용도로 사용)
		//리뷰 수정 
		//리뷰 삭제

}
