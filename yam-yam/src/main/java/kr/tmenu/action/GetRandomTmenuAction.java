package kr.tmenu.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.tmenu.dao.TmenuDAO;
import kr.tmenu.vo.TmenuVO;
import kr.util.StringUtil;

public class GetRandomTmenuAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // DAO 호출
        TmenuDAO dao = TmenuDAO.getInstance();
        //리스트에 저장 
        List<TmenuVO> menuList = dao.getAllTmenus(); 
       
       //array리스트에 저장 해서 6개 랜덤 뽑기
        List<TmenuVO> randomItems = new ArrayList<TmenuVO>();
        if (menuList != null && menuList.size() > 6) {
            // 6개 랜덤 선택
        	Collections.shuffle(menuList);
            randomItems = menuList.subList(0, 6);
        } else if(menuList != null) {
        	//6개 이하ㅇ인 경우 전부 선택
        	randomItems  = menuList;
        }

        // AJAX 요청인지 확인
        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

        if (isAjax) {
            // AJAX 요청인 경우 JSON 응답 반환
            List<Map<String, String>> responseData = new ArrayList<>();
            for (TmenuVO item : randomItems) {
                Map<String, String> menuData = new HashMap<>();
                menuData.put("tm_num", String.valueOf(item.getTm_num())); // 항목 번호
                menuData.put("tm_menu", item.getTm_menu()); // 항목 이름
                responseData.add(menuData);
            }

            // 응답에 성공 여부와 데이터 추가
            Map<String, Object> mapAjax = new HashMap<>();
            mapAjax.put("result", "success");
            mapAjax.put("data", responseData);

            return StringUtil.parseJSON(request, mapAjax);
        }

        // 일반 요청인 경우 JSP로 데이터 전달
        request.setAttribute("menuList", menuList); // 전체 메뉴 리스트 전달
        request.setAttribute("randomItems", randomItems); // 선택된 랜덤 항목 전달
        request.setAttribute("result", randomItems != null ? "success" : "error");
        request.setAttribute("message", randomItems == null ? "항목이 없습니다." : null);

        // 요청 URI에 따라 다른 JSP 반환
        String uri = request.getRequestURI();
        if (uri.endsWith("roulette.do")) {
            return "tmenu/tmenu_roulette.jsp"; // 룰렛 JSP 반환
        } else if (uri.endsWith("draw.do")) {
            return "tmenu/tmenu_draw.jsp"; // 제비뽑기 JSP 반환
        } else {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }
}
