package kr.cmenu_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CMENU_BoardVO;
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
		
		long cmenu_num = Long.parseLong(request.getParameter("cmenu_num"));
		
		CMENU_BoardDAO dao = CMENU_BoardDAO.getInstance();
		CMENU_BoardVO cmenu = dao.getBoard(cmenu_num);
		if (user_num != cmenu.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		// 큰 따옴표 처리 (수정폼의 input 태그에만 명시)
		cmenu.setCmenu_title(StringUtil.parseQuot(cmenu.getCmenu_title()));
		request.setAttribute("cmenu", cmenu);
		
		return "comm/mainboard/cmenu/cmenu_updateForm.jsp";
	}

}
