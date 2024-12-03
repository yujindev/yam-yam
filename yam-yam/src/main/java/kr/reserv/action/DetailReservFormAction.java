package kr.reserv.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;

public class DetailReservFormAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	Long rs_num = Long.parseLong(request.getParameter("rs_num"));
        
        ReservDAO dao = ReservDAO.getInstance();
        ReservVO reserv = dao.getReservByRsNum(rs_num);

        request.setAttribute("reserv", reserv);

        return "/reserv/detailReservForm.jsp";
    }
}