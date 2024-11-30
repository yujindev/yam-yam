package kr.cbob_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		
		CBOB_BoardVO cbob_board = new CBOB_BoardVO();
		cbob_board.setCbob_title(request.getParameter("cbob_title"));
		cbob_board.setCbob_article(request.getParameter("cbob_article"));
		cbob_board.setCbob_meet(request.getParameter("cbob_meet"));
		cbob_board.setCbob_menu(request.getParameter("cbob_menu"));
		cbob_board.setCbob_gender1(request.getParameter("cbob_gender1"));
		cbob_board.setCbob_gender2(request.getParameter("cbob_gender2"));
		cbob_board.setMem_num(user_num);
		
		CBOB_BoardDAO dao = CBOB_BoardDAO.getInstance();
		dao.insertCBOB_Board(cbob_board);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/cbob_board/list.do");

		return "common/alert_view.jsp";
	}

}
