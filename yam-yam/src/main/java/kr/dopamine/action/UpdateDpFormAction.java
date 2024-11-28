package kr.dopamine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.dopamine.dao.DopamineDAO;
import kr.dopamine.vo.DopamineVO;
import kr.util.StringUtil;

public class UpdateDpFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		if(user_auth!=9) {//관리자가 아닌 경우
			return "common/not_admin.jsp";
		}
		
		long dp_num = Long.parseLong(request.getParameter("dp_num"));
		DopamineDAO dao = DopamineDAO.getInstance();
		DopamineVO dopamine = dao.getDp(dp_num);
		
		//큰 따옴표 처리(수정폼의 input 태그에만 명시
		dopamine.setDp_title(StringUtil.parseQuot(dopamine.getDp_title()));
		
		request.setAttribute("dopamine", dopamine);
		
		return "dopamine/updateDpForm.jsp";
	}

}
