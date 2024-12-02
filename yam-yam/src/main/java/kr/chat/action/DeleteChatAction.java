package kr.chat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DeleteChatAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
    	HttpSession session = request.getSession();
    	Long user_num = (Long)session.getAttribute("user_num");
    	
    	if(user_num == null) {//로그인이 되지 않은 경우 
    		mapAjax.put("result", "logout");
    	}else {//로그인이 된 경우
    		//전송된 데이터 인코딩 처리
    		request.setCharacterEncoding("utf-8");
    		
    		long chat_receiver_num = Long.parseLong(request.getParameter("chat_receiver_num"));
    		long chat_sender_num = Long.parseLong(request.getParameter("chat_sender_num"));
    		
    		ChatDAO dao = ChatDAO.getInstance();
    		dao.deleteChat(chat_receiver_num,chat_sender_num );
    		
    		mapAjax.put("result", "success");
    		
    	}
    	
    	return "redirect:/chat/chatList.do";
		
	}

}
