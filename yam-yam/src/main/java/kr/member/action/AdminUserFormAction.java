package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class AdminUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num==null) {//로그인이 안됨
			return "redirect:/member/loginForm.do";
		}
		if(user_auth!=9) {//관리자가 아님
			return "common/not_admin.jsp";
		}
		//전송된 데이터 반환
		long mem_num = Long.parseLong(request.getParameter("mem_num"));
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(mem_num);
		
		request.setAttribute("member", member);
		request.setAttribute("mem_num", mem_num);
		
		//JSP 경로 반환
		return "member/detailUserForm.jsp";
	}

}
