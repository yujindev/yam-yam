package kr.fplace.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;

public class GetScoreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//전송된 데이터 반환
		long fp_num = Long.parseLong(request.getParameter("fp_num"));
				
		FplaceDAO dao = FplaceDAO.getInstance();
		double score = dao.getAverageScoreByRestaurant(fp_num);
		
		request.setAttribute("score", score);
		
		return "fplace/list.jsp";
	}
}
