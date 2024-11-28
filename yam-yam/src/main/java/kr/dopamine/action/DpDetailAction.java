package kr.dopamine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.dopamine.dao.DopamineDAO;
import kr.dopamine.vo.DopamineVO;
import kr.util.StringUtil;

public class DpDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		long dp_num = Long.parseLong(request.getParameter("dp_num"));

		DopamineDAO dao = DopamineDAO.getInstance();
		DopamineVO dopamine = dao.getDp(dp_num);
		//Html 태그를 허용하지않음
		dopamine.setDp_title(StringUtil.useNoHtml(dopamine.getDp_title()));
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		dopamine.setDp_content(StringUtil.useBrNoHtml(dopamine.getDp_content()));

		request.setAttribute("dopamine", dopamine);

		return "dopamine/detail.jsp";
	}

}
