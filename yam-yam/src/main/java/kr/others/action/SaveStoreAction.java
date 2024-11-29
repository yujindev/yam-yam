package kr.others.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.controller.Action;
import kr.fplace.vo.FplaceVO;
import kr.member.vo.MemberVO;
import kr.others.dao.OthersDAO;
import kr.util.PagingUtil;

public class SaveStoreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		// 특정회원 정보 가져오기
		String mem_id = request.getParameter("mem_id");

		// DAO호출
		ChatDAO dao = ChatDAO.getInstance();
		MemberVO member = dao.getMember(mem_id);

		request.setAttribute("member", member);

		// =========찜 목록 시작 ============//
//		member.getMem_num();
		long mem_num = member.getMem_num();
		OthersDAO odao = OthersDAO.getInstance();
		int count = odao.countMemberStoreBookmarks(mem_num);

		HttpSession session = request.getSession();

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)
			pageNum = "1";

		String rowCount = request.getParameter("rowCount");
		if (rowCount == null)
			rowCount = "10";

		// currentPage,count,rowCount
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		List<FplaceVO> list = null;
		if (count > 0) {
			list = odao.getMemberStoreBookmarks(page.getStartRow(), page.getEndRow(), mem_num);
		} else {
			list = Collections.emptyList();
		}

		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page",page.getPage());

		// JSP 경로 반환
		return "others/saveStore.jsp";
	}

}
