package kr.fplace.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");

		long fp_num = Long.parseLong(request.getParameter("fp_num"));
		FplaceDAO dao = FplaceDAO.getInstance();
		FplaceVO db_fplace = dao.getFplace(fp_num);


		if (user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		if (user_auth != 9 && db_fplace.getMem_num() != user_num) {
			return "common/notice.jsp";


		}else if(user_num!=null && (user_num == db_fplace.getMem_num() || user_auth == 9)) {
			//로그인된 경우
			//전송된 데이터 인코딩 처리

			FplaceVO fplace = new FplaceVO();
			fplace.setFp_num(fp_num);
			fplace.setFp_name(request.getParameter("fp_name"));
			fplace.setFp_phone(request.getParameter("fp_phone"));
			fplace.setFp_time(request.getParameter("fp_time"));
			fplace.setFp_loc(request.getParameter("fp_loc"));
			fplace.setFp_storeimg(FileUtil.uploadFile(request,"fp_storeimg"));

			String[] fp_filter1 = request.getParameterValues("fp_filter1");
			String tmp_fp_filter1 = "";
			for(int i=0;i<fp_filter1.length;i++) {
				if (i>0) tmp_fp_filter1+=",";
				tmp_fp_filter1 += fp_filter1[i];
			}
			fplace.setFp_filter1(tmp_fp_filter1);

			String[] fp_filter2 = request.getParameterValues("fp_filter2");
			String tmp_fp_filter2 = "";
			for(int i=0;i<fp_filter2.length;i++) {
				if (i>0) tmp_fp_filter2 += ",";
				tmp_fp_filter2 += fp_filter2[i];
			}
			fplace.setFp_filter2(tmp_fp_filter2);

			String[] fp_filter3 = request.getParameterValues("fp_filter3");
			String tmp_fp_filter3="";
			for(int i=0;i<fp_filter3.length;i++) {
				if (i>0) tmp_fp_filter3 += ",";
				tmp_fp_filter3 += fp_filter3[i];
			}
			fplace.setFp_filter3(tmp_fp_filter3);

			dao.updateFplace(fplace);
			if (request.getPart("fp_storeimg").getSize() == 0) {
				fplace.setFp_storeimg(db_fplace.getFp_storeimg()); // 기존 파일 유지
			} else {
				String newImage = FileUtil.uploadFile(request, "fp_storeimg");
				fplace.setFp_storeimg(newImage); // 새로운 파일 저장
			}

		}
		return "redirect:/fplace/detail.do?fp_num="+fp_num;
	}

}
