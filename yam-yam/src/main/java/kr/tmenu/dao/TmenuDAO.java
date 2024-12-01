package kr.tmenu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fplace.vo.ReviewsVO;
import kr.tmenu.vo.TmenuVO;
import kr.util.DBUtil;

public class TmenuDAO {
	//싱글턴패턴
	private static TmenuDAO instance = new TmenuDAO();
	public static TmenuDAO getInstance() {
		return instance;
	}
	private TmenuDAO() {}
	
	//관리자 메뉴 항목 추가
	public void insertTmenu(TmenuVO tmenu) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "insert into tmenu(tm_num, tm_menu, mem_num) values (TMENU_SEQ.nextval,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tmenu.getTm_menu());
			pstmt.setLong(2, tmenu.getMem_num());
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자 메뉴 항목 개수 
	public int getTmenuCount(String keyfield, String keyword) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql ="";
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where tm_menu like '%' || ? || '%' ";
			}
			sql ="select count(*) from tmenu join "
				+"member using(mem_num)" + sub_sql ;
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count= rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//관리자 메뉴 항목 목록 
	public List<TmenuVO> getListTmenu(int start, int end, String keyfield,String keyword) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<TmenuVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where tm_menu  like '%' || ? || '%' ";
			}
			
			sql ="select * from (select a.*, rownum rnum from (select * from tmenu "
					+ "join member using(mem_num) " +sub_sql 
					+" order by tm_menu asc)a) where rnum >= ? and rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//sql문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<TmenuVO>();
			while(rs.next()) {
				TmenuVO tmenu = new TmenuVO();
				tmenu.setTm_menu(rs.getString("tm_menu"));
				tmenu.setTm_num(rs.getLong("tm_num"));
				tmenu.setMem_num(rs.getLong("mem_num"));
				list.add(tmenu);
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}
	return list;
}
	//메뉴 상세(메뉴 삭제시 작성자 회원번호 체크용도로 사용)
	public TmenuVO getTmenu(long tm_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TmenuVO tmenu = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM tmenu WHERE tm_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, tm_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				tmenu = new TmenuVO();
				tmenu.setTm_menu(rs.getString("tm_menu"));
				tmenu.setMem_num(rs.getLong("mem_num"));
				tmenu.setTm_num(rs.getLong("tm_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return tmenu;
	}
	
	//메뉴 항목 삭제 
	public void deleteTmenu(long tm_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null; 
		try {
			conn = DBUtil.getConnection();
			sql = "delete from tmenu where tm_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, tm_num);
			pstmt.executeUpdate();
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
		DBUtil.executeClose(null, pstmt, conn);
	
	}
}
	//
	// 랜덤 항목 선택
	public TmenuVO getRandomTmenu() throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    TmenuVO tmenu = null;
	    String sql = null;

	    try {
	        conn = DBUtil.getConnection();
	        sql = "SELECT * FROM (select * from tmenu ORDER BY DBMS_RANDOM.VALUE) where rownum = 1"; // 랜덤 선택
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            tmenu = new TmenuVO();
	            tmenu.setTm_menu(rs.getString("tm_menu"));
	            tmenu.setTm_num(rs.getLong("tm_num"));
	            tmenu.setMem_num(rs.getLong("mem_num"));
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return tmenu;
	}
	
	
	
	//메뉴 리스트 생성
	 public List<TmenuVO> getAllTmenus() throws Exception {
	        List<TmenuVO> menuList = new ArrayList<>();
	        String sql = "SELECT * FROM tmenu"; // tmenu 테이블에 맞게 수정

	        try (Connection conn = DBUtil.getConnection(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                TmenuVO menu = new TmenuVO();
	                menu.setTm_num(rs.getInt("tm_num"));
	                menu.setTm_menu(rs.getString("tm_menu"));
	                menu.setMem_num(rs.getLong("mem_num"));
	                menuList.add(menu);
	            }
	        }
	        return menuList;
	    }
	}
	
	

