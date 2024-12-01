package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ModifyPasswordAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//로그인된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//전송된 데이터 반환
		String id = request.getParameter("mem_id");
		String nickname = request.getParameter("mem_nickname");
		//현재 비밀번호
		String origin_pw = request.getParameter("origin_pw");
		//새 비밀번호
		String pw = request.getParameter("mem_pw");
		
		//현재 로그인한 아이디
		String user_id = (String)session.getAttribute("user_id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id, nickname);
		
		boolean check = false;
		
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하는지 체크
		if(member!=null && id.equals(user_id)) {
			//비밀번호 일치 여부 체크
			check = member.isCheckedPw(origin_pw);
		}
		if(check) {//인증 성공 -> 비번 변경
			dao.modifyPw(pw, user_num);
		}
		
		request.setAttribute("check", check);
		//JSP 경로 반환
		return "member/modifyPassword.jsp";
	}

}
