package kr.fplace.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long fp_num = Long.parseLong(request.getParameter("fp_num"));
		
		FplaceDAO dao = FplaceDAO.getInstance();
		//북마크 수? 별점 ? 
		FplaceVO fplace = dao.getFplace(fp_num);
		
		//html 태그를 허용하지 않음 필요한가?
		//fplace.setFp_name(StringUtil.useNoHtml(fplace.getFp_name()));
		
		request.setAttribute("fplace", fplace);
		
		
		
		return "fplace/detail.jsp";
	}

}
