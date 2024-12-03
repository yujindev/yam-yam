package kr.cmenu_board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.CMENU_BoardDAO;
import kr.board.dao.CTALK_BoardDAO;
import kr.board.vo.CMENU_BoardVO;
import kr.board.vo.CTALK_BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else {
			request.setCharacterEncoding("utf-8");
			long cmenu_num = Long.parseLong(request.getParameter("cmenu_num"));

			CMENU_BoardDAO dao = CMENU_BoardDAO.getInstance();
			CMENU_BoardVO db_board = dao.getBoard(cmenu_num);
			if (user_num != db_board.getMem_num()) {
				// 로그인한 회원번호와 작성자 회원번호 불일치
				mapAjax.put("result", "wrongAccess");
			} else {
				dao.deleteFile(cmenu_num);
				//파일 삭제
				FileUtil.removeFile(request, 
						               db_board.getCmenu_filename());
				FileUtil.removeFile(request, 
			               				db_board.getCmenu_filename2());
				
				mapAjax.put("result", "success");
			}
		}
		//JSON 데이터로 변환

		return StringUtil.parseJSON(request, mapAjax);
	}

}
