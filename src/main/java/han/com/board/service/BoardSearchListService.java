package han.com.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import han.com.board.dao.BoardDAO;

@Service
public class BoardSearchListService {
	public Model execute(Model model) {
		String keyword = null;
		Map map = model.asMap();
		keyword = (String) map.get("keyword");
		String keyfield = null;
		keyfield = (String) map.get("keyfield");
		BoardDAO boardDAO = new BoardDAO();
		List<?> searchBoardlist = new ArrayList<Object>();
		int page = 1;
		int limit = 10;
		if (map.get("page") != null) {
			page = (int) map.get("page");
		}
		int searchlistcount = boardDAO.getSearchListCount(keyword, keyfield);
		searchBoardlist = boardDAO.getSearchList(keyword, keyfield, page, limit);
		int maxpage = (int) ((double) searchlistcount / limit + 0.95);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage) {
			endpage = maxpage;
		}
		model.addAttribute("page", page);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("startpage", startpage);
		model.addAttribute("endpage", endpage);
		model.addAttribute("searchlistcount", searchlistcount);
		model.addAttribute("searchBoardlist", searchBoardlist);
		model.addAttribute("keyword", keyword);
		model.addAttribute("keyfield", keyfield);
		return model;
	}
}
