package kr.fplace.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.BmreviewsVO;
import kr.util.StringUtil;

public class GetBmReviewsAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long reviews_num = Long.parseLong(
				        request.getParameter("reviews_num"));
		
		Map<String,Object> mapAjax = 
				             new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		FplaceDAO dao = FplaceDAO.getInstance();
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("status", "noBmreviews");
		}else {//로그인이 된 경우
			BmreviewsVO bmreviews = 
					dao.selectBmreviews(new BmreviewsVO(reviews_num,user_num));
			if(bmreviews!=null) {
				//로그인한 회원이 좋아요 클릭
				mapAjax.put("status", "yesBmreviews");
			}else {
				//로그인한 회원이 좋아요 미클릭
				mapAjax.put("status", "noBmreviews");
			}
		}
		//좋아요 개수
		mapAjax.put("count", dao.selectBmreviewsCount(reviews_num));
		
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}

