package kr.tmenu.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.tmenu.dao.TmenuDAO;
import kr.tmenu.vo.TmenuVO;
import kr.util.PagingUtil;

public class TmenuListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth!=9) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}
		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum ="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		TmenuDAO dao = TmenuDAO.getInstance();
		int count = dao.getTmenuCount(keyfield, keyword);
		
		//페이지 처리
		//keyfield, keyword, currentPage, rowCount, pageCount, url 순으로 진행
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"tmenuList.do");
		List<TmenuVO> list = null;
		if(count>0) {
			list = dao.getListTmenu(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//jsp 경로 반환
		return "tmenu/tmenu_list.jsp";
	}

}
