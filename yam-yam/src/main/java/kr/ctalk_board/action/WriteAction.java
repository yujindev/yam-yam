package kr.ctalk_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CTALK_BoardDAO;
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
		
		CTALK_BoardVO ctalk_board = new CTALK_BoardVO();
		ctalk_board.setCtalk_title(request.getParameter("ctalk_title"));
		
		ctalk_board.setCtalk_article(request.getParameter("ctalk_article"));
		ctalk_board.setMem_num(user_num);
		
		CTALK_BoardDAO dao = CTALK_BoardDAO.getInstance();
		dao.insertCTALK_Board(ctalk_board);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/ctalk_board/list.do");

		return "common/alert_view.jsp";
	}

}
