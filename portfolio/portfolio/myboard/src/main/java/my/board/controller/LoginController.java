package my.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import my.board.persistence.MemberRepository;

@Controller
public class LoginController {
	
	@Autowired
	MemberRepository repo;

	@GetMapping("/login")
	public void login() {
		
		
	}
	
	
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		
	}
	
	@PostMapping("/logout")
	public void logout() {
		
	
	}
	
	

}
