package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CZONE_BoardVO;
import kr.board.vo.Cmenu_Re_BoardVO;
import kr.board.vo.Czone_Re_BoardVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class CZONE_BoardDAO {
	private static CZONE_BoardDAO instance = new CZONE_BoardDAO();
	public static CZONE_BoardDAO getInstance() {
		return instance;
	}
	private CZONE_BoardDAO() {}

	//잡담글 등록
	public void insertCZONE_Board(CZONE_BoardVO czone_board) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO comm_zone(czone_num, czone_title, czone_loc, czone_article, czone_filename, mem_num) "
					+ "VALUES(comm_zone_seq.nextval,?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, czone_board.getCzone_title());
			pstmt.setString(2, czone_board.getCzone_loc());
			pstmt.setString(3, czone_board.getCzone_article());
			pstmt.setString(4, czone_board.getCzone_filename());
			pstmt.setLong(5, czone_board.getMem_num());
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 잡담 글 개수 / 검색 글 개수	
	public int getczoneBoardCount(String keyfield, String keyword) throws Exception{
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
				if(keyfield.equals("1")) sub_sql += "where czone_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}
			//sql 문 작성
			sql = "select count(*) from COMM_zone " + sub_sql;

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
	public List<CZONE_BoardVO> getListBoard(int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CZONE_BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();	

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "where czone_article like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_id like '%' || ? || '%'";
			}

			sql = "select * from (select a.*, rownum rnum from "
					+ "(select * from COMM_zone join member using(mem_num) " + sub_sql
					+ " order by czone_num desc)a) where rnum >= ? and rnum <= ?";

			pstmt = conn.prepareStatement(sql);

			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<CZONE_BoardVO>();

			while (rs.next()) {
				CZONE_BoardVO czone_board = new CZONE_BoardVO();
				czone_board.setCzone_num(rs.getLong("czone_num"));
				czone_board.setCzone_title(StringUtil.useNoHtml(rs.getString("czone_title")));
				czone_board.setCzone_hit(rs.getInt("czone_hit"));
				czone_board.setCzone_date(rs.getDate("czone_date"));
				list.add(czone_board);
			}	
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 잡담 글 상세
	public CZONE_BoardVO getBoard(long Czone_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CZONE_BoardVO czone_board = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_zone join member using(mem_num) left outer join "
					+ "member_detail using(mem_num) where czone_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, Czone_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				czone_board = new CZONE_BoardVO();
				czone_board.setCzone_num(rs.getLong("czone_num"));
				czone_board.setCzone_title(rs.getString("czone_title"));
				czone_board.setCzone_loc(rs.getString("czone_loc"));
				czone_board.setCzone_article(rs.getString("czone_article"));
				czone_board.setCzone_hit(rs.getInt("czone_hit"));
				czone_board.setCzone_date(rs.getDate("czone_date"));
				czone_board.setMem_img(rs.getString("mem_img"));
				czone_board.setCzone_filename(rs.getString("czone_filename"));
				czone_board.setMem_num(rs.getInt("mem_num"));
				czone_board.setMem_nickname(rs.getString("mem_nickname"));
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return czone_board;
	}

	// 잡담 조회수 증가
	public void updateReadcount(long czone_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "UPDATE COMM_ZONE set Czone_HIT=Czone_HIT+1 where Czone_NUM=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, czone_num);
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 파일삭제
	public void deleteFile(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE comm_zone SET czone_filename='' WHERE zboard_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 수정
	public void updateczoneBoard(CZONE_BoardVO czone) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;


		try {
			conn=DBUtil.getConnection();
			if(czone.getCzone_filename()!=null 
					&& !"".equals(czone.getCzone_filename())) {
				sub_sql += ",czone_filename=?";
			}

			sql = "UPDATE COMM_ZONE SET czone_title=?, czone_loc=?, czone_article=?, "
				+ "czone_date=SYSDATE" + sub_sql  
				+ " WHERE czone_num=?" ;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, czone.getCzone_title());
			pstmt.setString(++cnt, czone.getCzone_loc());
			pstmt.setString(++cnt, czone.getCzone_article());

			if(czone.getCzone_filename()!=null 
					&& !"".equals(czone.getCzone_filename())) {
				pstmt.setString(++cnt, czone.getCzone_filename());
			}
			pstmt.setLong(++cnt, czone.getCzone_num());

			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 잡담 글 삭제
	public void deleteCzone(long czone_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		String sql =null;
		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			conn.setAutoCommit(false);
			//댓글 삭제
			sql = "delete from COMM_zone_RE where czone_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, czone_num);
			pstmt.executeUpdate();

			sql = "DELETE FROM COMM_zone WHERE czone_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, czone_num);
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
	public void insertReplyczone(Czone_Re_BoardVO czonereplay) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO COMM_zone_RE (czone_re_num, czone_re_content, czone_num, mem_num) "
					+"VALUES(COMM_zone_RE_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, czonereplay.getCzone_re_content());
			pstmt.setLong(2, czonereplay.getCzone_num());
			pstmt.setLong(3, czonereplay.getMem_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//댓글 개수
	public int getReplyczoneCount(long czone_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM COMM_zone_RE WHERE czone_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, czone_num);
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
	public List<Czone_Re_BoardVO>getListReplyBoard(int start, int end, long czone_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Czone_Re_BoardVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM COMM_zone_RE JOIN member "
					+ "USING(mem_num) WHERE czone_num = ? "
					+ "ORDER BY czone_re_num DESC)a) WHERE rnum >=? AND rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, czone_num); 
			pstmt.setInt(2, start); 
			pstmt.setInt(3, end);

			rs = pstmt.executeQuery();
			list = new ArrayList<Czone_Re_BoardVO>();
			while (rs.next()) {
				Czone_Re_BoardVO reply = new Czone_Re_BoardVO();
				reply.setCzone_re_num(rs.getLong("czone_re_num"));
				reply.setCzone_re_date(DurationFromNow.getTimeDiffLabel(rs.getString("czone_re_date")));
				if (rs.getString("czone_re_mdate")!=null) {
					reply.setCzone_re_mdate(DurationFromNow.getTimeDiffLabel(rs.getString("czone_re_mdate")));
				}
				reply.setCzone_re_content(StringUtil.useBrNoHtml(rs.getString("czone_re_content")));
				reply.setCzone_num(rs.getLong("czone_num"));
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
	public Czone_Re_BoardVO getReplyBoard(long re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Czone_Re_BoardVO reply = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql ="select * from COMM_zone_RE where czone_re_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, re_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new Czone_Re_BoardVO();
				reply.setCzone_re_num(rs.getLong("czone_re_num"));
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
	public void updateReplyBoard(Czone_Re_BoardVO re)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;

		try {
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "update COMM_zone_RE set czone_re_content=?, czone_re_mdate=SYSDATE WHERE czone_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,re.getCzone_re_content());
			pstmt.setLong(2, re.getCzone_re_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글 삭제
	public void deleteCzoneRe(long czone_re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM COMM_zone_RE WHERE czone_re_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, czone_re_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}


}
