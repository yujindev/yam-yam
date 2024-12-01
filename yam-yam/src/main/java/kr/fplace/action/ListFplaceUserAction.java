package kr.fplace.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.fplace.dao.FplaceDAO;
import kr.fplace.vo.FplaceVO;
import kr.util.PagingUtil;

public class ListFplaceUserAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pageNum = request.getParameter("pageNum");
        if (pageNum == null) pageNum = "1";

        String keyfield = request.getParameter("keyfield");
        String keyword = request.getParameter("keyword");

        FplaceDAO dao = FplaceDAO.getInstance();
        int count = dao.getFplaceCountByUser(keyfield, keyword);

        PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 10, 10, "list.do");
        List<FplaceVO> list = null;
        if (count > 0) {
            list = dao.getListFplaceByUser(page.getStartRow(), page.getEndRow(), keyfield, keyword);
        }

        request.setAttribute("count", count);
        request.setAttribute("list", list);
        request.setAttribute("page", page.getPage());

        return "fplace/user_fplaceList.jsp";
    }

	
}
