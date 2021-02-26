package my.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.board.domain.Member;
import my.board.persistence.MemberRepository;

@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	PasswordEncoder pwEncoder;
	
	@Autowired
	MemberRepository repo;
	
	@GetMapping("/join")
	public void join() {
		
	}
	
	@PostMapping("/join")
	public String joinPost(@ModelAttribute("member") Member member) {
			
			String encryptPw=pwEncoder.encode(member.getUpw());
			member.setUpw(encryptPw);
			repo.save(member);
			
		return "redirect:/login";
	}
	
	@GetMapping("mydata")
	public void mydataget() {
		
	}
	
	@PostMapping("/mydata")
	public String mydatapost(@ModelAttribute("member") Member member) {
		
		String encryptPw=pwEncoder.encode(member.getUpw());
		member.setUpw(encryptPw);
		repo.save(member);
		
		
		return "redirect:/login";
	}
}
