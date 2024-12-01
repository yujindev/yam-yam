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

public class UpdateFpmenuAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		//메뉴 번호
		long fpmenu_num = Long.parseLong(request.getParameter("fpmenu_num"));
		
		FplaceDAO dao = FplaceDAO.getInstance();
		FpMenuVO db_fpmenu = dao.getFpMenuDetail(fpmenu_num);
		
		//관리자 권한 확인
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if (user_num == null || (user_auth != 9 && user_auth != 7)){ // 로그인이 되지 않은 경우, 관리자 또는 식당관리자도 아닌경우
            mapAjax.put("result", "logout");
        }else if(user_num!=null && (user_num == db_fpmenu.getMem_num() || user_auth == 9)) {
        	//로그인한 회원번호와 작성자 회원번호 일치  자바빈(vO) 생성
        	FpMenuVO fpmenu = new FpMenuVO();
        	fpmenu.setFpmenu_num(fpmenu_num);
        	fpmenu.setFpmenu_name(request.getParameter("fpmenu_name"));
			fpmenu.setFpmenu_img(FileUtil.uploadFile(request,"fpmenu_img"));
			fpmenu.setFpmenu_price(Integer.parseInt(request.getParameter("fpmenu_price")));
        	
			//메뉴 수정
            dao.updateFpMenu(fpmenu);
            mapAjax.put("result", "success");
            
        } else {//관리자 권한이 없는경우
            mapAjax.put("result", "wrongAccess");
        }
        return StringUtil.parseJSON(request, mapAjax);
    }
	

}
