package kr.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class FileUtil {
	public static final String UPLOAD_PATH = "/upload";
	public static String uploadFile(HttpServletRequest requset, String param) throws IOException, ServletException {

		String upload = requset.getServletContext().getRealPath(UPLOAD_PATH);
		Part part = requset.getPart(param);
		String fp_storeimg = part.getSubmittedFileName();
		if (!fp_storeimg.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			fp_storeimg = uuid.toString() + fp_storeimg.substring(fp_storeimg.lastIndexOf("."));
			part.write(upload+"/"+fp_storeimg);
		}
		return fp_storeimg;
	}
	public static void removeFile(HttpServletRequest requset, String fp_storeimg) {
		if (fp_storeimg!=null) {
			String upload = requset.getServletContext().getRealPath(UPLOAD_PATH);
			File file = new File(upload+"/"+fp_storeimg);
			if (file.exists()) file.delete();
		}
	}
}
