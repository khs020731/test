package my.board.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.java.Log;
import my.board.domain.Board;
import my.board.persistence.BoardRepository;

@Controller
@RequestMapping("/board")
@Log
public class BoardController {

	@Autowired
	BoardRepository repo;

	@GetMapping("/list")
	public String boardView(@PageableDefault Pageable pageable, Model model,Authentication authentication) {

		Page<Board> boardList = repo.getBoardList(pageable);
		model.addAttribute("boardList", boardList);
		model.addAttribute("username", authentication);
		return "/board/list";
	}

	@GetMapping("/register")
	public void registerget(@ModelAttribute("vo") Board vo) {

	}

	@PostMapping("/register")
	public String registerpost(@ModelAttribute("vo") Board vo) {

		repo.save(vo);

		return "redirect:/board/list";
	}

	@GetMapping("/view")
	public void view(@RequestParam("bno") Long bno, Model model, Principal principal) {

		repo.findById(bno).ifPresent(board -> model.addAttribute("vo", board));
		model.addAttribute("loginusername", principal.getName());

	}

	@GetMapping("/modify")
	public void modifyget(@RequestParam("bno") Long bno, Model model) {

		repo.findById(bno).ifPresent(board -> model.addAttribute("vo", board));
	}

	@PostMapping("/modify")
	public String modifypost(@RequestParam("bno") Long bno, HttpServletRequest request) {

		repo.findById(bno).ifPresent(bo -> {
			bo.setTitle(request.getParameter("title"));
			bo.setContent(request.getParameter("content"));
			repo.save(bo);
		});

		return "redirect:/board/list";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("bno") Long bno) {

		repo.deleteById(bno);

		return "redirect:/board/list";
	}

	@GetMapping("/myboardlist")
	public void myboardlist(Model model,@RequestParam("uid") String uid) {

		List<Board> list=repo.findAll();
		
		model.addAttribute("userlist", list);
		model.addAttribute("uid", uid);
	}

}
