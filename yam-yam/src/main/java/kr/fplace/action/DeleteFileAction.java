package kr.fplace.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();

		HttpSession session = request.getSession();
		Long user_num =(Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");

		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long fp_num = Long.parseLong(request.getParameter("fp_num"));

		FplaceDAO dao = FplaceDAO.getInstance();
		FplaceVO db_fplace = dao.getFplace(fp_num);

	
		if (user_num == null || (user_auth != 9 && user_auth != 7)){ // 로그인이 되지 않은 경우, 관리자 또는 식당관리자도 아닌경우
            mapAjax.put("result", "logout");
        }else if(user_num!=null && (user_num == db_fplace.getMem_num() || user_auth == 9)) {
        	//로그인한 회원번호와 작성자 회원번호 일치  자바빈(vO) 생성
			 if (db_fplace != null) {
			dao.deleteFile(fp_num);
			//파일삭제
			FileUtil.removeFile(request, db_fplace.getFp_storeimg());
			 mapAjax.put("result", "success");
			}
        }
	//json 데이터로 변환
	return StringUtil.parseJSON(request, mapAjax);
}

	}
