package kr.cmenu_board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.Cmenu_Re_BoardVO;
import kr.board.vo.Ctalk_Re_BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class WriteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if (user_num == null) { // 로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
			} else {
				request.setCharacterEncoding("utf-8");
				Cmenu_Re_BoardVO reply = new Cmenu_Re_BoardVO();
				reply.setMem_num(user_num); // 댓글 작성자 회원번호
				reply.setCmenu_re_content(request.getParameter("cmenu_re_content"));
				reply.setCmenu_num(Long.parseLong(request.getParameter("cmenu_num")));
				
				CMENU_BoardDAO dao = CMENU_BoardDAO.getInstance();
				dao.insertReplycmenu(reply);
				mapAjax.put("result", "success");
			}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
