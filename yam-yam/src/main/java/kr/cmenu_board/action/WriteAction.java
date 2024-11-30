package kr.cmenu_board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
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
		
		CMENU_BoardVO cmenu_board = new CMENU_BoardVO();
		cmenu_board.setCmenu_title(request.getParameter("cmenu_title"));
		
		cmenu_board.setCmenu_article(request.getParameter("cmenu_article"));
		cmenu_board.setCmenu_filename(FileUtil.uploadFile(request, "cmenu_filename"));
		cmenu_board.setCmenu_filename2(FileUtil.uploadFile(request, "cmenu_filename2"));
		cmenu_board.setMem_num(user_num);
		
		CMENU_BoardDAO dao = CMENU_BoardDAO.getInstance();
		dao.insertCMENU_Board(cmenu_board);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/cmenu_board/list.do");

		return "common/alert_view.jsp";
	}

}
