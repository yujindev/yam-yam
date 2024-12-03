package kr.reserv.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;

public class AdminReservAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");
        
        if (user_num == null) { // 로그인이 되지 않은 경우
            return "redirect:/member/loginForm.do";
        }

        Integer user_auth = (Integer) session.getAttribute("user_auth");
        if (user_auth == null || (user_auth != 7 && user_auth != 9)) { // 관리자가 아닌 경우
            return "common/not_admin.jsp";
        }

        // GET 요청 처리: 예약 상세 정보를 조회하고 수정 폼으로 전달
        if (request.getMethod().equalsIgnoreCase("GET")) {
            Long rs_num = Long.parseLong(request.getParameter("rs_num")); // 전달받은 예약 번호
            ReservDAO dao = ReservDAO.getInstance();
            ReservVO reserv = dao.getReservByRsNum(rs_num); // 예약 정보 조회

            if (reserv == null) { // 예약 정보가 없는 경우
                request.setAttribute("notice_msg", "존재하지 않는 예약 정보입니다.");
                request.setAttribute("notice_url", request.getContextPath() + "/fplace/adminReservList.do");
                return "common/alert_view.jsp";
            }

            request.setAttribute("reserv", reserv); // 예약 정보를 요청 객체에 저장
            return "/WEB-INF/views/fplace/detailReservForm.jsp"; // 수정 폼으로 이동
        }

        // POST 요청 처리: 예약 상태 수정
        if (request.getMethod().equalsIgnoreCase("POST")) {
            request.setCharacterEncoding("utf-8");
            Long rs_num = Long.parseLong(request.getParameter("rs_num")); // 예약 번호
            int rs_status = Integer.parseInt(request.getParameter("rs_status")); // 수정된 예약 상태
            ReservDAO dao = ReservDAO.getInstance();
            Long mem_num = dao.getMemNumByRsNum(rs_num); //예약자 회원번호
            dao.ManageReserv(rs_num, rs_status,mem_num); // 상태 수정

            request.setAttribute("notice_msg", "예약 상태가 성공적으로 수정되었습니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/fplace/adminReservList.do");
            return "common/alert_view.jsp"; // 결과 알림 페이지로 이동
        }

        // 예외 처리: 지원하지 않는 요청 방식
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return null;
    }
}