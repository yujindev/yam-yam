package kr.dopamine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import kr.dopamine.vo.DopamineVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class DopamineDAO {
	private static DopamineDAO instance = new DopamineDAO();
	
	public static DopamineDAO getInstance() {
		return instance;
	}
	private DopamineDAO() {}
	
	//이게 뭐지 메인(카테고리,카테고리별 글제목 소량모음)
	
	//전체 글 개수/검색 글 개수
	public int getDpCount(String keyfield,String keyword,String dp_category) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int c = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
		
			if(dp_category != null) {
				//검색 처리
				sub_sql += "where dp_category=? ";
				if(keyword != null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += "and ";
					else if(keyfield.equals("2")) sub_sql += "and ";
				}
			}else {
				if(keyword != null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += "where ";
					else if(keyfield.equals("2")) sub_sql += "where ";
				}
			}
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "dp_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "dp_content LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT COUNT(*) FROM dopamine " + sub_sql;
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			if(dp_category != null) {
				pstmt.setString(++c, dp_category);
			}
			
			//?에 데이터 바인딩
			if(keyword !=null && !"".equals(keyword)) {
				pstmt.setString(++c, keyword);
			}	
			
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
	
	//글 보기
	public List<DopamineVO> getListDp(int start, int end, String keyfield, String keyword, String dp_category) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DopamineVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(dp_category != null) {
				//검색 처리
				sub_sql += "where dp_category=? ";
				if(keyword != null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += "and ";
					else if(keyfield.equals("2")) sub_sql += "and ";
				}
			}else {
				if(keyword != null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += "where ";
					else if(keyfield.equals("2")) sub_sql += "where ";
				}
			}
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "dp_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "dp_content LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT dp_title,dp_num FROM (SELECT a.*, rownum AS rnum FROM (SELECT * FROM dopamine " + sub_sql + " ORDER BY dp_num DESC)a " +
				      ") WHERE rnum >= ? AND rnum <= ?";
			
			pstmt=conn.prepareStatement(sql);
			
			if(dp_category != null) {
				pstmt.setString(++cnt, dp_category);
			}
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			
			pstmt.setInt(++cnt,start);
			pstmt.setInt(++cnt,end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<DopamineVO>();
			while(rs.next()) {
				DopamineVO dopamine = new DopamineVO();
				dopamine.setDp_title(StringUtil.useNoHtml(rs.getString("dp_title")));
				dopamine.setDp_num(rs.getLong("dp_num"));
				list.add(dopamine);
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//상세 글 보기(카테고리는 상단에,제목,내용 순)
	public DopamineVO getDp(long dp_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DopamineVO dopamine = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "select * from dopamine where dp_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, dp_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dopamine = new DopamineVO();
				dopamine.setDp_category(rs.getInt("dp_category"));
				dopamine.setDp_num(rs.getInt("dp_num"));
				dopamine.setDp_title(rs.getString("dp_title"));
				dopamine.setDp_content(rs.getString("dp_content"));
				dopamine.setDp_file(rs.getString("dp_file"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return dopamine;
	}
	
	//글 작성하기(카테고리 설정, 제목, 내용 작성 칸 있어야함)
	public void writeDp(DopamineVO dopamine)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO dopamine (dp_num, dp_category, dp_title, dp_content, dp_file, mem_num) VALUES (dopamine_seq.nextval, ?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dopamine.getDp_category());
			pstmt.setString(2, dopamine.getDp_title());
			pstmt.setString(3, dopamine.getDp_content());
			pstmt.setString(4, dopamine.getDp_file());
			pstmt.setLong(5, dopamine.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글 수정
	public void modifyDp(DopamineVO dopamine) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null; 
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(dopamine.getDp_file()!=null 
					&& !"".equals(dopamine.getDp_file())) {
				sub_sql += ",dp_file=? ";
			}
			sql = "update dopamine set dp_title=?,dp_content=?"+ sub_sql +"where dp_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(++cnt, dopamine.getDp_title());
			pstmt.setString(++cnt, dopamine.getDp_content());
			if(dopamine.getDp_file()!=null && !"".equals(dopamine.getDp_file())) {
			pstmt.setString(++cnt, dopamine.getDp_file());
			}
			pstmt.setLong(++cnt, dopamine.getDp_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글 삭제
	public void deleteDp(long dp_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "delete from dopamine where dp_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, dp_num);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	public List<DopamineVO> getListDpMain(int start, int end, String keyfield, String keyword, String dp_category) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<DopamineVO> list = new ArrayList<>();
	    String sql = "SELECT dp_num, dp_title, dp_file FROM (" +
	                 "  SELECT a.*, rownum rnum FROM (" +
	                 "    SELECT * FROM dopamine WHERE dp_category = ? ORDER BY dp_num DESC" +
	                 "  ) a WHERE rownum <= ?" +
	                 ") WHERE rnum >= ?";

	    try {
	        conn = DBUtil.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dp_category);
	        pstmt.setInt(2, end);
	        pstmt.setInt(3, start);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            DopamineVO dopamine = new DopamineVO();
	            dopamine.setDp_num(rs.getLong("dp_num"));
	            dopamine.setDp_title(rs.getString("dp_title"));
	            dopamine.setDp_file(rs.getString("dp_file"));
	            list.add(dopamine);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return list;
	}


}
