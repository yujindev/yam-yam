package kr.cbob_board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("page_num");
		if (pageNum == null)pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		CBOB_BoardDAO dao = CBOB_BoardDAO.getInstance();
		int count = dao.getcbobBoardCount(keyfield, keyword);
		
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum),count,20,10,"list.do");
		List<CBOB_BoardVO> list = null;
		if (count > 0) {
			list = dao.getListBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword);
			
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "comm/mainboard/cbob/cbob_list.jsp";
		
	}

}
