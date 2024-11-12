package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
  
public class RegisterUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//자바빈(VO) 생성
		MemberVO member = new MemberVO();
		member.setMem_id(request.getParameter("mem_id"));
		member.setMem_nickname(request.getParameter("mem_nickname"));
		member.setMem_pw(request.getParameter("mem_pw"));
		member.setMem_phone(request.getParameter("mem_phone"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.insertMember(member);
		
		request.setAttribute("result_title", "가입 완료");
		request.setAttribute("result_msg", "가입이 완료되었습니다.");
		request.setAttribute("result_url", request.getContextPath()+"/main/main.do");
		
		//JSP 경로 반환
		return "common/result_view.jsp";
	}

}
