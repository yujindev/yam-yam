package kr.tmenu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.tmenu.dao.TmenuDAO;


public class TmenuDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		 Integer user_auth = (Integer)session.getAttribute("user_auth");
        if (user_num == null) {//로그인 되지 않은 경우
            return "redirect:/member/loginForm.do";
        }

		if(user_auth !=9 ) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}else {
        
		long tm_num = Long.parseLong(request.getParameter("tm_num"));
        TmenuDAO dao = TmenuDAO.getInstance();

        dao.deleteTmenu(tm_num);
       
        
        request.setAttribute("notice_msg", "메뉴 목록 삭제 완료!");
        request.setAttribute("notice_url", request.getContextPath()+"/tmenu/tmenuList.do");
        
        
		}
		return "common/alert_view.jsp";
	}

}
