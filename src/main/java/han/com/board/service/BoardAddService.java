package han.com.board.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import han.com.board.dao.BoardDAO;
import han.com.board.model.BoardDTO;

@Service
public class BoardAddService {
	public Model execute(Model model, HttpServletRequest request) throws IOException {
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		String realFolder = "";
		String saveFolder = "./boardUpload";
		int fileSize = 5 * 1024 * 1024;
		realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
		boolean result = false;
		MultipartRequest multipartRequest = null;
		multipartRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		boardDTO.setName(multipartRequest.getParameter("name"));
		boardDTO.setPass(multipartRequest.getParameter("pass"));
		boardDTO.setSubject(multipartRequest.getParameter("subject"));
		boardDTO.setContent(multipartRequest.getParameter("content"));
		if (multipartRequest.getFilesystemName((String) multipartRequest.getFileNames().nextElement()) != null) {
			boardDTO.setAttached_file(
					multipartRequest.getFilesystemName((String) multipartRequest.getFileNames().nextElement()));
		} else {
			boardDTO.setAttached_file("null");
		}
		int num = boardDAO.boardInsert(boardDTO);
		model.addAttribute("num", num);
		return model;
	}
}
