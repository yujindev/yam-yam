package kr.cbob_board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.Cbob_Re_BoardVO;
import kr.board.vo.Cmenu_Re_BoardVO;
import kr.board.vo.Ctalk_Re_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class UpdateReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		long cbob_re_num = Long.parseLong(request.getParameter("cbob_re_num"));
		
		CBOB_BoardDAO dao = CBOB_BoardDAO.getInstance();
		Cbob_Re_BoardVO db_reply = dao.getReplyBoard(cbob_re_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		Map<String, String> mapAjax = new HashMap<String, String>();

		if (user_num == null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		} else if(user_num!=null && user_num == db_reply.getMem_num()) {
			Cbob_Re_BoardVO reply = new Cbob_Re_BoardVO();
			reply.setCbob_re_num(cbob_re_num);
			reply.setCbob_re_content(request.getParameter("cbob_re_content"));
			dao.updateReplyBoard(reply);
			mapAjax.put("result", "success");

		} else {
			mapAjax.put("result", "wrongAccess");
		}
		return StringUtil.parseJSON(request, mapAjax);
	}
}
