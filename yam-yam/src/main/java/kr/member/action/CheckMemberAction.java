package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.StringUtil;

public class CheckMemberAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String mem_id = request.getParameter("mem_id");
	    String mem_nickname = request.getParameter("mem_nickname");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(mem_id,mem_nickname);
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(mem_id!=null && mem_nickname==null) {
			if(member == null) {//아이디 미중복
				mapAjax.put("result", "idNotFound");
			}else {//아이디 중복
				mapAjax.put("result", "idDuplicated");
			}
		}else if(mem_id==null && mem_nickname!=null) {
			if(member == null) {//닉네임 미중복
				mapAjax.put("result", "nicknameNotFound");
			}else {//닉네임 중복
				mapAjax.put("result", "nicknameDuplicated");
			}
		}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
