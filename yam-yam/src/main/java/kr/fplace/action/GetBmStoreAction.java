package kr.fplace.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.BmreviewsVO;
import kr.fplace.vo.BmstoreVO;
import kr.util.StringUtil;

public class GetBmStoreAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long fp_num = Long.parseLong(
				        request.getParameter("fp_num"));
		
		Map<String,Object> mapAjax = 
				             new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		FplaceDAO dao = FplaceDAO.getInstance();
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("status", "noBmstore");
		}else {//로그인이 된 경우
			BmstoreVO bmstore = 
					dao.selectBmstore(new BmstoreVO(fp_num,user_num));
			if(bmstore!=null) {
				//로그인한 회원이 좋아요 클릭
				mapAjax.put("status", "yesBmstore");
			}else {
				//로그인한 회원이 좋아요 미클릭
				mapAjax.put("status", "noBmstore");
			}
		}
		//좋아요 개수
		mapAjax.put("count", dao.selectBmstoreCount(fp_num));
		
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}

