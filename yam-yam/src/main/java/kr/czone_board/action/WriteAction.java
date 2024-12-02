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
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		
		CZONE_BoardVO czone_board = new CZONE_BoardVO();
		czone_board.setCzone_title(request.getParameter("czone_title"));
		czone_board.setCzone_article(request.getParameter("czone_article"));
		czone_board.setCzone_loc(request.getParameter("czone_loc"));
		czone_board.setCzone_filename(FileUtil.uploadFile(request, "czone_filename"));
		czone_board.setMem_num(user_num);
		
		CZONE_BoardDAO dao = CZONE_BoardDAO.getInstance();
		dao.insertCZONE_Board(czone_board);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/czone_board/list.do");

		return "common/alert_view.jsp";
	}

}
