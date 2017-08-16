package han.com.board.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import han.com.board.dao.BoardDAO;
import han.com.board.model.BoardDTO;

@Service
public class BoardModifyService {
	public Model execute(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		boolean result = false;
		String realFolder = "";
		String saveFolder = "./boardUpload";
		int fileSize = 5 * 1024 * 1024;
		realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
		MultipartRequest multipartRequest = null;
		multipartRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		int num = Integer.parseInt(multipartRequest.getParameter("num"));
		boolean usercheck = boardDAO.isBoardWriter(num, multipartRequest.getParameter("pass"));
		if (usercheck == false) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정할 권한이 없습니다.');");
			out.println("location.href='./BoardMain';");
			out.println("</script>");
			out.close();
			return null;
		}
		boardDTO.setNum(num);
		boardDTO.setName(multipartRequest.getParameter("name"));
		boardDTO.setSubject(multipartRequest.getParameter("subject"));
		boardDTO.setContent(multipartRequest.getParameter("content"));
		if (multipartRequest.getFilesystemName((String) multipartRequest.getFileNames().nextElement()) != null) {
			boardDTO.setAttached_file(
					multipartRequest.getFilesystemName((String) multipartRequest.getFileNames().nextElement()));
		} else {
			boardDTO.setAttached_file("null");
		}
		boardDTO.setOld_file(multipartRequest.getParameter("old_file"));
		boardDAO.boardModify(boardDTO);
		model.addAttribute("num", num);
		return model;
	}
}
