package kr.fplace.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		long fp_num =Long.parseLong(request.getParameter("fp_num"));
		FplaceDAO dao =FplaceDAO.getInstance();
		FplaceVO fplace = dao.getFplace(fp_num);
		
		if (user_auth != 9 && fplace.getMem_num() != user_num) {
		    return "common/notice.jsp";
		}

		
		request.setAttribute("fplace", fplace);
		
		return "fplace/updateForm.jsp";
	}

}
