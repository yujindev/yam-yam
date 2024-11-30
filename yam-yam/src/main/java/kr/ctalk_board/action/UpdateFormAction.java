package kr.ctalk_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		long ctalk_num = Long.parseLong(request.getParameter("ctalk_num"));
		
		CTALK_BoardDAO ctalkdao = CTALK_BoardDAO.getInstance();
		CTALK_BoardVO ctalkvo = ctalkdao.getBoard(ctalk_num);
		if (user_num != ctalkvo.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		// 큰 따옴표 처리 (수정폼의 input 태그에만 명시)
		ctalkvo.setCtalk_title(StringUtil.parseQuot(ctalkvo.getCtalk_title()));
		request.setAttribute("ctalkvo", ctalkvo);
		
		return "comm/mainboard/ctalk/ctalk_updateForm.jsp";
	}

}
