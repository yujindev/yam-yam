package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String mem_id = request.getParameter("mem_id");
		String mem_pw = request.getParameter("mem_pw");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(mem_id, null);
		boolean check = false;
		
		if(member!=null) {
			//비밀번호 일치 여부 체크
			check = member.isCheckedPw(mem_pw);
		}
		if(check) {//인증 성공
			//로그인 처리
			HttpSession session = request.getSession();
			session.setAttribute("user_num", member.getMem_num());
			session.setAttribute("user_id", member.getMem_id());
			session.setAttribute("user_auth", member.getMem_auth());
			//메인으로 리다이렉트
			return "redirect:/main/main.do";
		}
		//인증 실패
		return "member/login.jsp";
	}

}
