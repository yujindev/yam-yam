package kr.fplace.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.ReviewsVO;
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class ListReviewsAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String rowCount = request.getParameter("rowCount");
		if(rowCount==null) rowCount = "10";
		
		long fp_num = Long.parseLong(request.getParameter("fp_num"));
		
		FplaceDAO dao = FplaceDAO.getInstance();
		int count = dao.getReviewsCount(fp_num);
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num==null) { user_num = 0L; } //이거 없으면 로그인 안할때 리뷰가 안뜨는 오류남!
		 
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		List<ReviewsVO> list = null;
		if(count > 0) {
			list = dao.getListReviews(page.getStartRow(), page.getEndRow(), fp_num, user_num);
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		mapAjax.put("user_num", user_num);
		
		return StringUtil.parseJSON(request, mapAjax);
	}
	
}
