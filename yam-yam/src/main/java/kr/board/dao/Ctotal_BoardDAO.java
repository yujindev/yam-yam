package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.Ctotal_BoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class Ctotal_BoardDAO {
	private static Ctotal_BoardDAO instance = new Ctotal_BoardDAO();
	public static Ctotal_BoardDAO getInstance() {
		return instance;
	}
	private Ctotal_BoardDAO() {}

	public int getCtotalCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;

		try {
			conn = DBUtil.getConnection();

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "AND board_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "AND board_date LIKE '%' || ? || '%'";
			}

			sql = "SELECT COUNT(*) FROM "
					+ "(SELECT ctalk_num board_num, ctalk_title board_title, ctalk_date board_date, ctalk_hit board_hit, mem_num, 'comm_talk' board_name FROM COMM_TALK "
					+ "UNION ALL "
					+ " SELECT cmenu_num board_num, cmenu_title board_title, cmenu_date board_date, cmenu_hit board_hit, mem_num, 'comm_menu' board_name FROM COMM_MENU "
					+ " UNION ALL "
					+ "SELECT cbob_num board_num, cbob_title board_title, cbob_date board_date, cbob_hit board_hit, mem_num, 'comm_bob' board_name FROM COMM_BOB "
					+ "UNION ALL "
					+ "SELECT cmov_num board_num, cmov_title board_title, cmov_date board_date, cmov_hit board_hit, mem_num, 'comm_mov' board_name FROM COMM_MOV "
					+ "UNION ALL "
					+ "SELECT czone_num board_num, czone_title board_title, czone_date board_date, czone_hit board_hit, mem_num, 'comm_zone' board_name FROM COMM_ZONE) "
					+ "JOIN member USING(mem_num)" + sub_sql;

			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}    
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}

	public List<Ctotal_BoardVO> getListctotalBoard(int start, int end, String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Ctotal_BoardVO> list = new ArrayList<>();
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			conn = DBUtil.getConnection();

			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "AND board_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "AND board_date LIKE '%' || ? || '%'";
			}

			// SQL 작성 (페이징 처리)
			sql = "SELECT * FROM( "
					+ " SELECT a.*,rownum rnum FROM( "
					+ "	SELECT * FROM "
					+ "	(SELECT ctalk_num board_num, ctalk_title board_title, ctalk_date board_date, ctalk_hit board_hit, mem_num, 'comm_talk' board_name FROM COMM_TALK "
					+ "	UNION ALL "
					+ "	SELECT cmenu_num board_num, cmenu_title board_title, cmenu_date board_date, cmenu_hit board_hit, mem_num, 'comm_menu' board_name FROM COMM_MENU "
					+ "	UNION ALL "
					+ "	SELECT cbob_num board_num, cbob_title board_title, cbob_date board_date, cbob_hit board_hit, mem_num, 'comm_bob' board_name FROM COMM_BOB "
					+ "	UNION ALL "
					+ "	SELECT cmov_num board_num, cmov_title board_title, cmov_date board_date, cmov_hit board_hit, mem_num, 'comm_mov' board_name FROM COMM_MOV "
					+ "	UNION ALL "
					+ "	SELECT czone_num board_num, czone_title board_title, czone_date board_date, czone_hit board_hit, mem_num, 'comm_zone' board_name FROM COMM_ZONE) "
					+ "	JOIN member USING(mem_num) " + sub_sql 
					+ " ORDER BY board_hit DESC, board_date DESC)a) "
					+ "	WHERE rnum >= ? AND rnum <= ? ";

			pstmt = conn.prepareStatement(sql);

			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start); 
			pstmt.setInt(++cnt, end);
			list = new ArrayList<Ctotal_BoardVO>();

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Ctotal_BoardVO board = new Ctotal_BoardVO();
				board.setBoard_num(rs.getLong("board_num"));
				board.setBoard_title(StringUtil.useNoHtml(rs.getString("board_title")));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_date(rs.getDate("board_date"));
				board.setMem_num(rs.getLong("mem_num"));
				board.setBoard_hit(rs.getInt("board_hit"));

				list.add(board);
			}
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
}
