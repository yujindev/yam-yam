package kr.cmov_board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CBOB_BoardDAO;
import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CMOV_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.Cbob_Re_BoardVO;
import kr.board.vo.Cmenu_Re_BoardVO;
import kr.board.vo.Cmov_Re_BoardVO;
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
				Cmov_Re_BoardVO reply = new Cmov_Re_BoardVO();
				reply.setMem_num(user_num); // 댓글 작성자 회원번호
				reply.setCmov_re_content(request.getParameter("cmov_re_content"));
				reply.setCmov_num(Long.parseLong(request.getParameter("cmov_num")));
				
				CMOV_BoardDAO dao = CMOV_BoardDAO.getInstance();
				dao.insertReplycmov(reply);
				mapAjax.put("result", "success");
			}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
