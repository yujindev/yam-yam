package kr.cmov_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMOV_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CBOB_BoardVO;
import kr.board.vo.CMOV_BoardVO;
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
		
		long cmov_num = Long.parseLong(request.getParameter("cmov_num"));
		System.out.println(cmov_num);
		
		CMOV_BoardDAO dao = CMOV_BoardDAO.getInstance();
		CMOV_BoardVO cmov = dao.getBoard(cmov_num);
		if (user_num != cmov.getMem_num()) {
			// 로그인한 회원번호와 작성자 회원번호 불일치
			return "common/notice.jsp";
		}
		// 큰 따옴표 처리 (수정폼의 input 태그에만 명시)
		cmov.setCmov_title(StringUtil.parseQuot(cmov.getCmov_title()));
		request.setAttribute("cmov", cmov);
		
		return "comm/mainboard/cmov/cmov_updateForm.jsp";
	}

}
