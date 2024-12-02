package kr.chat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ChatListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do"; 
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum="1";
		
		
		ChatDAO dao = ChatDAO.getInstance();
		int count = dao.getChatCount(user_num);
	    System.out.println(count);
		
		//페이징처리 
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,20,10,"chatList.do");
		List<ChatVO> list = null;
		
		if(count>0) {
			list = dao.getChatList(page.getStartRow(), page.getEndRow(), user_num);
		}
		    
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "chat/chatList.jsp";
	}

}
