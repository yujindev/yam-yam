package kr.reserv.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;

public class ReservAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인 안됨
			return "redirect:/member/loginForm.do";
		}
		
		//로그인이 된 경우
		//자바빈 생성
		ReservVO reserv = new ReservVO();
		reserv.setFp_num(Long.parseLong(request.getParameter("fp_num")));
		reserv.setRs_cnt(Integer.parseInt(request.getParameter("rs_cnt"))); //인원수
		reserv.setRs_time(request.getParameter("rs_time"));
		reserv.setMem_num(user_num);//작성자의 회원번호
		
		
		ReservDAO dao = ReservDAO.getInstance();
		dao.reservation(reserv);
		
		request.setAttribute("reserv", reserv);
		
		request.setAttribute("notice_msg", "예약 신청이 완료되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/fplace/list.do");
		
		return "common/alert_view.jsp";
	}

}
