package kr.dopamine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class WriteDpFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth!=9) {//관리자가 아닌 경우
			return "common/not_admin.jsp";
		}
		
		return "dopamine/writeDpForm.jsp";
	}

}
