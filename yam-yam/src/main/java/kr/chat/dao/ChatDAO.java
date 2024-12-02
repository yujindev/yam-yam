package kr.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import kr.chat.vo.ChatVO;
import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class ChatDAO {
	// 싱글턴패턴
	private static ChatDAO instance = new ChatDAO();

	public static ChatDAO getInstance() {
		return instance;
	}

	private ChatDAO() {
	}
	//멤버정보 읽기
	public MemberVO getMember(String mem_id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM member JOIN member_detail USING(mem_num) WHERE mem_id=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, mem_id);
			//sql문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_nickname(rs.getString("mem_nickname"));
			}
			
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	//멤버정보 읽기(mem_num)
		public MemberVO getMemberByMem_num(long mem_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			MemberVO member = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM member JOIN member_detail USING(mem_num) WHERE mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setLong(1, mem_num);
				//sql문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					member = new MemberVO();
					member.setMem_num(rs.getLong("mem_num"));
					member.setMem_id(rs.getString("mem_id"));
					member.setMem_nickname(rs.getString("mem_nickname"));
				}
				
			}catch(Exception e){
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return member;
		}
	//채팅메시지 등록
	public void insertChat(ChatVO chat) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션 풀로부터 커넥션 할당 
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO chat (chat_num, chat_sender_num, chat_receiver_num, chat_message) VALUES (chat_seq.nextval,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터바인딩
			pstmt.setLong(1, chat.getChat_sender_num());
			pstmt.setLong(2, chat.getChat_receiver_num());
			pstmt.setString(3, chat.getChat_message());
			
			pstmt.executeUpdate();
			
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//채팅 이력 가져오기 (목록)
	public List<ChatVO> getChatHistory(long chat_sender_num, long chat_receiver_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		List<ChatVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제 
			conn.setAutoCommit(false);
			
			//메시지 읽기여부 업데이트 
			sql = "UPDATE chat set chat_read=0 WHERE chat_sender_num=? AND chat_receiver_num=?";
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터바인딩
			pstmt.setLong(1, chat_receiver_num);
			pstmt.setLong(2, chat_sender_num);
			
			//SQL문 실행
			pstmt.executeUpdate();
			  
			//대화내역 가져오기 
			sql = "SELECT * FROM chat c JOIN member m "
					+ "ON c.chat_sender_num=m.mem_num WHERE "
					+ "(chat_sender_num=? AND chat_receiver_num=?) OR "
					+ "(chat_sender_num=? AND chat_receiver_num=?) "
					+ "ORDER BY chat_sent_at ASC";
			//pstmt 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setLong(1, chat_sender_num);
			pstmt2.setLong(2, chat_receiver_num);
			pstmt2.setLong(3, chat_receiver_num);
			pstmt2.setLong(4, chat_sender_num);
			//SQL문 실행 
			rs = pstmt2.executeQuery();
			list = new ArrayList<ChatVO>();
			while(rs.next()) {
				ChatVO chat = new ChatVO();
				chat.setChat_num(rs.getLong("chat_num"));
				chat.setChat_sender_num(rs.getLong("chat_sender_num"));
				chat.setChat_receiver_num(rs.getLong("chat_receiver_num"));
				chat.setChat_message(rs.getString("chat_message"));
				chat.setChat_read(rs.getInt("chat_read"));
				chat.setChat_sent_at(rs.getString("chat_sent_at"));
				chat.setSender_nickname(rs.getString("mem_nickname"));
				
				list.add(chat);
			}
			//SQL문이 모두 성공하면 커밋
			conn.commit();
			
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(rs, pstmt2, conn);
		}
		
		return list;
	}
	//전체 채팅 개수 
	public int getChatCount(long mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥션 할당
			conn = DBUtil.getConnection();
			
			sql="SELECT COUNT(*) FROM chat JOIN (SELECT SUM(chat_read) cnt,MAX(chat_num) chat_num FROM (SELECT chat_num,chat_read,"
					+ "CASE WHEN INSTR(chat_sender_num || ','||chat_receiver_num,?)>1 THEN chat_sender_num ELSE chat_receiver_num END room FROM chat WHERE chat_sender_num=? OR chat_receiver_num=?) "
					+ "GROUP BY room) USING(chat_num)";
			
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			pstmt.setLong(2, mem_num);
			pstmt.setLong(3, mem_num);
			
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
	
	//로그인한 유저가 참여하고 있는 전체 채팅 목록 
	public List<ChatVO> getChatList(int start, int end, long mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		List<ChatVO> list = null;
		String sql = null;
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum, CASE WHEN chat_sender_num=? THEN (SELECT mem_nickname FROM member WHERE mem_num=chat_receiver_num) "
					+ "ELSE (SELECT mem_nickname FROM member WHERE mem_num=chat_sender_num) END mem_nickname FROM (SELECT * FROM chat "
					+ "JOIN (SELECT SUM(chat_read) cnt,MAX(chat_num) chat_num "
					+ "FROM (SELECT chat_num,chat_read,"
					+ "CASE WHEN INSTR(chat_sender_num ||','||chat_receiver_num,?)>1 THEN chat_sender_num ELSE chat_receiver_num END room FROM chat WHERE chat_sender_num=? OR chat_receiver_num=?) "
					+ "GROUP BY room) USING(chat_num) order by chat_sent_at desc, cnt desc)a) "
					+ "WHERE rnum >= ? AND rnum <=?";
			
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(++cnt, mem_num);
			pstmt.setLong(++cnt, mem_num);
			pstmt.setLong(++cnt, mem_num);
			pstmt.setLong(++cnt, mem_num);
			
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<ChatVO>();
			while(rs.next()) {
				ChatVO chat = new ChatVO();
				chat.setChat_num(rs.getLong("chat_num"));
				chat.setChat_receiver_num(rs.getLong("chat_receiver_num"));
				chat.setChat_sender_num(rs.getLong("chat_sender_num"));
				chat.setChat_message(rs.getString("chat_message"));
				chat.setChat_sent_at(rs.getString("chat_sent_at"));
				chat.setReceiver_nickname(rs.getString("mem_nickname"));
				chat.setCnt(rs.getInt("cnt"));//읽지 않은 채팅갯수 
				list.add(chat);
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//로그인한 유저가 읽지않은 메시지의 수 
	public int getUnreadCount(long chat_receiver_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		try {
			//커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT SUM(chat_read) FROM chat WHERE chat_receiver_num=?";
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setLong(1, chat_receiver_num);
			//SQL문 작성
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
	//채팅 종료 (로그인한 유저가 특정회원과의 채팅한 내역을 삭제)
	public void deleteChat(long chat_receiver_num , long chat_sender_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//채팅방 삭제
			sql = "DELETE FROM chat WHERE (chat_receiver_num=? AND chat_sender_num=?) OR (chat_receiver_num=? AND chat_sender_num=?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, chat_receiver_num);
			pstmt.setLong(2, chat_sender_num);
			pstmt.setLong(3, chat_sender_num);
			pstmt.setLong(4, chat_receiver_num);
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			throw new Exception(e);
			
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
}
