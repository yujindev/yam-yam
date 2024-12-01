package kr.tmenu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.tmenu.dao.TmenuDAO;
import kr.tmenu.vo.TmenuVO;

public class TmenuWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth !=9 ) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}else {
		
		//로그인된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		TmenuVO tmenu = new TmenuVO();
		tmenu.setTm_menu(request.getParameter("tm_menu"));
		tmenu.setMem_num(user_num);
		
		TmenuDAO dao = TmenuDAO.getInstance();
		dao.insertTmenu(tmenu);
		
		request.setAttribute("notice_msg","메뉴 목록 등록 완료!");
		request.setAttribute("notice_url", request.getContextPath()+"/tmenu/tmenuList.do");
		}
		return "common/alert_view.jsp";
	}

}
