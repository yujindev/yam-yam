package kr.fplace.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.ReviewsVO;
import kr.util.StringUtil;

public class DeleteReviewsAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long re_num = Long.parseLong(request.getParameter("reviews_num"));
		Map<String,String> mapAjax = new HashMap<String,String>();
		FplaceDAO dao = FplaceDAO.getInstance();
		ReviewsVO db_reviews = dao.getReviews(re_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num == db_reviews.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 일치
			dao.deleteReviews(re_num);
			mapAjax.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			mapAjax.put("result", "wrongAccess");
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}
