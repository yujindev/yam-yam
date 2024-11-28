package kr.reserv.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FpTimeVO;
import kr.fplace.vo.FplaceVO;
import kr.reserv.dao.ReservDAO;

public class ReservFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인X
			return "redirect:/member/loginForm.do";
		}
	
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");

		Long fp_num = Long.parseLong(request.getParameter("fp_num"));
		FplaceDAO fplaceDAO = FplaceDAO.getInstance();
		FplaceVO fplace = fplaceDAO.getFplace(fp_num);

		if(fplace == null) {
			request.setAttribute("notice_msg", "해당 식당 정보를 찾을 수 없습니다.");
			request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");
			return "common/alert_view.jsp";
		}

		request.setAttribute("fplace", fplace);

		ReservDAO dao = ReservDAO.getInstance();
		List<FpTimeVO> list = null;
		list = dao.getTimeList(fp_num);
		
		request.setAttribute("list", list);
		
		return "reserv/reservForm.jsp";
	}
}
