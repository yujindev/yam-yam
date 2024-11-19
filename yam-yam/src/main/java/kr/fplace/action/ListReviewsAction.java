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
		//조회수 증가
		dao.updateReadcount(Long.parseLong(request.getParameter("reviews_num")));
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		List<ReviewsVO> list = null;
		if(count > 0) {
			list = dao.getListReviews(page.getStartRow(), page.getEndRow(),fp_num);
		}else {
			list = Collections.emptyList();
		}
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		mapAjax.put("user_num", user_num);
		
		return StringUtil.parseJSON(request, mapAjax);
	}
	
}
