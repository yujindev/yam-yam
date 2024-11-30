package kr.ctalk_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		long ctalk_num = Long.parseLong(request.getParameter("ctalk_num"));

		CTALK_BoardDAO dao = CTALK_BoardDAO.getInstance();
		CTALK_BoardVO db_board = dao.getBoard(ctalk_num);
		
		if (user_num != db_board.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		dao.deleteCtalk(ctalk_num);
		
		request.setAttribute("notice_msg", "글삭제 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/ctalk_board/list.do");

		return "common/alert_view.jsp";
	}

}
