package kr.dopamine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.dopamine.dao.DopamineDAO;
import kr.dopamine.vo.DopamineVO;
import kr.util.FileUtil;

public class WriteDpAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		int user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth!=9) {//관리자가 아닌 경우
			return "common/not_admin.jsp";
		}
		
		//관리자인 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//자바빈(VO) 생성
		DopamineVO dopamine = new DopamineVO();
		dopamine.setDp_category(Integer.parseInt(request.getParameter("dp_category")));
		dopamine.setDp_title(request.getParameter("dp_title"));
		dopamine.setDp_content(request.getParameter("dp_content"));
		dopamine.setDp_file(FileUtil.uploadFile(request, "dp_file"));
	    dopamine.setMem_num(user_num);
		
		DopamineDAO dao = DopamineDAO.getInstance();
		dao.writeDp(dopamine);
		
		request.setAttribute("notice_msg", "글쓰기 완료!");
		request.setAttribute("notice_url", request.getContextPath()+"/dopamine/dpList.do");
		
		return "common/alert_view.jsp";
	}
	
	
	
}