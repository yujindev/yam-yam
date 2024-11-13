package kr.fplace.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth !=9 ) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}
		
		//로그인된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		FplaceVO fplace = new FplaceVO();
		fplace.setFp_name(request.getParameter("fp_name"));
		fplace.setFp_phone(request.getParameter("fp_phone"));
		fplace.setFp_time(request.getParameter("fp_time"));
		fplace.setFp_loc(request.getParameter("fp_loc"));
		fplace.setFp_storeimg(FileUtil.uploadFile(request,"fp_storeimg"));
		fplace.setFp_menuimg1(FileUtil.uploadFile(request, "fp_menuimg1"));
		fplace.setFp_menuimg2(FileUtil.uploadFile(request, "fp_menuimg2"));
		fplace.setFp_menuimg3(FileUtil.uploadFile(request, "fp_menuimg3"));
		fplace.setFp_menuimg4(FileUtil.uploadFile(request,"fp_menuimg4"));
		fplace.setFp_filter1(request.getParameter("fp_fliter1"));
		fplace.setFp_filter2(request.getParameter("fp_fliter2"));
		fplace.setFp_filter3(request.getParameter("fp_fliter3"));
		fplace.setMem_num(user_num);// 작성자 회원번호
		
		FplaceDAO dao = FplaceDAO.getInstance();
		dao.insertFplace(fplace);
		
		request.setAttribute("notice_msg","식당 정보 등록 완료!");
		request.setAttribute("notice_url", request.getContextPath()+"/fplace/list.do");
		
		return "common/alert_view.jsp";
	}

}
