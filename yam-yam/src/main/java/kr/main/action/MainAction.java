package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.Ctotal_BoardDAO;
import kr.board.vo.Ctotal_BoardVO;
import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.PagingUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * //게시판 HttpSession session = request.getSession(); Long user_num =
		 * (Long)session.getAttribute("user_num");
		 * 
		 * if (user_num == null) { // 로그인이 되지 않은 경우 return
		 * "redirect:/member/loginForm.do"; }
		 * 
		 * String pageNum = request.getParameter("page_num"); if (pageNum ==
		 * null)pageNum = "1";
		 * 
		 * String keyfield = request.getParameter("keyfield"); String keyword =
		 * request.getParameter("keyword");
		 * 
		 * Ctotal_BoardDAO dao = Ctotal_BoardDAO.getInstance(); int count =
		 * dao.getCtotalCount(keyfield, keyword, user_num);
		 * 
		 * PagingUtil page = new PagingUtil(keyfield, keyword,
		 * Integer.parseInt(pageNum),count,20,10,"list.do"); List<Ctotal_BoardVO> list =
		 * null; if (count > 0) { list = dao.getListctotalBoard(page.getStartRow(),
		 * page.getEndRow(), keyfield, keyword, user_num);
		 * 
		 * } request.setAttribute("count", count); request.setAttribute("list", list);
		 * request.setAttribute("page", page.getPage());
		 */
		
		
		//메뉴추천
		FplaceDAO dao = FplaceDAO.getInstance();
		int count = dao.getMainFplaceCount();
		
		List<FplaceVO> list = null;
		if(count > 0) {
			list = dao.getMainListFplace();
		}
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		
		return "main/main.jsp";
	}
}
