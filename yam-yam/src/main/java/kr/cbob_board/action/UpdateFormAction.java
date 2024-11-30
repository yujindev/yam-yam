package kr.cbob_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
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
		
		long cbob_num = Long.parseLong(request.getParameter("cbob_num"));
		System.out.println(cbob_num);
		
		CBOB_BoardDAO dao = CBOB_BoardDAO.getInstance();
		CBOB_BoardVO cbob = dao.getBoard(cbob_num);
		if (user_num != cbob.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		// 큰 따옴표 처리 (수정폼의 input 태그에만 명시)
		cbob.setCbob_title(StringUtil.parseQuot(cbob.getCbob_title()));
		request.setAttribute("cbob", cbob);
		
		return "comm/mainboard/cbob/cbob_updateForm.jsp";
	}

}
