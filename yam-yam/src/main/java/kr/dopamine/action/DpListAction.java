package kr.dopamine.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.dopamine.dao.DopamineDAO;
import kr.dopamine.vo.DopamineVO;
import kr.util.PagingUtil;

public class DpListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String dp_category = request.getParameter("dp_category");
		
		DopamineDAO dao = DopamineDAO.getInstance();
		int count = dao.getDpCount(keyfield, keyword, dp_category);
		System.out.println(count);
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"dpList.do");
		
		List<DopamineVO> list = null;
		if(count > 0) {
			list = dao.getListDp(page.getStartRow(),page.getEndRow(),keyfield,keyword,dp_category);
		}
		System.out.println(list);
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//JSP 경로 반환
		return "dopamine/dpList.jsp";
	}

}
