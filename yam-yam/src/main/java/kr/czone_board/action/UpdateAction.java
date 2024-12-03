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

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");

		long czone_num = Long.parseLong(request.getParameter("czone_num"));

		CZONE_BoardDAO czonedao = CZONE_BoardDAO.getInstance();
		CZONE_BoardVO czonevo = czonedao.getBoard(czone_num);
		if (user_num != czonevo.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		
		CZONE_BoardVO board = new CZONE_BoardVO();
		board.setCzone_num(czone_num);
		board.setCzone_title(request.getParameter("czone_title"));
		board.setCzone_loc(request.getParameter("czone_loc"));
		board.setCzone_article(request.getParameter("czone_article"));
		board.setMem_nickname(request.getParameter("mem_nickname"));

	
		return "redirect:/czone_board/czone_detail.do?czone_num="+czone_num;
	}
}
