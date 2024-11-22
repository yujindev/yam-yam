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
        String fp_filter1="",fp_filter2="",fp_filter3="",addKey="";
        if(filter1!=null) {
        	for(int i=0;i<filter1.length;i++) {
            	if(i>0) {
            		fp_filter1 += ",";
            	}
            	fp_filter1 += filter1[i];
            	addKey += "&fp_filter1="+filter1[i];
           }
        }
        if(filter2!=null) {
        	for(int i=0;i<filter2.length;i++) {
            	if(i>0) {
            		fp_filter2 += ",";
            	}
            	fp_filter2 += filter2[i];
            	addKey += "&fp_filter2="+filter2[i];
           }
        }
        if(filter3!=null) {
        	for(int i=0;i<filter3.length;i++) {
            	if(i>0) fp_filter3 += ",";
            	fp_filter3 += filter3[i];
            	addKey += "&fp_filter3="+filter3[i];
           }
        }
       
		FplaceDAO dao = FplaceDAO.getInstance();
		int count = dao.getFplaceCount(fp_filter1, fp_filter2, fp_filter3);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(null, null, Integer.parseInt(pageNum), count, 1, 10, "list.do",addKey);
		List<FplaceVO> list = null;
		if(count > 0) {
			list = dao.getListFplace(page.getStartRow(), page.getEndRow(), fp_filter1, fp_filter2, fp_filter3);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());

		
		return "fplace/list.jsp";
	}

}
