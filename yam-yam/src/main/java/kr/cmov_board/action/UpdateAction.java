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

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");

		long cmov_num = Long.parseLong(request.getParameter("cmov_num"));
		
		CMOV_BoardDAO cmovdao = CMOV_BoardDAO.getInstance();
		CMOV_BoardVO cmov = cmovdao.getBoard(cmov_num);
		if (user_num != cmov.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		
		CMOV_BoardVO board = new CMOV_BoardVO();
		board.setCmov_num(cmov_num);
		board.setCmov_title(request.getParameter("cmov_title"));
		board.setCmov_article(request.getParameter("cmov_article"));
		board.setMem_nickname(request.getParameter("mem_nickname"));

		CMOV_BoardDAO dao = CMOV_BoardDAO.getInstance();
		dao.updatecmovBoard(cmov);
		return "redirect:/cmov_board/cmov_detail.do?cmov_num="+cmov_num;

	}
}
