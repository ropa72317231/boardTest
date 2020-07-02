package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.model.Board;
import com.example.board.model.User;
import com.example.board.repository.BoardRepository;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/write")
	public String boardWrite() {
		return "board/write";
	}

	@PostMapping("/write")
	@ResponseBody
	public String boardWritePost(@ModelAttribute Board board) {
		System.out.println(board);
		// 로그인 여부 확인(세션의 값 확인)
		User user = (User) session.getAttribute("user_info");
		if(user == null) {	//로그인 안됨
			return "0";
		} else {		//로그인 
		String userId = user.getEmail();
		board.setUserId(userId);
		boardRepository.save(board);
	}
		return "1";
}
	
	@GetMapping("/signout")
	public String signout() {
		session.removeAttribute("user_info");		// 지정된 세션값만 삭제
		session.invalidate();						// 세션의 모든 정보 삭제
		return "redirect:/";
	}
	
	@GetMapping("/signin")		// login
	public String signin() {
		return "signin";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id) {
		boardRepository.deleteById(id);
		return "redirect:/board/";
	}
	
	@GetMapping("/update/{id}")	// <-- id라는 명칭은 개발자가 임의로 만들어낸 명칭
	public String update(@PathVariable("id") long id, Model model) {
		System.out.println("!@@@@@" + id);
		Optional<Board> opt = boardRepository.findById(id);
		Board board = opt.get();
		System.out.println(board);
		model.addAttribute("board", board);
		
		return "board/update";
	}
	
	
	@PostMapping("/update")
	@ResponseBody
	public String boardUpdatePost(@ModelAttribute Board board) {
		System.out.println(board);
		// board의 키(id)가 중복이면 -> 수정 기능 수행
		// board의 키(id)가 중복이 아니면 -> 입력 기능 수행
		boardRepository.save(board); // 입력 또는 수정
		

		
//		// 로그인 여부 확인(세션의 값 확인)
//		User user = (User) session.getAttribute("user_info");
//		if(user == null) {	//로그인 안됨
//			return "0";
//		} else {		//로그인 
//		String userId = user.getEmail();
//		board.setUserId(userId);
//		boardRepository.save(board);
//	}
		return "1";
}

	
	
	
	@GetMapping("/{id}")	// <-- id라는 명칭은 개발자가 임의로 만들어낸 명칭
	public String detail(@PathVariable("id") long id, Model model) {
		System.out.println("!@@@@@" + id);
		Optional<Board> opt = boardRepository.findById(id);
		Board board = opt.get();
		System.out.println(board);
		model.addAttribute("board", board);
		
		return "board/detail";
	}
	
	@GetMapping("/")
	public String board(Model model, 
			@RequestParam(name = "page", defaultValue="1") int page) {
		int startPage = (page-1) /10 *10 +1;
		int endPage = startPage + 9;
		
		PageRequest req = PageRequest.of(page -1, 10, Sort.by(Direction.DESC, "id"));
		
	Page<Board> result = boardRepository.findAll(req);
	int totalPage = result.getTotalPages();
	if(endPage > totalPage) {
		endPage = totalPage;
	}
	List<Board> list = result.getContent();
	model.addAttribute("list", list);
	model.addAttribute("startPage", startPage);
	model.addAttribute("endPage", endPage);
	model.addAttribute("totalPage", totalPage);
	model.addAttribute("page", page);
	return "board/list";
	}
}




