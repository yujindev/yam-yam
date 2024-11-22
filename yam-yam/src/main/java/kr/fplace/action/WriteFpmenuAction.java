package kr.fplace.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FpMenuVO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class WriteFpmenuAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if (user_num == null || user_auth != 9) {//로그인이 되지 않은경우, 관리자로 로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			request.setCharacterEncoding("utf-8");
			FpMenuVO fpmenu = new FpMenuVO();
			fpmenu.setMem_num(user_num);
			fpmenu.setFpmenu_name(request.getParameter("fpmenu_name"));
			fpmenu.setFpmenu_img(FileUtil.uploadFile(request,"fpmenu_img"));
			fpmenu.setFpmenu_price(Integer.parseInt(request.getParameter("fpmenu_price")));
			fpmenu.setFp_num(Long.parseLong(request.getParameter("fp_num")));

			FplaceDAO dao = FplaceDAO.getInstance();
			dao.insertFpMenu(fpmenu);

			mapAjax.put("result", "success");
		}

		//json  데이터로 반환
		return StringUtil.parseJSON(request, mapAjax);

	}
}
