package kr.ctalk_board.action;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.Ctalk_Re_BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class ListReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("page_num");
		if (pageNum == null)pageNum = "1";
		
		String rowCount = request.getParameter("rowCount");
		if(rowCount==null) rowCount = "10";	
		
		long ctalk_num = Long.parseLong(request.getParameter("ctalk_num"));
		
		CTALK_BoardDAO dao = CTALK_BoardDAO.getInstance();
		int count = dao.getReplyctalkCount(ctalk_num);
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,Integer.parseInt(rowCount));
		List<Ctalk_Re_BoardVO> list = null;
		
		if (count > 0) {
			list = dao.getListReplyBoard(page.getStartRow(), page.getEndRow(),ctalk_num);	
		} else {
			list = Collections.emptyList();
		}
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		mapAjax.put("user_num",user_num);

		return StringUtil.parseJSON(request, mapAjax);
	}

}
