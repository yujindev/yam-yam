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

public class WriteBmStoreAction implements Action {

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
			long fp_num = Long.parseLong(
					request.getParameter("fp_num"));

			BmstoreVO bmstoreVO = new BmstoreVO();
			bmstoreVO.setFp_num(fp_num);
			bmstoreVO.setMem_num(user_num);

			FplaceDAO dao = FplaceDAO.getInstance();
			//좋아요 등록 여부 체크
			BmstoreVO db_bmstore = dao.selectBmstore(bmstoreVO);
			if(db_bmstore!=null) {//좋아요 등록 O
				//좋아요 삭제
				dao.deleteBmstore(db_bmstore);
				mapAjax.put("status", "noBmstore");
			}else {//좋아요 등록 X
				//좋아요 등록
				dao.insertBmstore(bmstoreVO);
				mapAjax.put("status", "yesBmstore");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectBmstoreCount(fp_num));
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);

	}


}
