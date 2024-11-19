package kr.fplace.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.ReviewsVO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class WriteReviewsAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			request.setCharacterEncoding("utf-8");
			ReviewsVO reviews = new ReviewsVO();
			reviews.setMem_num(user_num);
			reviews.setReviews_con(request.getParameter("reviews_con"));
			reviews.setReviews_score(Integer.parseInt(request.getParameter("reviews_score")));
			reviews.setReviews_img1(FileUtil.uploadFile(request, "reviews_img1")); //"" 안은 html에서 name속성!!
			reviews.setFp_num(Long.parseLong(request.getParameter("fp_num")));//댓글의 부모 글번호

			FplaceDAO dao = FplaceDAO.getInstance();
			dao.insertReviews(reviews);
			
			mapAjax.put("result", "success");
		}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
