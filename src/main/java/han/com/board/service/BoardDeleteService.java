package han.com.board.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import han.com.board.dao.BoardDAO;
import han.com.board.model.BoardDTO;

@Service
public class BoardDeleteService {
	public Model execute(Model model, HttpServletResponse response) throws IOException {
		boolean usercheck = false;
		Map map = model.asMap();
		int num = (int) map.get("num");
		BoardDAO boardDAO = new BoardDAO();
		usercheck = boardDAO.isBoardWriter(num, ""+map.get("pass"));
		if (usercheck == false) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제할 권한이 없습니다.');");
			out.println("location.href='./BoardMain';");
			out.println("</script>");
			out.close();
			return null;
		}
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setNum(num);
		boardDAO.boardDelete(num);
		return model;
	}
}
