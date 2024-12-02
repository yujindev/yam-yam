package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.Cmenu_Re_BoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class CMENU_BoardDAO {
	private static CMENU_BoardDAO instance = new CMENU_BoardDAO();
	public static CMENU_BoardDAO getInstance() {
		return instance;
	}
	private CMENU_BoardDAO() {}

	//잡담글 등록
	public void insertCMENU_Board(CMENU_BoardVO cmenu_board) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			 sql = "INSERT INTO comm_menu(cmenu_num, cmenu_title, "
			 		+ "cmenu_article, cmenu_star, cmenu_filename, cmenu_filename2, "
			 		+ "cmenu_name, cmenu_loc, mem_num) "
		            + "VALUES(comm_menu_seq.nextval,?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmenu_board.getCmenu_title());
			pstmt.setString(2, cmenu_board.getCmenu_article());
			pstmt.setInt(3, cmenu_board.getCmenu_star());
			pstmt.setString(4, cmenu_board.getCmenu_filename());
			pstmt.setString(5, cmenu_board.getCmenu_filename2());
			pstmt.setString(6, cmenu_board.getCmenu_name());
			pstmt.setString(7, cmenu_board.getCmenu_loc());
			pstmt.setLong(8, cmenu_board.getMem_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 잡담 글 개수 / 검색 글 개수	
	public int getcmenuBoardCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =null;
		String sub_sql ="";
		int count = 0;

		try {
			conn = DBUtil.getConnection();			

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where cmenu_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			//sql 문 작성
			sql = "select count(*) from COMM_menu " + sub_sql;

			//pre어쩌구 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
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

	// 잡담 글 목록 / 검색 글 목록
	public List<CMENU_BoardVO> getListBoard(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CMENU_BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();	

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where cmenu_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			
			sql = "select * from (select a.*, rownum rnum from "
				      + "(select * from COMM_menu join member using(mem_num) " + sub_sql
				      + " order by cmenu_num desc)a) where rnum >= ? and rnum <= ?";

			pstmt = conn.prepareStatement(sql);

			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<CMENU_BoardVO>();

			while (rs.next()) {
				CMENU_BoardVO cmenu_board = new CMENU_BoardVO();
				cmenu_board.setCmenu_num(rs.getLong("cmenu_num"));
				cmenu_board.setCmenu_title(StringUtil.useNoHtml(rs.getString("cmenu_title")));
				cmenu_board.setCmenu_hit(rs.getInt("cmenu_hit"));
				cmenu_board.setCmenu_date(rs.getDate("cmenu_date"));
				list.add(cmenu_board);
			}	
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 잡담 글 상세
	public CMENU_BoardVO getBoard(long Cmenu_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CMENU_BoardVO cmenu_board = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_menu join member using(mem_num) left outer join "
					+ "member_detail using(mem_num) where cmenu_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, Cmenu_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cmenu_board = new CMENU_BoardVO();
				cmenu_board.setCmenu_num(rs.getLong("cmenu_num"));
				cmenu_board.setCmenu_title(rs.getString("cmenu_title"));
				cmenu_board.setCmenu_article(rs.getString("cmenu_article"));
				cmenu_board.setCmenu_date(rs.getDate("cmenu_date"));
				cmenu_board.setCmenu_star(rs.getInt("cmenu_star"));
				cmenu_board.setCmenu_filename(rs.getString("cmenu_filename"));
				cmenu_board.setCmenu_filename2(rs.getString("cmenu_filename2"));
				cmenu_board.setCmenu_name(rs.getString("cmenu_name"));
				cmenu_board.setCmenu_loc(rs.getString("cmenu_loc"));
				cmenu_board.setCmenu_hit(rs.getInt("cmenu_hit"));
				cmenu_board.setMem_num(rs.getInt("mem_num"));
				cmenu_board.setMem_nickname(rs.getString("mem_nickname"));

			}
			
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return cmenu_board;
	}

	// 잡담 조회수 증가
	public void updateReadcount(long cmenu_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "UPDATE COMM_MENU set CMENU_HIT=CMENU_HIT+1 where CMENU_NUM=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmenu_num);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 수정
	public void updatecmenuBoard(CMENU_BoardVO cmenu) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql = "UPDATE COMM_MENU SET cmenu_title=?, "
				+ "cmenu_article=?, cmenu_star=?, cmenu_filename=?, cmenu_filename2=?,  "
				+ "cmenu_name=?, cmenu_loc=?, cmenu_date=SYSDATE WHERE cmenu_num=?" ;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmenu.getCmenu_title());
			pstmt.setString(2, cmenu.getCmenu_article());
			pstmt.setInt(3, cmenu.getCmenu_star());
			pstmt.setString(4, cmenu.getCmenu_filename());
			pstmt.setString(5, cmenu.getCmenu_filename2());
			pstmt.setString(6, cmenu.getCmenu_name());
			pstmt.setString(7, cmenu.getCmenu_loc());
			pstmt.setLong(8, cmenu.getCmenu_num());
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 삭제
	public void deleteCmenu(long cmenu_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		String sql =null;
		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			conn.setAutoCommit(false);
            //댓글 삭제
            sql = "delete from COMM_MENU_RE where cmenu_num=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, cmenu_num);
            pstmt.executeUpdate();
			
			sql = "DELETE FROM COMM_MENU WHERE cmenu_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, cmenu_num);
			pstmt2.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {

			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 잡담 좋아요 개수
	// 회원번호와 게시물 번호를 이용한 좋아요 정보 (회원이 게시물을 호출했을 때 좋아요 선택 여부 표시)
	// 잡담 좋아요 등록
	// 잡담 좋아요 삭제
	//내가 선택한 좋아요 목록
	
	//댓글 등록
	public void insertReplycmenu(Cmenu_Re_BoardVO cmenureplay) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO COMM_MENU_RE (cmenu_re_num, cmenu_re_content, cmenu_num, mem_num) "
					+"VALUES(COMM_MENU_RE_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmenureplay.getCmenu_re_content());
			pstmt.setLong(2, cmenureplay.getCmenu_num());
			pstmt.setLong(3, cmenureplay.getMem_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 개수
	public int getReplycmenuCount(long cmenu_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM COMM_MENU_RE WHERE cmenu_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmenu_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//댓글 목록
	public List<Cmenu_Re_BoardVO>getListReplyBoard(int start, int end, long cmenu_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cmenu_Re_BoardVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM COMM_MENU_RE JOIN member "
					+ "USING(mem_num) WHERE cmenu_num = ? "
					+ "ORDER BY cmenu_re_num DESC)a) WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmenu_num); 
			pstmt.setInt(2, start); 
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			list = new ArrayList<Cmenu_Re_BoardVO>();
			while (rs.next()) {
				Cmenu_Re_BoardVO reply = new Cmenu_Re_BoardVO();
				reply.setCmenu_re_num(rs.getLong("cmenu_re_num"));
				reply.setCmenu_re_date(DurationFromNow.getTimeDiffLabel(rs.getString("cmenu_re_date")));
				if (rs.getString("cmenu_re_mdate")!=null) {
					reply.setCmenu_re_mdate(DurationFromNow.getTimeDiffLabel(rs.getString("cmenu_re_mdate")));
				}
				reply.setCmenu_re_content(StringUtil.useBrNoHtml(rs.getString("cmenu_re_content")));
				reply.setCmenu_num(rs.getLong("cmenu_num"));
				reply.setMem_num(rs.getLong("mem_num"));
				list.add(reply);
			}	
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	//댓글 상세
	public Cmenu_Re_BoardVO getReplyBoard(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Cmenu_Re_BoardVO reply = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_MENU_RE where cmenu_re_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new Cmenu_Re_BoardVO();
				reply.setCmenu_re_num(rs.getLong("cmenu_re_num"));
				reply.setMem_num(rs.getLong("mem_num"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return reply;
	}

	//댓글 수정
	public void updateReplyBoard(Cmenu_Re_BoardVO re)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "update COMM_menu_RE set cmenu_re_content=?, cmenu_re_mdate=SYSDATE WHERE cmenu_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,re.getCmenu_re_content());
			pstmt.setLong(2, re.getCmenu_re_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 삭제
	public void deleteCmenuRe(long cmenu_re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM COMM_menu_RE WHERE cmenu_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmenu_re_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//+ 마이페이지

}
