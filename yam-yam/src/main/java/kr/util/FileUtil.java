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
		String filename = part.getSubmittedFileName();
		if (!filename.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			filename = uuid.toString() + filename.substring(filename.lastIndexOf("."));
			part.write(upload+"/"+filename);
		}
		return filename;
	}
	public static void removeFile(HttpServletRequest requset, String filename) {
		if (filename!=null) {
			String upload = requset.getServletContext().getRealPath(UPLOAD_PATH);
			File file = new File(upload+"/"+filename);
			if (file.exists()) file.delete();
		}
	}
}
