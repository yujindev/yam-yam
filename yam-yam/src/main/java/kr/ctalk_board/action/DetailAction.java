package kr.ctalk_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;
  
public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long ctalk_num = Long.parseLong(request.getParameter("ctalk_num"));
		
		CTALK_BoardDAO dao = CTALK_BoardDAO.getInstance();
		dao.updateReadcount(ctalk_num);
		CTALK_BoardVO ctalk = dao.getBoard(ctalk_num);
		
		System.out.println(ctalk);
		
		ctalk.setCtalk_title(StringUtil.useNoHtml(ctalk.getCtalk_title()));
		ctalk.setCtalk_article(StringUtil.useBrNoHtml(ctalk.getCtalk_article()));
		
		request.setAttribute("ctalk", ctalk);
		
		return "comm/mainboard/ctalk/ctalk_detail.jsp";

	}

}
