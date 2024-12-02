package kr.cbob_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
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

		long cbob_num = Long.parseLong(request.getParameter("cbob_num"));
		
		CBOB_BoardDAO cbobdao = CBOB_BoardDAO.getInstance();
		CBOB_BoardVO cbobvo = cbobdao.getBoard(cbob_num);
		if (user_num != cbobvo.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		
		CBOB_BoardVO board = new CBOB_BoardVO();
		board.setCbob_num(cbob_num);
		board.setCbob_title(request.getParameter("cbob_title"));
		board.setCbob_article(request.getParameter("cbob_article"));
		board.setCbob_menu(request.getParameter("cbob_menu"));
		board.setCbob_gender1(request.getParameter("cbob_gender1"));
		board.setCbob_gender2(request.getParameter("cbob_gender2"));
		board.setCbob_meet(request.getParameter("cbob_meet"));
		board.setCbob_hit(Integer.parseInt(request.getParameter("cbob_hit")));
		
		CBOB_BoardDAO dao = CBOB_BoardDAO.getInstance();
		dao.updatecbobBoard(board);
		
		return "redirect:/cbob_board/cbob_detail.do?cbob_num="+cbob_num;
	}
}
