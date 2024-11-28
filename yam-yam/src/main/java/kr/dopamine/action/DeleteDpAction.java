package kr.dopamine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.dopamine.dao.DopamineDAO;

public class DeleteDpAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		int user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		if(user_auth!=9) {//관리자가 아닌 경우
			return "common/not_admin.jsp";
		}
		
		//관리자인 경우
		long dp_num = Long.parseLong(request.getParameter("dp_num"));
		DopamineDAO dao = DopamineDAO.getInstance();
		
		dao.deleteDp(dp_num);
		
		request.setAttribute("notice_msg", "글 삭제 완료!");
		request.setAttribute("notice_url", request.getContextPath()+"/dopamine/dpList.do");

		return "common/alert_view.jsp";
	}

}
