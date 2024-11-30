package kr.cmov_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CMOV_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CMOV_BoardVO;
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
		
		CMOV_BoardVO cmov_board = new CMOV_BoardVO();
		cmov_board.setCmov_title(request.getParameter("cmov_title"));
		
		cmov_board.setCmov_article(request.getParameter("cmov_article"));
		cmov_board.setCmov_link(request.getParameter("cmov_link"));

		cmov_board.setMem_num(user_num);
		
		CMOV_BoardDAO dao = CMOV_BoardDAO.getInstance();
		dao.insertCMOV_Board(cmov_board);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/cmov_board/list.do");

		return "common/alert_view.jsp";
	}

}
