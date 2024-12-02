package kr.ctalk_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");

		long ctalk_num = Long.parseLong(request.getParameter("ctalk_num"));

		CTALK_BoardDAO ctalkdao = CTALK_BoardDAO.getInstance();
		CTALK_BoardVO ctalkvo = ctalkdao.getBoard(ctalk_num);
		if (user_num != ctalkvo.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		
		CTALK_BoardVO board = new CTALK_BoardVO();
		board.setCtalk_num(ctalk_num);
		board.setCtalk_title(request.getParameter("ctalk_title"));
		board.setCtalk_article(request.getParameter("ctalk_article"));
		
		CTALK_BoardDAO dao = CTALK_BoardDAO.getInstance();
		dao.updatectalkBoard(board);
		  
		return "redirect:/ctalk_board/ctalk_detail.do?ctalk_num="+ctalk_num;
	}
}
