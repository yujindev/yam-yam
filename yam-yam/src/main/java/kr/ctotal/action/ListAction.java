package kr.ctotal.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.Ctotal_BoardDAO;
import kr.board.vo.Ctotal_BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("page_num");
		if (pageNum == null)pageNum = "1";

		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");

		Ctotal_BoardDAO dao = Ctotal_BoardDAO.getInstance();
		int count = dao.getCtotalCount(keyfield, keyword);

		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum),count,20,10,"list.do");
		List<Ctotal_BoardVO> list = null;
		if (count > 0) {
			list = dao.getListctotalBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword);

		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());

		return "comm/mainboard/main/main_list.jsp";
	}

}
