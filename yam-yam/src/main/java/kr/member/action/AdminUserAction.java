package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;

public class AdminUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}		
		if(user_auth != 9) {//관리자가 아닌 경우
			return "common/notice.jsp";
		}
		
		/* ============ 관리자 로그인 ============ */
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		Long mem_num = Long.parseLong(request.getParameter("mem_num"));
		int auth = Integer.parseInt(request.getParameter("mem_auth"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.updateMemberByAdmin(auth, mem_num);
		
		request.setAttribute("notice_msg", "회원등급이 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/member/adminUserForm.do?mem_num="+mem_num);
		
		return "common/alert_view.jsp";
	}

}
