package kr.reserv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.fplace.vo.FpTimeVO;
import kr.reserv.vo.ReservVO;
import kr.util.DBUtil;

public class ReservDAO {
	//싱글턴 패턴
	private static ReservDAO instance = new ReservDAO();
	
	public static ReservDAO getInstance() {
		return instance;
	}
	private ReservDAO() {}
	
	//예약가능시간 불러오기
	public List<FpTimeVO> getTimeList(long fp_num)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FpTimeVO> list = null;
		String sql = null;
		
		try {
			conn=DBUtil.getConnection();
			sql="select * from fplace_time where fp_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, fp_num);
			rs = pstmt.executeQuery();
			list = new ArrayList<FpTimeVO>();
			while(rs.next()) {
				FpTimeVO fptime = new FpTimeVO();
				fptime.setFt_num(rs.getLong("ft_num"));
				fptime.setFt_time(rs.getString("ft_time"));
				fptime.setFp_num(rs.getLong("fp_num"));
				
				list.add(fptime);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//예약하기
	public void reservation(ReservVO reserv)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt0 = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		long rs_num = 0;
		
		try {
			conn=DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "select reserv_seq.nextval From dual";
			pstmt0 = conn.prepareStatement(sql);
			rs = pstmt0.executeQuery();
			if(rs.next()) {
				rs_num = rs.getLong(1);
			}
			
			//예약 정보 추가
			sql="insert into reserv (rs_num, rs_cnt, rs_time, mem_num, fp_num) values (?,?,?,?,?)";
			pstmt1=conn.prepareStatement(sql);
			pstmt1.setLong(1, rs_num);
			pstmt1.setInt(2, reserv.getRs_cnt());
			pstmt1.setString(3, reserv.getRs_time());
			pstmt1.setLong(4, reserv.getMem_num());
			pstmt1.setLong(5, reserv.getFp_num());
			pstmt1.executeUpdate();
			
			//예약 상태 변경
			sql="update reserv set rs_status=? where rs_num=?";
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setInt(1, 1);
			pstmt2.setLong(2, rs_num);
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt1, null);
			DBUtil.executeClose(rs, pstmt0, conn);
		}
		
	}
	
	//예약 개수
	public int getReservCount(long mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn=DBUtil.getConnection();
			sql = "select count(*) from reserv where mem_num=? and (rs_status=1 or rs_status=2)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, mem_num);
			rs=pstmt.executeQuery();
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
	
	//사용자 예약 내역
	public List<ReservVO> getReservList(long mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReservVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "select r.fp_num, f.fp_name, r.rs_cnt, r.rs_time, r.rs_status, r.rs_num from reserv r join fplace f on r.fp_num = f.fp_num where r.mem_num=? and (r.rs_status=1 or r.rs_status=2)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, mem_num);
			rs = pstmt.executeQuery();
			list = new ArrayList<ReservVO>();
			while(rs.next()) {
				ReservVO reserv = new ReservVO();
				reserv.setFp_num(rs.getLong("fp_num"));
				reserv.setFp_name(rs.getString("fp_name"));
				reserv.setMem_num(mem_num);
				reserv.setRs_cnt(rs.getInt("rs_cnt"));
				reserv.setRs_num(rs.getLong("rs_num"));
				reserv.setRs_time(rs.getString("rs_time"));
				reserv.setRs_status(rs.getInt("rs_status"));
				
				list.add(reserv);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//식당관리자 예약 보기
	public List<ReservVO> AllReservList(long fp_num, int start, int end, String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReservVO> list = null;
		String sql = null;
		String sub_sql = "";
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1"))
					sub_sql += "and status=0";
				else if(keyfield.equals("2"))
					sub_sql += "and status=1";
				else if(keyfield.equals("3"))
					sub_sql += "and status=2";
			}
			
			sql = "select * from reserv where fp_num=?"+sub_sql;
			pstmt=conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ReservVO>();
			while(rs.next()) {
				ReservVO reserv = new ReservVO();
				reserv.setFp_num(rs.getLong("fp_num"));
				reserv.setMem_num(rs.getLong("mem_num"));
				reserv.setRs_cnt(rs.getInt("rs_cnt"));
				reserv.setRs_num(rs.getLong("rs_num"));
				reserv.setRs_time(rs.getString("rs_time"));
				reserv.setRs_status(rs.getInt("rs_status"));
				
				list.add(reserv);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//식당관리자 예약 상태 관리
	public void ManageReserv(long rs_num,int rs_status,long mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "update reserv set rs_status=? where rs_num=? and mem_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rs_status);
				pstmt.setLong(2, rs_num);
				pstmt.setLong(3, mem_num);
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	}
	

