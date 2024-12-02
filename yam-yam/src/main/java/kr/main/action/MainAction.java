package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.Ctotal_BoardDAO;
import kr.board.vo.Ctotal_BoardVO;
import kr.controller.Action;
import kr.dopamine.dao.DopamineDAO;
import kr.dopamine.vo.DopamineVO;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.PagingUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * //게시판 HttpSession session = request.getSession(); Long user_num =
		 * (Long)session.getAttribute("user_num");
		 * 
		 * if (user_num == null) { // 로그인이 되지 않은 경우 return
		 * "redirect:/member/loginForm.do"; }
		 * 
		 * String pageNum = request.getParameter("page_num"); if (pageNum ==
		 * null)pageNum = "1";
		 * 
		 * String keyfield = request.getParameter("keyfield"); String keyword =
		 * request.getParameter("keyword");
		 * 
		 * Ctotal_BoardDAO dao = Ctotal_BoardDAO.getInstance(); int count =
		 * dao.getCtotalCount(keyfield, keyword, user_num);
		 * 
		 * PagingUtil page = new PagingUtil(keyfield, keyword,
		 * Integer.parseInt(pageNum),count,20,10,"list.do"); List<Ctotal_BoardVO> list =
		 * null; if (count > 0) { list = dao.getListctotalBoard(page.getStartRow(),
		 * page.getEndRow(), keyfield, keyword, user_num);
		 * 
		 * } request.setAttribute("count", count); request.setAttribute("list", list);
		 * request.setAttribute("page", page.getPage());
		 */
		
		
		//메뉴추천
		FplaceDAO dao = FplaceDAO.getInstance();
		int count = dao.getMainFplaceCount();
		
		List<FplaceVO> list = null;
		if(count > 0) {
			list = dao.getMainListFplace();
		}
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		
		// 커뮤니티
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		Ctotal_BoardDAO dao3 = Ctotal_BoardDAO.getInstance();
		int count3 = dao3.getCtotalCount(keyfield, keyword);
		List<Ctotal_BoardVO> list3 = null;
		if (count > 0) {
			list3 = dao3.getListctotalBoard(count, count3, keyfield, keyword);
		}
		request.setAttribute("count3", count3);
		request.setAttribute("list3", list3);
		
		
		//도파민 
		DopamineDAO ddao = DopamineDAO.getInstance();
        
        // 축제 글 개수 가져오기
        int count2 = ddao.getDpCount(null, null, "3"); // 카테고리 3번 (축제 데이터)
        
        // 축제 글 목록 가져오기
        List<DopamineVO> list2 = null;
        if (count > 0) {
            list2 = ddao.getListDpMain(1, 5, null, null, "3"); // 상위 5개의 축제 데이터 가져오기
        }

        // JSP에 전달할 데이터 설정
        request.setAttribute("list2", list2);
        request.setAttribute("count2", count2);

   
	
		
		return "main/main.jsp";
	}
}
