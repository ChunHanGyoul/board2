package han.com.board.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import han.com.board.service.BoardAddService;
import han.com.board.service.BoardDeleteService;
import han.com.board.service.BoardDetailService;
import han.com.board.service.BoardDownloadService;
import han.com.board.service.BoardListService;
import han.com.board.service.BoardModifyDetailService;
import han.com.board.service.BoardModifyService;
import han.com.board.service.BoardReplyMoveService;
import han.com.board.service.BoardReplyService;
import han.com.board.service.BoardSearchListService;


@Controller
public class BoardController {
	@Qualifier("boardListService")
	@Autowired
	private BoardListService boardListService;
	@Autowired
	private BoardAddService boardAddService;
	@Autowired
	private BoardDetailService boardDetailService;
	@Autowired
	private BoardDownloadService boardDownloadService; 
	@Autowired
	private BoardModifyDetailService boardModifyDetailService;
	@Autowired
	private BoardModifyService boardModifyService;
	@Autowired
	private BoardReplyMoveService boardReplyMoveService;
	@Autowired
	private BoardReplyService boardReplyService;
	@Autowired
	private BoardSearchListService boardSearchListService;
	@Autowired
	private BoardDeleteService boardDeleteService;
	
	@RequestMapping("/BoardMain")
	public String boardMain(Model model, HttpServletRequest request) {
		if(request.getParameter("page") != null) {
			model.addAttribute("page", Integer.parseInt(request.getParameter("page")));
		}
		model = boardListService.execute(model);
		return "/board/board_list";
	}
	
	@RequestMapping(value = "/BoardWrite", method = RequestMethod.GET)
	public String boardWirteForm() {
		return "/board/board_write";
	}
	
	@RequestMapping(value = "/BoardWrite", method = RequestMethod.POST)
	public String boardWirte(Model model, HttpServletRequest request) throws IOException {
		boardAddService.execute(model, request);
		return boardDetail(model, request);
	}
	
	@RequestMapping(value = "/BoardDetail", method = RequestMethod.GET)
	public String boardDetail(Model model, HttpServletRequest request) {
		if(request.getParameter("num") != null) {
			model.addAttribute("num", Integer.parseInt(request.getParameter("num")));
		}
		model = boardDetailService.execute(model);
		return "/board/board_view";
	}
	
	@RequestMapping(value = "/BoardDownload", method = RequestMethod.GET)
	public void boardDownload(Model model, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		boardDownloadService.execute(model, request, response);
	}
	
	@RequestMapping(value = "/BoardModify", method = RequestMethod.GET)
	public String boardModifyDetail(Model model, HttpServletRequest request){
		if(request.getParameter("num") != null) {
			model.addAttribute("num", Integer.parseInt(request.getParameter("num")));
		}
		model = boardModifyDetailService.execute(model);
		return "/board/board_modify";
	}
	
	@RequestMapping(value = "/BoardModify", method = RequestMethod.POST)
	public String boardModify(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		boardModifyService.execute(model, request, response);
		return boardDetail(model, request);
	}
	
	@RequestMapping(value = "/BoardReply", method = RequestMethod.GET)
	public String boardReplyDetail(Model model, HttpServletRequest request){
		if(request.getParameter("num") != null) {
			model.addAttribute("num", Integer.parseInt(request.getParameter("num")));
		}
		model = boardReplyMoveService.execute(model);
		return "/board/board_reply";
	}
	
	@RequestMapping(value = "/BoardReply", method = RequestMethod.POST)
	public String boardReply(Model model, HttpServletRequest request) throws IOException{
		int num = boardReplyService.execute(model, request);
		model.addAttribute("num", num);
		return boardDetail(model, request);
	}
	
	@RequestMapping(value = "/BoardSearchList", method = RequestMethod.POST)
	public String boardSearchList(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		Map map = model.asMap();
		model.addAttribute("keyfield", request.getParameter("keyfield"));
		model.addAttribute("keyword", request.getParameter("keyword"));
		if(request.getParameter("page") != null) {
			model.addAttribute("page", Integer.parseInt(request.getParameter("page")));
		}
		model = boardSearchListService.execute(model);
		return "/board/board_search_list";
	}
	
	@RequestMapping(value = "/BoardDelete", method = RequestMethod.GET)
	public String boardDeleteForm(Model model, HttpServletRequest request){
		if(request.getParameter("num") != null) {
			model.addAttribute("num", Integer.parseInt(request.getParameter("num")));
		}
		return "/board/board_delete";
	}
	
	@RequestMapping(value = "/BoardDelete", method = RequestMethod.POST)
	public String boardDelete(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getParameter("num") != null) {
			model.addAttribute("num", Integer.parseInt(request.getParameter("num")));
		}
		if(request.getParameter("pass") != null) {
			model.addAttribute("pass", Integer.parseInt(request.getParameter("pass")));
		}
		model = boardDeleteService.execute(model, response);
		return boardMain(model, request);
	}
	// 들리트 하고 다실행해보고 안되는부분고치고 소스 스프링화하기
	
}