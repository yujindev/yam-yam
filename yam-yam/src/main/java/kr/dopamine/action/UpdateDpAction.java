package kr.dopamine.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.dopamine.dao.DopamineDAO;
import kr.dopamine.vo.DopamineVO;
import kr.util.FileUtil;

public class UpdateDpAction implements Action{

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
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		long dp_num = Long.parseLong(request.getParameter("dp_num"));

		DopamineDAO dao = DopamineDAO.getInstance();
		DopamineVO db_dp = dao.getDp(dp_num);

		DopamineVO dp = new DopamineVO();
		dp.setDp_num(dp_num);
		dp.setDp_title(request.getParameter("dp_title"));
		dp.setDp_content(request.getParameter("dp_content"));
		dp.setDp_file(FileUtil.uploadFile(request, "dp_file"));

		dao.modifyDp(dp);
		
		if(dp.getDp_file()!=null && !"".equals(dp.getDp_file())) {
		//새 파일로 교체할 때 원래 파일 제거
		FileUtil.removeFile(request, db_dp.getDp_file());
	}

		return "redirect:/dopamine/detail.do?dp_num="+dp_num;
	}

}
