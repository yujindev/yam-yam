package kr.dopamine.dao;

public class DopamineDAO {
	private static DopamineDAO instance = new DopamineDAO();
	
	public static DopamineDAO getInstance() {
		return instance;
	}
	private DopamineDAO() {}
	
	//이게 뭐지 메인(카테고리,카테고리별 글제목 소량모음)
	//카테고리별 전체글 개수(글제목 모음)
	//카테고리별 전체글 목록(글제목 모음)
	//상세 글 보기(카테고리는 상단에,제목,내용 순)
	//글 작성하기(카테고리 설정, 제목, 내용 작성 칸 있어야함)
	//글 수정
	//글 삭제
}
