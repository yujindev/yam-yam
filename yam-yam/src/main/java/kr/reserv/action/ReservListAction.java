package kr.reserv.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;
import kr.util.StringUtil;

public class ReservListAction implements Action{
   
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Long mem_num = (Long)session.getAttribute("user_num");
		
		ReservDAO dao = ReservDAO.getInstance();
		int rs_now = dao.getReservCount(mem_num);
		List<ReservVO> reserv = null;
		
		
		if(rs_now > 0) {
			reserv = dao.getReservList(mem_num);
		}else{
			reserv = Collections.emptyList();
		}
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("rs_now", rs_now);
		mapAjax.put("reserv", reserv);
		
		//로그인한 사람이 예약자인지 체크하기 위해서 로그인한 회원번호 전송
		mapAjax.put("mem_num", mem_num);
		mapAjax.put("result", "success");
		
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}
	
}
