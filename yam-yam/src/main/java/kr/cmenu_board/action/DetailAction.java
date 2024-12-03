package kr.cmenu_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;
  
public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long cmenu_num = Long.parseLong(request.getParameter("cmenu_num"));
		
		CMENU_BoardDAO dao = CMENU_BoardDAO.getInstance();
		dao.updateReadcount(cmenu_num);
		CMENU_BoardVO cmenu = dao.getBoard(cmenu_num);
		
		System.out.println(cmenu);
		
		cmenu.setCmenu_title(StringUtil.useNoHtml(cmenu.getCmenu_title()));
		cmenu.setCmenu_article(StringUtil.useBrNoHtml(cmenu.getCmenu_article()));

		request.setAttribute("cmenu", cmenu);
		
		return "comm/mainboard/cmenu/cmenu_detail.jsp";

	}

}
