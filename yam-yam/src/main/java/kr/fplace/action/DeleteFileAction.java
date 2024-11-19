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

		if(user_num==null) {
			mapAjax.put("result", "logout");
		}else if (user_auth != 9) {
		        mapAjax.put("result", "wrongAccess");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			long fp_num = Long.parseLong(request.getParameter("fp_num"));

			FplaceDAO dao = FplaceDAO.getInstance();
			FplaceVO db_fplace = dao.getFplace(fp_num);

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
