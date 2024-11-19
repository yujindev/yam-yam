package kr.fplace.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
        if (user_num == null) {//로그인 되지 않은 경우
            return "redirect:/member/loginForm.do";
        }
        long fp_num = Long.parseLong(request.getParameter("fp_num"));
        
        FplaceDAO dao = FplaceDAO.getInstance();
        FplaceVO db_fplace = dao.getFplace(fp_num);
        Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth !=9 ) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}else {
        dao.deleteFplace(fp_num);
        //파일 삭제
        FileUtil.removeFile(request, db_fplace.getFp_storeimg());
        
        request.setAttribute("notice_msg", "식당정보 삭제 완료!");
        request.setAttribute("notice_url", request.getContextPath()+"/fplace/list.do");
        
        
		}
		return "common/alert_view.jsp";
	}

}
