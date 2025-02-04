package kr.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class StringUtil {

	public static String parseJSON(HttpServletRequest request, Map<String,?> map) 
			throws JsonGenerationException,JsonMappingException, IOException{

		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(map);

		request.setAttribute("ajaxData", ajaxData);

		return "common/ajax_view.jsp";

	}
	//HTML 태그를 허용하면서 줄바꿈
    public static String useBrHtml(String str) {
        if(str == null) return null;
    
        return str.replaceAll("\r\n", "<br>").replaceAll("\r", "<br>").replaceAll("\n","<br>");
    }
    //HTML 태그를 허용하지 않으면서 줄바꿈
    public static String useBrNoHtml(String str) {
        if(str == null) return null;
        return str.replaceAll("<", "&lt;").replaceAll(">","&gt;").replaceAll("\r\n", "<br>").replaceAll("\r", "<br>").replaceAll("\n","<br>");
    }
    //HTML 태그를 허용하지 않음
        public static String useNoHtml(String str) {
            if(str == null) return null;
            return str.replaceAll("<", "&lt;").replaceAll(">","&gt;");
        }
        
    //큰 따옴표 처리
    public static String parseQuot(String str) {
        if(str == null) return null;
        return str.replaceAll("\"", "&quot;");
    }
}