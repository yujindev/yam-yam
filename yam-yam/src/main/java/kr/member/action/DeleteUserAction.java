package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class DeleteUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인이 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		String passwd = request.getParameter("passwd");
		
		//로그인한 아이디
		String user_id = 
				  (String)session.getAttribute("user_id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO db_member = dao.checkMember(id, user_id);
		boolean check = false;
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하는지
		//체크, 입력한 이메일과 저장된 이메일 일치 여부 체크
		if(db_member!=null && id.equals(user_id)
				 && phone.equals(db_member.getMem_phone())) {
			//비밀번호 일치 여부 체크
			check = db_member.isCheckedPw(passwd);
		}
		
		if(check) {//인증 성공
			//회원정보 삭제
			dao.deleteMember(user_num);
			//로그아웃
			session.invalidate();
		}
		request.setAttribute("check", check);	
		
		return "member/deleteUser.jsp";
	}

}




