package kr.ctalk_board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.Ctalk_Re_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DeleteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		long re_num = Long.parseLong(request.getParameter("re_num"));
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		CTALK_BoardDAO dao = CTALK_BoardDAO.getInstance();
		Ctalk_Re_BoardVO db_reply = dao.getReplyBoard(re_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if (user_num == null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		} else if(user_num!=null && user_num == db_reply.getMem_num()) {
			dao.deleteCtalkRe(re_num);
			mapAjax.put("result", "success");
		} else {
			mapAjax.put("result", "wrongAccess");
		}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
