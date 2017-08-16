package han.com.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import han.com.board.dao.BoardDAO;
@Service
public class BoardListService {
	public Model execute(Model model){
		BoardDAO boardDAO = new BoardDAO();
		List<?> boardList = new ArrayList<Object>();
		int page = 1;
		int limit = 10;
		Map modelMap  = model.asMap();
		if (modelMap.get("page") != null) {
			page = (Integer) modelMap.get("page");
		}
		int listcount = boardDAO.getListCount();
		boardList = boardDAO.getBoardList(page, limit);
		int maxpage = (int) ((double) listcount / limit + 0.95);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage) {
			endpage = maxpage;
		}

		model.addAttribute("page", page);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("startpage",startpage);
		model.addAttribute("endpage", endpage);
		model.addAttribute("listcount",listcount);
		model.addAttribute("boardList", boardList);
		return model;
	}
}
