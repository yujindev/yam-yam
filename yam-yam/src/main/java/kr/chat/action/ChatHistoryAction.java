package kr.chat.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class ChatHistoryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우  
			mapAjax.put("result", "logout");
		}else {
		//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			String chat_receiver_num = request.getParameter("chat_receiver_num");
			
			ChatDAO dao = ChatDAO.getInstance();
			List<ChatVO> list = dao.getChatHistory(user_num, Long.parseLong(chat_receiver_num));
			if(list==null) list = Collections.emptyList();
			
			mapAjax.put("result", "success");
			mapAjax.put("list", list);
			
		}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
