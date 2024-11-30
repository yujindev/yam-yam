package kr.cmenu_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CMENU_BoardVO;
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

		long cmenu_num = Long.parseLong(request.getParameter("cmenu_num"));

		CMENU_BoardDAO cmenudao = CMENU_BoardDAO.getInstance();
		CMENU_BoardVO cmenuvo = cmenudao.getBoard(cmenu_num);
		if (user_num != cmenuvo.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		
		CMENU_BoardVO board = new CMENU_BoardVO();
		board.setCmenu_num(cmenu_num);
		board.setCmenu_title(request.getParameter("cmenu_title"));
		board.setCmenu_article(request.getParameter("cmenu_article"));
	
		return "redirect:/mainboard/cmenu/cmenu_detail.do?cmenu_num="+cmenu_num;
	}
}
