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

public class WriteBmReviewsAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = 
				new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			long reviews_num = Long.parseLong(
					request.getParameter("reviews_num"));

			BmreviewsVO bmreviewsVO = new BmreviewsVO();
			bmreviewsVO.setReviews_num(reviews_num);
			bmreviewsVO.setMem_num(user_num);

			FplaceDAO dao = FplaceDAO.getInstance();
			//좋아요 등록 여부 체크
			BmreviewsVO db_bmreviews = dao.selectBmreviews(bmreviewsVO);
			if(db_bmreviews!=null) {//좋아요 등록 O
				//좋아요 삭제
				dao.deleteBmreviews(db_bmreviews);
				mapAjax.put("status", "noBmreviews");
			}else {//좋아요 등록 X
				//좋아요 등록
				dao.insertBmreviews(bmreviewsVO);
				mapAjax.put("status", "yesBmreviews");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectBmreviewsCount(reviews_num));
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);

	}


}
