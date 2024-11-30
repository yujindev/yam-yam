package kr.czone_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.dao.CZONE_BoardDAO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.board.vo.CZONE_BoardVO;
import kr.controller.Action;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		long czone_num = Long.parseLong(request.getParameter("czone_num"));

		CZONE_BoardDAO dao = CZONE_BoardDAO.getInstance();
		CZONE_BoardVO db_board = dao.getBoard(czone_num);
		
		if (user_num != db_board.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		dao.deleteCzone(czone_num);
		
		request.setAttribute("notice_msg", "글삭제 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/czone_board/list.do");

		return "common/alert_view.jsp";
	}

}
