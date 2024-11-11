package kr.fplace.dao;

public class FplaceDAO {
	//싱글턴 패턴
		private static FplaceDAO instance = new FplaceDAO();
		public static FplaceDAO getInstance() {
			return instance;
		}
		private FplaceDAO() {}
		//식당 등록
		//전체 식당 개수
		//전체 식당 목록
		//식당상세
		//식당 정보 수정
		//식당 정보 삭제
		//북마크 개수
		
		//별점-->순위를 매길수있음... 
		//회원번호와 식당 번호를 이용한 북마크 정보
		//(회원이 식당정보를 호출했을 때 북마크 선택 여부 표시)
		//북마크 등록
		//북마크 삭제
		//내가 선택한 북마크 목록
		//리뷰 등록
		//리뷰 개수
		//리뷰 목록
		//리뷰 상세(댓글수정, 삭제시 작성자 회원번호 체크용도로 사용)
		//리뷰 수정 
		//리뷰 삭제
}
