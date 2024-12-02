package kr.reserv.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;
import kr.util.PagingUtil;

public class AdminReservListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if((user_auth == null || user_auth != 7) && user_auth!= 9) {//관리자로 로그인하지 않은 경우
			return "common/not_admin.jsp";
		}
		
		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
			
		ReservDAO dao = ReservDAO.getInstance();
		Long fp_num = dao.getFpNumByMemNum(user_num);
		int count = dao.AllReservCount(fp_num);
		
		if (fp_num == null) {
			throw new Exception("fp_num을 찾을 수 없습니다. 관리자의 가게 정보가 올바르지 않습니다.");
		}
				
		//페이지 처리
		PagingUtil page = new PagingUtil(null,null,Integer.parseInt(pageNum),count,20,10,"adminReservList.do");
		List<ReservVO> reserv = null;
		if(count > 0) {
			reserv = dao.AllReservList(fp_num,page.getStartRow(),page.getEndRow());
		}
				
		request.setAttribute("count", count);
		request.setAttribute("reserv", reserv);
		request.setAttribute("page", page.getPage());
		
		//JSP 경로 반환
		return "reserv/adminReservList.jsp";
	}

}
