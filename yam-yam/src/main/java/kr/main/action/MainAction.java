package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FplaceDAO dao = FplaceDAO.getInstance();
		int count = dao.getMainFplaceCount();
		
		List<FplaceVO> list = null;
		if(count > 0) {
			list = dao.getMainListFplace();
		}
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		return "main/main.jsp";
	}
}
