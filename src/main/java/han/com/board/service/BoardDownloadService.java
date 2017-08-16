package han.com.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class BoardDownloadService {
	public void execute(Model model, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		String fileName = request.getParameter("attached_file");
		String savePath = "./boardUpload";
		ServletContext context = request.getSession().getServletContext();
		String downPath = context.getRealPath(savePath);
		String fielPath = downPath + "\\" + fileName;
		byte b[] = new byte[4096];
		new File(fielPath);
		FileInputStream fileInputStream = new FileInputStream(fielPath);
		String sEncoding = null;
		try {
			boolean MSIE = (request.getHeader("user-agent").indexOf("MSIE") != -1)
					|| (request.getHeader("user-agent").indexOf("Trident") != -1);
			String downType = request.getSession().getServletContext().getMimeType(fielPath);
			if (downType == null)
				downType = "application/octet-stream";
			response.setContentType(downType);
			if (MSIE) {
				sEncoding = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1").replaceAll("\\+", "%20");
			} else {
				sEncoding = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + sEncoding + "\"");
			ServletOutputStream servletOutputStream = response.getOutputStream();
			int nunRead;
			while ((nunRead = fileInputStream.read(b, 0, b.length)) != -1) {
				servletOutputStream.write(b, 0, nunRead);
			}
			servletOutputStream.flush();
			servletOutputStream.close();
			fileInputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
