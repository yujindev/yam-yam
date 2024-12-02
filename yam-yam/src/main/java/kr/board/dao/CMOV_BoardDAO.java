package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.CMOV_BoardVO;
import kr.board.vo.Cmov_Re_BoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class CMOV_BoardDAO {

	private static CMOV_BoardDAO instance = new CMOV_BoardDAO();
	public static CMOV_BoardDAO getInstance() {
		return instance;
	}
	private CMOV_BoardDAO() {}

	//잡담글 등록
	public void insertCMOV_Board(CMOV_BoardVO cmov_board) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			 sql = "INSERT INTO comm_mov(cmov_num, cmov_title, cmov_link, cmov_article, mem_num) "
		                + "VALUES(comm_mov_seq.nextval,?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmov_board.getCmov_title());
			pstmt.setString(2, cmov_board.getCmov_link());	
			pstmt.setString(3, cmov_board.getCmov_article());
			pstmt.setLong(4, cmov_board.getMem_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 잡담 글 개수 / 검색 글 개수	
	public int getcmovBoardCount(String keyfield, String keyword) throws Exception{
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
				if(keyfield.equals("1")) sub_sql += "where cmov_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			//sql 문 작성
			sql = "select count(*) from COMM_mov " + sub_sql;

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
	public List<CMOV_BoardVO> getListBoard(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CMOV_BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();	

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where cmov_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			
			sql = "select * from (select a.*, rownum rnum from "
				      + "(select * from COMM_mov join member using(mem_num) " + sub_sql
				      + " order by cmov_num desc)a) where rnum >= ? and rnum <= ?";

			pstmt = conn.prepareStatement(sql);

			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<CMOV_BoardVO>();

			while (rs.next()) {
				CMOV_BoardVO cmov_board = new CMOV_BoardVO();
				cmov_board.setCmov_num(rs.getLong("cmov_num"));
				cmov_board.setCmov_title(StringUtil.useNoHtml(rs.getString("cmov_title")));
				cmov_board.setCmov_hit(rs.getInt("cmov_hit"));
				cmov_board.setCmov_date(rs.getDate("cmov_date"));
				list.add(cmov_board);
			}	
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 잡담 글 상세
	public CMOV_BoardVO getBoard(long Cmov_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CMOV_BoardVO cmov_board = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_mov join member using(mem_num) left outer join "
					+ "member_detail using(mem_num) where cmov_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, Cmov_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cmov_board = new CMOV_BoardVO();
				cmov_board.setCmov_num(rs.getLong("cmov_num"));
				cmov_board.setCmov_title(rs.getString("cmov_title"));
				cmov_board.setCmov_article(rs.getString("cmov_article"));
				cmov_board.setCmov_hit(rs.getInt("cmov_hit"));
				cmov_board.setCmov_date(rs.getDate("cmov_date"));
				cmov_board.setCmov_link(rs.getString("cmov_link"));
				cmov_board.setMem_num(rs.getInt("mem_num"));
				cmov_board.setMem_nickname(rs.getString("mem_nickname"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return cmov_board;
	}

	// 잡담 조회수 증가
	public void updateReadcount(long cmov_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "UPDATE COMM_MOV set CMOV_HIT=CMOV_HIT+1 where CMOV_NUM=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmov_num);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 수정
	public void updatecmovBoard(CMOV_BoardVO cmov) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql = "UPDATE COMM_MOV SET cmov_title=?, cmov_link=?, cmov_article=?, cmov_date=SYSDATE WHERE cmov_num=?" ;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmov.getCmov_title());
			pstmt.setString(2, cmov.getCmov_article());
			pstmt.setString(3, cmov.getCmov_link());
			pstmt.setLong(4, cmov.getCmov_num());
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 삭제
	public void deleteCmov(long cmov_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		String sql =null;
		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			conn.setAutoCommit(false);
            //댓글 삭제
            sql = "delete from COMM_MOV_RE where cmov_num=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, cmov_num);
            pstmt.executeUpdate();
			
			sql = "DELETE FROM COMM_MOV WHERE cmov_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, cmov_num);
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
	public void insertReplycmov(Cmov_Re_BoardVO cmovreplay) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO COMM_MOV_RE (cmov_re_num, cmov_re_content, cmov_num, mem_num) "
					+"VALUES(COMM_MOV_RE_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cmovreplay.getCmov_re_content());
			pstmt.setLong(2, cmovreplay.getCmov_num());
			pstmt.setLong(3, cmovreplay.getMem_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 개수
	public int getReplycmovCount(long cmov_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM COMM_MOV_RE WHERE cmov_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmov_num);
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
	public List<Cmov_Re_BoardVO>getListReplyBoard(int start, int end, long cmov_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cmov_Re_BoardVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM COMM_MOV_RE JOIN member "
					+ "USING(mem_num) WHERE cmov_num = ? "
					+ "ORDER BY cmov_re_num DESC)a) WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmov_num); 
			pstmt.setInt(2, start); 
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			list = new ArrayList<Cmov_Re_BoardVO>();
			while (rs.next()) {
				Cmov_Re_BoardVO reply = new Cmov_Re_BoardVO();
				reply.setCmov_re_num(rs.getLong("cmov_re_num"));
				reply.setCmov_re_date(DurationFromNow.getTimeDiffLabel(rs.getString("cmov_re_date")));
				if (rs.getString("cmov_re_mdate")!=null) {
					reply.setCmov_re_mdate(DurationFromNow.getTimeDiffLabel(rs.getString("cmov_re_mdate")));
				}
				reply.setCmov_re_content(StringUtil.useBrNoHtml(rs.getString("cmov_re_content")));
				reply.setCmov_num(rs.getLong("cmov_num"));
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
	public Cmov_Re_BoardVO getReplyBoard(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Cmov_Re_BoardVO reply = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_MOV_RE where cmov_re_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new Cmov_Re_BoardVO();
				reply.setCmov_re_num(rs.getLong("cmov_re_num"));
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
	public void updateReplyBoard(Cmov_Re_BoardVO re)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "update COMM_MOV_RE set cmov_re_content=?, cmov_re_mdate=SYSDATE WHERE cmov_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,re.getCmov_re_content());
			pstmt.setLong(2, re.getCmov_re_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 삭제
	public void deleteCmovRe(long cmov_re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM COMM_MOV_RE WHERE cmov_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cmov_re_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
}

	//+ 마이페이지

}
