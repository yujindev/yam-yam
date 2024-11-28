package kr.others.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.others.vo.OthersVO;
import kr.util.DBUtil;

//이용자를 팔로우하는 기능을 만들고 타이용자의 정보조회를 하면 됨 (팔로우 기능...? 식당북마크, 식당리뷰 테이블 조인 추후 수정 )
  
public class OthersDAO {
	//팔로우
	public void insertFav(OthersVO favVO) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션 풀로부커 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성 
			sql = "SELECT * FROM reviews_store WHERE rest_num=? AND other_num=?";
			//PreparedStatement 객체 생성
			//?에 데이터 바인딩
			//SQL문 실행
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//찜삭제
	//특정회원이 좋아요한 목록 
}
