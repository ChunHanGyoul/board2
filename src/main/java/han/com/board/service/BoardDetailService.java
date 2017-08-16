package han.com.board.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import han.com.board.dao.BoardDAO;
import han.com.board.model.BoardDTO;

@Service
public class BoardDetailService {
	public Model execute(Model model) {
		BoardDAO boardDAO = new BoardDAO( );
		BoardDTO boardDTO = new BoardDTO( );
		Map map = model.asMap();
		int num = (int) map.get("num");
		boardDTO.setNum(num);
		System.out.println(num);
		boardDAO.setReadCountUpdate(boardDTO);
		System.out.println(boardDTO);
		boardDTO = boardDAO.getDetail(boardDTO);
		System.out.println("���⼭ �������� 3");
		if (boardDTO == null) {
			System.out.println("�󼼺��� ����");
		}
		System.out.println("�󼼺��� ����");
		model.addAttribute("boardDTO", boardDTO);
		return model;
	}
}
