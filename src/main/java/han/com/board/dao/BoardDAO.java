package han.com.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import han.com.board.model.BoardDTO;

@Repository
public class BoardDAO {
	SqlSessionTemplate template;
	
	public void setTemplate(SqlSessionTemplate template) {
		this.template = template;
	}
	
	public void makeTemplate() {
		ApplicationContext context = new ClassPathXmlApplicationContext("config/root-context.xml");
		template = (SqlSessionTemplate) context.getBean("sqlSessionTemplate");
	}
	
	public int getListCount() {
		makeTemplate();
		return template.selectOne("getListCount");
	}

	public List<BoardDTO> getBoardList(int page, int limit) {
		makeTemplate();
		int startrow = (page - 1) * 10 + 1;
		int endrow = startrow + limit - 1;
		Map map = new HashMap();
		map.put("startrow", startrow);
		map.put("endrow", endrow);
		return template.selectList("getBoardList", map);
	}

	public int boardInsert(BoardDTO boardDTO) {
		makeTemplate();
		template.insert("boardInsert", boardDTO);
		return template.selectOne("getMaxCount");
	}

	public BoardDTO getDetail(BoardDTO boardDTO) {
		makeTemplate();
		return template.selectOne("getDetail", boardDTO);
	}

	public void setReadCountUpdate(BoardDTO boardDTO) {
		makeTemplate();
		template.update("setReadCountUpdate", boardDTO);
	}

	public int boardModify(BoardDTO boardDTO) {
		makeTemplate();
		int result = template.update("boardModify", boardDTO);
		return result;
	}

	public boolean isBoardWriter(int num, String pass) {
		makeTemplate();
		Map map = new HashMap();
		map.put("num", num);
		map.put("pass", pass);
		BoardDTO searchBoardDTO = template.selectOne("isBoardWriter", map);
		if (searchBoardDTO == null) {
			return false;
		}
		return true;
	}

	public int boardReply(BoardDTO boardDTO) {
		makeTemplate();
		int num = template.selectOne("getMaxNum");
		num++;
		template.update("boardReplyUpdate", boardDTO);
		boardDTO.setAnswer_seq(boardDTO.getAnswer_seq() + 1);
		boardDTO.setAnswer_lev(boardDTO.getAnswer_lev() + 1);
		boardDTO.setNum(num);
		template.insert("boardReply", boardDTO);
		return template.selectOne("getMaxCount");
	}

	public List<BoardDTO> getSearchList(String keyword, String keyfield, int page, int limit) {
		makeTemplate();
		String searchCall = "";
		System.out.println("2keyword : " + keyword);
		System.out.println("2keyFile : " + keyfield);
		System.out.println("2page : " + page);
		System.out.println("2limit : " + limit);
		if (!"".equals(keyword)) {
			if ("all".equals(keyfield)) {
				searchCall = "(subject like '%' || '" + keyword + "' || '%' ) or ( name like '%' || '" + keyword
						+ "' || '%') or ( content like '%' || '" + keyword + "' || '%')";
			} else if ("subject".equals(keyfield)) {
				searchCall = " subject like '%' || '" + keyword + "' || '%'";
			} else if ("name".equals(keyfield)) {
				searchCall = " name like '%' || '" + keyword + "' || '%'";
			} else if ("content".equals(keyfield)) {
				searchCall = " content like '%' || '" + keyword + "' || '%'";
			}
		}
		int startrow = (page - 1) * 10 + 1;
		int endrow = startrow + limit - 1;
		Map map = new HashMap();
		map.put("searchCall", searchCall);
		map.put("startrow", startrow);
		map.put("endrow", endrow);
		return template.selectList("getSearchList", map);
	}

	public int getSearchListCount(String keyword, String keyfield) {
		makeTemplate();
		String searchCall = "";
		if (!"".equals(keyword)) {
			if ("all".equals(keyfield)) {
				searchCall = "(subject like '%' || '" + keyword + "' || '%' ) or ( name like '%' || '" + keyword
						+ "' || '%') or ( content like '%' || '" + keyword + "' || '%')";
			} else if ("subject".equals(keyfield)) {
				searchCall = " subject like '%' || '" + keyword + "' || '%'";
			} else if ("name".equals(keyfield)) {
				searchCall = " name like '%' || '" + keyword + "' || '%'";
			} else if ("content".equals(keyfield)) {
				searchCall = " content like '%' || '" + keyword + "' || '%'";
			}
		}
		Map map = new HashMap();
		map.put("value", searchCall);
		System.out.println(map.get("value"));
		int count = template.selectOne("getSearchListCount", map);
		return count;
	}

	public void boardDelete(int num) {
		makeTemplate();
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setNum(num);
		template.selectOne("boardDelete", boardDTO);
	}
}
