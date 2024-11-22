package kr.fplace.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FpMenuVO;
import kr.util.StringUtil;

public class DeleteFpmenuAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long fpmenu_num = Long.parseLong(request.getParameter("fpmenu_num"));
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		FplaceDAO dao = FplaceDAO.getInstance();
		FpMenuVO db_fpmenu = dao.getFpMenuDetail(fpmenu_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num ==null ) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num != null & user_auth == 9) {
			//로그인한 회원번호와 작성자 회원번호가 일치
			dao.deleteFpMenu(fpmenu_num);
			mapAjax.put("result", "success");
		}else {
			mapAjax.put("result", "wrongAccess");
		}
			
		return StringUtil.parseJSON(request, mapAjax);
	}

}
