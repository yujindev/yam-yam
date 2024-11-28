package kr.others.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.others.dao.OthersDAO;
  
public class SaveStoreAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mem_id = request.getParameter("mem_id");
		
		
		//DAO호출
		ChatDAO dao = ChatDAO.getInstance();
		MemberVO member= dao.getMember(mem_id);
	
		request.setAttribute("member",member);
		
		//JSP 경로 반환
		return "others/saveStore.jsp";
	}

}
