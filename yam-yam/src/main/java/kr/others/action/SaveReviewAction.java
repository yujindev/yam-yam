package kr.others.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.controller.Action;
import kr.fplace.vo.ReviewsVO;
import kr.member.vo.MemberVO;
import kr.others.dao.OthersDAO;
import kr.util.PagingUtil;

public class SaveReviewAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 특정회원 정보 가져오기
		String mem_id = request.getParameter("mem_id");

		// DAO호출
		ChatDAO dao = ChatDAO.getInstance();
		OthersDAO odao = OthersDAO.getInstance();
		MemberVO member = dao.getMember(mem_id);
		

		request.setAttribute("member", member);

		// =========찜 목록 시작 ============//
		
		long mem_num = member.getMem_num();
		int count = odao.countMemberReviewBookmarks(mem_num);
		

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1";

		String rowCount = request.getParameter("rowCount");
		if (rowCount == null)
			rowCount = "10";

		// currentPage,count,rowCount
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		List<ReviewsVO> list = null;
		if (count > 0) {
			list = odao.getMemberReviewBookmarks(page.getStartRow(), page.getEndRow(), mem_num);
		} else {
			list = Collections.emptyList();
		}

		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page",page.getPage());

		// JSP 경로 반환
		return "others/saveReview.jsp";
	}

}
