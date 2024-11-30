package kr.czone_board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.dao.CZONE_BoardDAO;
import kr.board.vo.Cmenu_Re_BoardVO;
import kr.board.vo.Ctalk_Re_BoardVO;
import kr.board.vo.Czone_Re_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class UpdateReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		long czone_re_num = Long.parseLong(request.getParameter("czone_re_num"));
		
		CZONE_BoardDAO dao = CZONE_BoardDAO.getInstance();
		Czone_Re_BoardVO db_reply = dao.getReplyBoard(czone_re_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		Map<String, String> mapAjax = new HashMap<String, String>();

		if (user_num == null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		} else if(user_num!=null && user_num == db_reply.getMem_num()) {
			Czone_Re_BoardVO reply = new Czone_Re_BoardVO();
			reply.setCzone_re_num(czone_re_num);
			reply.setCzone_re_content(request.getParameter("czone_re_content"));
			dao.updateReplyBoard(reply);
			mapAjax.put("result", "success");

		} else {
			mapAjax.put("result", "wrongAccess");
		}
		return StringUtil.parseJSON(request, mapAjax);
	}
}
