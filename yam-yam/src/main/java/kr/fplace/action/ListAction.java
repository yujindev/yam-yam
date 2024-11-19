package kr.fplace.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum =request.getParameter("pageNum");
		if(pageNum == null) pageNum ="1";
		
        String[] filter1 = request.getParameterValues("fp_filter1");
        String[] filter2 = request.getParameterValues("fp_filter2");
        String[] filter3 = request.getParameterValues("fp_filter3");
		
		FplaceDAO dao = FplaceDAO.getInstance();
		int count = dao.getFplaceCount(filter1, filter2, filter3);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(null, null, Integer.parseInt(pageNum), count, 6, 10, "list.do");
		List<FplaceVO> list = null;
		if(count > 0) {
			list = dao.getListFplace(page.getStartRow(), page.getEndRow(), filter1, filter2, filter3);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());

		
		return "fplace/list.jsp";
	}

}
