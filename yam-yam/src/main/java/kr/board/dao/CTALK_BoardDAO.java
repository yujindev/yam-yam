package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.CTALK_BoardVO;
import kr.board.vo.Ctalk_Re_BoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class CTALK_BoardDAO {
	private static CTALK_BoardDAO instance = new CTALK_BoardDAO();
	public static CTALK_BoardDAO getInstance() {
		return instance;
	}
	private CTALK_BoardDAO() {}

	//잡담글 등록
	public void insertCTALK_Board(CTALK_BoardVO ctalk_board) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			 sql = "INSERT INTO comm_talk(ctalk_num, ctalk_title, ctalk_article, mem_num) "
		                + "VALUES(comm_talk_seq.nextval,?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctalk_board.getCtalk_title());
			pstmt.setString(2, ctalk_board.getCtalk_article());
			pstmt.setLong(3, ctalk_board.getMem_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 잡담 글 개수 / 검색 글 개수	
	public int getctalkBoardCount(String keyfield, String keyword) throws Exception{
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
				if(keyfield.equals("1")) sub_sql += "where ctalk_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			//sql 문 작성
			sql = "select count(*) from COMM_talk " + sub_sql;

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
	public List<CTALK_BoardVO> getListBoard(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CTALK_BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();	

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where ctalk_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			
			sql = "select * from (select a.*, rownum rnum from "
				      + "(select * from COMM_talk join member using(mem_num) " + sub_sql
				      + " order by ctalk_num desc)a) where rnum >= ? and rnum <= ?";

			pstmt = conn.prepareStatement(sql);

			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<CTALK_BoardVO>();

			while (rs.next()) {
				CTALK_BoardVO ctalk_board = new CTALK_BoardVO();
				ctalk_board.setCtalk_num(rs.getLong("ctalk_num"));
				ctalk_board.setCtalk_title(StringUtil.useNoHtml(rs.getString("ctalk_title")));
				ctalk_board.setCtalk_hit(rs.getInt("ctalk_hit"));
				ctalk_board.setCtalk_date(rs.getDate("ctalk_date"));
				list.add(ctalk_board);
			}	
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 잡담 글 상세
	public CTALK_BoardVO getBoard(long Ctalk_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CTALK_BoardVO ctalk_board = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_talk join member using(mem_num) left outer join "
					+ "member_detail using(mem_num) where ctalk_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, Ctalk_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ctalk_board = new CTALK_BoardVO();
				ctalk_board.setCtalk_num(rs.getLong("ctalk_num"));
				ctalk_board.setCtalk_title(rs.getString("ctalk_title"));
				ctalk_board.setCtalk_article(rs.getString("ctalk_article"));
				ctalk_board.setCtalk_hit(rs.getInt("ctalk_hit"));
				ctalk_board.setCtalk_date(rs.getDate("ctalk_date"));
				ctalk_board.setMem_num(rs.getInt("mem_num"));
				
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return ctalk_board;
	}

	// 잡담 조회수 증가
	public void updateReadcount(long ctalk_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "UPDATE COMM_TALK set CTALK_HIT=CTALK_HIT+1 where CTALK_NUM=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ctalk_num);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 수정
	public void updatectalkBoard(CTALK_BoardVO ctalk) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql = "UPDATE COMM_TALK SET ctalk_title=?, ctalk_article=?, ctalk_date=SYSDATE WHERE ctalk_num=?" ;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctalk.getCtalk_title());
			pstmt.setString(2, ctalk.getCtalk_article());
			pstmt.setLong(3, ctalk.getCtalk_num());
			
			pstmt.executeUpdate();   	

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 삭제
	public void deleteCtalk(long ctalk_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			conn.setAutoCommit(false);
            //댓글 삭제
            sql = "delete from COMM_TALK_RE where ctalk_num=?";
            pstmt2 = conn.prepareStatement(sql);
            pstmt2.setLong(1, ctalk_num);
            pstmt2.executeUpdate();
			
			sql = "DELETE FROM COMM_TALK WHERE ctalk_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, ctalk_num);
			pstmt3.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt3, null);
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
	public void insertReplyctalk(Ctalk_Re_BoardVO ctalkreplay) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO COMM_TALK_RE (ctalk_re_num, ctalk_re_content, ctalk_num, mem_num) "
					+"VALUES(COMM_TALK_RE_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctalkreplay.getCtalk_re_content());
			pstmt.setLong(2, ctalkreplay.getCtalk_num());
			pstmt.setLong(3, ctalkreplay.getMem_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 개수
	public int getReplyctalkCount(long ctalk_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM COMM_TALK_RE WHERE ctalk_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ctalk_num);
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
	public List<Ctalk_Re_BoardVO>getListReplyBoard(int start, int end, long ctalk_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Ctalk_Re_BoardVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM COMM_TALK_RE JOIN member "
					+ "USING(mem_num) WHERE ctalk_num = ? "
					+ "ORDER BY ctalk_re_num DESC)a) WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ctalk_num); 
			pstmt.setInt(2, start); 
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			list = new ArrayList<Ctalk_Re_BoardVO>();
			while (rs.next()) {
				Ctalk_Re_BoardVO reply = new Ctalk_Re_BoardVO();
				reply.setCtalk_re_num(rs.getLong("ctalk_re_num"));
				reply.setCtalk_re_date(DurationFromNow.getTimeDiffLabel(rs.getString("ctalk_re_date")));
				if (rs.getString("ctalk_re_mdate")!=null) {
					reply.setCtalk_re_mdate(DurationFromNow.getTimeDiffLabel(rs.getString("ctalk_re_mdate")));
				}
				reply.setCtalk_re_content(StringUtil.useBrNoHtml(rs.getString("ctalk_re_content")));
				reply.setCtalk_num(rs.getLong("ctalk_num"));
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
	public Ctalk_Re_BoardVO getReplyBoard(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Ctalk_Re_BoardVO reply = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_TALK_RE where ctalk_re_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new Ctalk_Re_BoardVO();
				reply.setCtalk_re_num(rs.getLong("ctalk_re_num"));
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
	public void updateReplyBoard(Ctalk_Re_BoardVO re)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "update COMM_TALK_RE set ctalk_re_content=?, ctalk_re_mdate=SYSDATE WHERE ctalk_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,re.getCtalk_re_content());
			pstmt.setLong(2, re.getCtalk_re_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 삭제
	public void deleteCtalkRe(long ctalk_re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM COMM_TALK_RE WHERE ctalk_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ctalk_re_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//+ 마이페이지

}
