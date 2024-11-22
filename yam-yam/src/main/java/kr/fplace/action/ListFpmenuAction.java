package kr.fplace.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FpMenuVO;
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class ListFpmenuAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum =request.getParameter("pageNum");
		if(pageNum == null) pageNum ="1";
		
		
		String rowCount = request.getParameter("rowCount");
		if(rowCount==null) rowCount ="3";
		
		long fp_num = Long.parseLong(request.getParameter("fp_num"));
		
		FplaceDAO dao = FplaceDAO.getInstance();
		int count = dao.getFpMenuCount(fp_num);
		
		//currentPage, count, rowCount
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		
		List<FpMenuVO> list = null;
		
		if(count>0) {
			list = dao.getListFpMenu(page.getStartRow(),page.getEndRow(),fp_num);
		}else {
			list = Collections.emptyList();
		}
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		mapAjax.put("user_num", user_num);
		mapAjax.put("user_auth", user_auth);
		
		
		return StringUtil.parseJSON(request, mapAjax);
	}

}
