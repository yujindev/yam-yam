package kr.cbob_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;
  
public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long cbob_num = Long.parseLong(request.getParameter("cbob_num"));
		
		CBOB_BoardDAO dao = CBOB_BoardDAO.getInstance();
		dao.updateReadcount(cbob_num);
		CBOB_BoardVO cbob = dao.getBoard(cbob_num);
		
		System.out.println(cbob);
		
		cbob.setCbob_title(StringUtil.useNoHtml(cbob.getCbob_title()));
		cbob.setCbob_article(StringUtil.useBrNoHtml(cbob.getCbob_article()));
		
		request.setAttribute("cbob", cbob);
		
		return "comm/mainboard/cbob/cbob_detail.jsp";

	}

}
