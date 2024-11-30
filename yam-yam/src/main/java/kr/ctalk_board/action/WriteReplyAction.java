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
				Ctalk_Re_BoardVO reply = new Ctalk_Re_BoardVO();
				reply.setMem_num(user_num); // 댓글 작성자 회원번호
				reply.setCtalk_re_content(request.getParameter("ctalk_re_content"));
				reply.setCtalk_num(Long.parseLong(request.getParameter("ctalk_num")));
				
				CTALK_BoardDAO dao = CTALK_BoardDAO.getInstance();
				dao.insertReplyctalk(reply);
				mapAjax.put("result", "success");
			}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
