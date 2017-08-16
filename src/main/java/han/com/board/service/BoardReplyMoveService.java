package han.com.board.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import han.com.board.dao.BoardDAO;
import han.com.board.model.BoardDTO;

@Service
public class BoardReplyMoveService {
	public Model execute(Model model) {
		BoardDAO boardDAO = new BoardDAO( );
		BoardDTO boardDTO = new BoardDTO( );
		Map map = model.asMap();
		int num = (int) map.get("num");
		boardDTO.setNum(num);
		boardDTO = boardDAO.getDetail(boardDTO);
		model.addAttribute("boardDTO", boardDTO);
		return model;
	}
}
