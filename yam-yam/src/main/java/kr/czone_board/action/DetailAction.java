package kr.czone_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.dao.CZONE_BoardDAO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.board.vo.CZONE_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;
  
public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long czone_num = Long.parseLong(request.getParameter("czone_num"));
		
		CZONE_BoardDAO dao = CZONE_BoardDAO.getInstance();
		dao.updateReadcount(czone_num);
		CZONE_BoardVO czone = dao.getBoard(czone_num);
		
		System.out.println(czone);
		
		czone.setCzone_title(StringUtil.useNoHtml(czone.getCzone_title()));
		czone.setCzone_article(StringUtil.useBrNoHtml(czone.getCzone_article()));
		czone.setCzone_loc(StringUtil.useBrNoHtml(czone.getCzone_loc()));
		request.setAttribute("czone", czone);
		
		return "comm/mainboard/czone/czone_detail.jsp";

	}

}
