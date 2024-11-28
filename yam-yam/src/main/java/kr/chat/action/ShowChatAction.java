package kr.chat.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.controller.Action;
import kr.member.vo.MemberVO;

public class ShowChatAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인이 되지 않았을때 로그인 페이지로 이동
		HttpSession session = request.getSession();
		Long user_num = (Long) session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/member/loginForm.do";
		}

		long mem_num = Long.parseLong(request.getParameter("chat_receiver_num"));

		// DAO호출
		ChatDAO dao = ChatDAO.getInstance();
		MemberVO member = dao.getMemberByMem_num(mem_num);

		request.setAttribute("member", member);

		return "chat/showChat.jsp";
	}

}
