package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.CBOB_BoardVO;
import kr.board.vo.Cbob_Re_BoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class CBOB_BoardDAO {
	private static CBOB_BoardDAO instance = new CBOB_BoardDAO();
	public static CBOB_BoardDAO getInstance() {
		return instance;
	}
	private CBOB_BoardDAO() {}

	//잡담글 등록
	public void insertCBOB_Board(CBOB_BoardVO cbob_board) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			 sql = "INSERT INTO comm_bob(cbob_num, cbob_title, cbob_article, cbob_menu, cbob_gender1, cbob_gender2, cbob_meet, mem_num) "
		                + "VALUES(comm_bob_seq.nextval,?, ?, ?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cbob_board.getCbob_title());
			pstmt.setString(2, cbob_board.getCbob_article());
			pstmt.setString(3, cbob_board.getCbob_menu());
			pstmt.setString(4, cbob_board.getCbob_gender1());
			pstmt.setString(5, cbob_board.getCbob_gender2());
			pstmt.setString(6, cbob_board.getCbob_meet());
			pstmt.setLong(7, cbob_board.getMem_num());
			
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 잡담 글 개수 / 검색 글 개수	
	public int getcbobBoardCount(String keyfield, String keyword) throws Exception{
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
				if(keyfield.equals("1")) sub_sql += "where cbob_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			//sql 문 작성
			sql = "SELECT COUNT(*) FROM COMM_BOB " + sub_sql;

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
	public List<CBOB_BoardVO> getListBoard(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CBOB_BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();	

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where cbob_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
	                + "(SELECT * FROM COMM_BOB JOIN member USING(mem_num)" + sub_sql
	                + " ORDER BY cbob_num DESC)a) WHERE rnum >= ? AND rnum <= ?";

			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<CBOB_BoardVO>();

			while (rs.next()) {
				CBOB_BoardVO cbob_board = new CBOB_BoardVO();
				cbob_board.setCbob_num(rs.getLong("cbob_num"));
				cbob_board.setCbob_title(StringUtil.useNoHtml(rs.getString("cbob_title")));
				cbob_board.setCbob_hit(rs.getInt("cbob_hit"));
				cbob_board.setCbob_date(rs.getDate("cbob_date"));
				list.add(cbob_board);
			}	
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 잡담 글 상세
	public CBOB_BoardVO getBoard(long Cbob_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CBOB_BoardVO cbob_board = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_BOB join member using(mem_num) left outer join "
					+ "member using(mem_num) where cbob_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, Cbob_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cbob_board = new CBOB_BoardVO();
				cbob_board.setCbob_num(rs.getLong("cbob_num"));
				cbob_board.setCbob_title(rs.getString("cbob_title"));
				cbob_board.setCbob_article(rs.getString("cbob_article"));
				cbob_board.setCbob_menu(rs.getString("cbob_menu"));
				cbob_board.setCbob_gender1(rs.getString("cbob_gender1"));
				cbob_board.setCbob_gender2(rs.getString("cbob_gender2"));
				cbob_board.setCbob_meet(rs.getString("cbob_meet"));
				cbob_board.setCbob_hit(rs.getInt("cbob_hit"));
				cbob_board.setCbob_date(rs.getDate("cbob_date"));
				cbob_board.setMem_num(rs.getInt("mem_num"));
				cbob_board.setMem_nickname(rs.getString("mem_nickname"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return cbob_board;
	}

	// 잡담 조회수 증가
	public void updateReadcount(long cbob_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "UPDATE COMM_BOB set CBOB_HIT=CBOB_HIT+1 where CBOB_NUM=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cbob_num);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 잡담 글 수정
	public void updatecbobBoard(CBOB_BoardVO cbob) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql = "UPDATE COMM_BOB SET cbob_title=?, cbob_article=?, "
				+ "cbob_gender1=?, cbob_gender2=?, cbob_menu=?, cbob_meet=? "
				+ "cbob_date=SYSDATE WHERE cbob_num=?" ;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cbob.getCbob_title());
			pstmt.setString(2, cbob.getCbob_article());
			pstmt.setString(3, cbob.getCbob_gender1());
			pstmt.setString(4, cbob.getCbob_gender2());
			pstmt.setString(5, cbob.getCbob_menu());
			pstmt.setString(6, cbob.getCbob_meet());
			pstmt.setLong(7, cbob.getCbob_num());
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 삭제
	public void deleteCbob(long cbob_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		String sql =null;
		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			conn.setAutoCommit(false);
            //댓글 삭제
            sql = "delete from COMM_BOB_RE where cbob_num=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, cbob_num);
            pstmt.executeUpdate();
			
			sql = "DELETE FROM COMM_BOB WHERE cbob_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, cbob_num);
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
	public void insertReplycbob(Cbob_Re_BoardVO cbobreplay) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO COMM_BOB_RE (cbob_re_num, cbob_re_content, cbob_num, mem_num) "
					+"VALUES(COMM_BOB_RE_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cbobreplay.getCbob_re_content());
			pstmt.setLong(2, cbobreplay.getCbob_num());
			pstmt.setLong(3, cbobreplay.getMem_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 개수
	public int getReplycbobCount(long cbob_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM COMM_BOB_RE WHERE cbob_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cbob_num);
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
	public List<Cbob_Re_BoardVO>getListReplyBoard(int start, int end, long cbob_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cbob_Re_BoardVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM COMM_BOB_RE JOIN member "
					+ "USING(mem_num) WHERE cbob_num = ? "
					+ "ORDER BY cbob_re_num DESC)a) WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cbob_num); 
			pstmt.setInt(2, start); 
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			list = new ArrayList<Cbob_Re_BoardVO>();
			while (rs.next()) {
				Cbob_Re_BoardVO reply = new Cbob_Re_BoardVO();
				reply.setCbob_re_num(rs.getLong("cbob_re_num"));
				reply.setCbob_re_date(DurationFromNow.getTimeDiffLabel(rs.getString("cbob_re_date")));
				if (rs.getString("cbob_re_mdate")!=null) {
					reply.setCbob_re_mdate(DurationFromNow.getTimeDiffLabel(rs.getString("cbob_re_mdate")));
				}
				reply.setCbob_re_content(StringUtil.useBrNoHtml(rs.getString("cbob_re_content")));
				reply.setCbob_num(rs.getLong("cbob_num"));
				reply.setMem_num(rs.getLong("mem_num"));
				reply.setMem_nickname(rs.getString("mem_nickname"));

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
	public Cbob_Re_BoardVO getReplyBoard(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Cbob_Re_BoardVO reply = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_BOB_RE where cbob_re_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new Cbob_Re_BoardVO();
				reply.setCbob_re_num(rs.getLong("cbob_re_num"));
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
	public void updateReplyBoard(Cbob_Re_BoardVO re)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "update COMM_bob_RE set cbob_re_content=?, cbob_re_mdate=SYSDATE WHERE cbob_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,re.getCbob_re_content());
			pstmt.setLong(2, re.getCbob_re_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 삭제
	public void deleteCbobRe(long cbob_re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM COMM_BOB_RE WHERE cbob_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cbob_re_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//+ 마이페이지

}
