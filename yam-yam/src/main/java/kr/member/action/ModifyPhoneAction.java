package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;

public class ModifyPhoneAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		String mem_phone = request.getParameter("mem_phone");
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.modifyPhone(user_num, mem_phone);
		
		request.setAttribute(
				       "notice_msg", "연락처 수정 완료!");
		request.setAttribute("notice_url", 
		     request.getContextPath()+"/member/myPage.do");
		//JSP 경로 반환
		return "common/alert_view.jsp";
	}

}