package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	/* ============ 회원 ============ */
	
	//회원 가입(ID, 닉네임 중복 체크)
	public void insertMember(MemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		long num = 0; //시퀀스 번호 저장
		
		try {
			conn=DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			
			//회원번호(mem_num) 생성
			sql = "select member_seq.nextval from dual";
			pstmt1 = conn.prepareStatement(sql);
			rs = pstmt1.executeQuery();
			if(rs.next()) {
				num = rs.getLong(1);
			}
			
			sql = "insert into member (mem_num,mem_id,mem_nickname) values (?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1,num); //시퀀스
			pstmt2.setString(2, member.getMem_id()); //아이디
			pstmt2.setString(3, member.getMem_nickname()); //닉네임
			pstmt2.executeUpdate();
			
			sql = "insert into member_detail (mem_num,mem_pw,mem_phone,mem_date) values (?,?,?,sysdate)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, num); //시퀀스
			pstmt3.setString(2, member.getMem_pw()); //비밀번호
			pstmt3.setString(3, member.getMem_phone()); //연락처
			pstmt3.executeUpdate();
			
			//SQL문 실행시 모두 성공하면 커밋
			conn.commit();
			
		}catch(Exception e) {
			//SQL문 하나라도 실패하면 롤백
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt1, conn);
		}
	}
	
	//ID 중복 체크 및 로그인 처리
	public MemberVO checkMember(String id, String nickname) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn=DBUtil.getConnection();
			
			if(id!=null && nickname==null) {//아이디 중복확인시
				sql="select * from member left outer join member_detail using(mem_num) where mem_id=?";
				
			}else if(nickname!=null && id==null) {//닉네임 중복확인시
				sql = "select * from member left outer join member_detail using(mem_num) where mem_nickname=?";
			}
			
			pstmt=conn.prepareStatement(sql);
			if(id!=null && nickname==null) {//아이디 중복확인시
				pstmt.setString(1, id);
			}else if(nickname!=null && id==null) {//닉네임 중복확인시
				pstmt.setString(1,nickname);
			}
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_nickname(rs.getString("mem_nickname"));
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_phone(rs.getString("mem_phone"));
				member.setMem_img(rs.getString("mem_img"));
				member.setMem_date(rs.getDate("mem_date"));
				member.setMem_auth(rs.getInt("mem_auth"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	
	//회원 상세 정보
	public MemberVO getMember(long mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			conn=DBUtil.getConnection();
			sql = "select * from member join member_detail using(mem_num) where mem_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, mem_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_nickname(rs.getString("mem_nickname")); //닉네임
				member.setMem_id(rs.getString("mem_id")); //아이디
				member.setMem_pw(rs.getString("mem_pw")); //비밀번호
				member.setMem_phone(rs.getString("mem_phone")); //연락처
				member.setMem_img(rs.getString("mem_img")); //프로필 이미지
				member.setMem_auth(rs.getInt("mem_auth")); //회원등급
				member.setMem_date(rs.getDate("mem_date")); //가입일
				if(rs.getDate("mem_mdate")!=null) { //수정일이 있으면
					member.setMem_mdate(rs.getDate("mem_mdate")); //수정일
				}
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	
	//회원 정보 수정
	//비밀번호 수정
	//회원 탈퇴(회원 정보 삭제)
	
	
	/* ============ 관리자 ============ */
	
	//전체 내용 개수, 검색 내용 개수
	public int getMemberCountByAdmin(String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn=DBUtil.getConnection();
			
			//검색 처리
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "where mem_id like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_nickname like '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "where mem_phone like '%' || ? || '%'";
			}
			
			sql = "select count(*) from member left outer join member_detail using(mem_num)"+sub_sql;
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count=rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	//전체 목록, 검색 목록
	public List<MemberVO> getListMemberByAdmin(int start,int end, String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "where mem_id like '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "where mem_nickname like '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "where mem_phone like '%' || ? || '%'";
			}
			sql = "select * from "
					+ "(select a.*,rownum rnum from "
					+ "(select * from member left outer join member_detail using(mem_num)"
					+ sub_sql + "order by mem_num desc nulls last)a)"
							+ " where rnum >= ? and rnum <= ?";
			pstmt=conn.prepareStatement(sql);
			
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<MemberVO>();
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_nickname(rs.getString("mem_nickname"));
				member.setMem_phone(rs.getString("mem_phone"));
				member.setMem_auth(rs.getInt("mem_auth"));
				member.setMem_date(rs.getDate("mem_date"));
				if(rs.getDate("mem_mdate")!=null) {
					member.setMem_mdate(rs.getDate("mem_mdate"));
				}
				list.add(member);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//회원 등급 수정
	public void updateMemberByAdmin(int mem_auth, long mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "update member set mem_auth=? where mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_auth);
			pstmt.setLong(2, mem_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
