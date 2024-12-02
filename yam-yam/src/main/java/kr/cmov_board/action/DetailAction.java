package kr.cmov_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CMOV_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CMOV_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;
  
public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long cmov_num = Long.parseLong(request.getParameter("cmov_num"));
		
		CMOV_BoardDAO dao = CMOV_BoardDAO.getInstance();
		dao.updateReadcount(cmov_num);
		CMOV_BoardVO cmov = dao.getBoard(cmov_num);
		
		System.out.println(cmov);
		
		cmov.setCmov_title(StringUtil.useNoHtml(cmov.getCmov_title()));
		cmov.setCmov_article(StringUtil.useBrNoHtml(cmov.getCmov_article()));

		request.setAttribute("cmov", cmov);
		
		return "comm/mainboard/cmov/cmov_detail.jsp";

	}

}
