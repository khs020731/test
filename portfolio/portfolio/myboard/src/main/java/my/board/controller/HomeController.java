package my.board.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;
import my.board.domain.Board;
import my.board.domain.Member;
import my.board.persistence.BoardRepository;
import my.board.persistence.MemberRepository;

@Controller
@Log
public class HomeController {
	
	@Autowired
	MemberRepository repo;
	
	@Autowired
	BoardRepository boardrepo;
	
	private static final String API_SERVER_HOST="https://dapi.kakao.com";
	private static final String SEARCH_PLACE_KEYWORD_PATH="/v3/search/book";

	@GetMapping("/")
	public String index() {
		
		return "index";
	}
	
	@GetMapping("/kakaomap")
	public String kakaomap() {
		
		return "kakaomap";
	}
	
	@GetMapping("/kakaobooksearch")
	public void kakaobooksearchget() {
	
	}
	
	@PostMapping("/kakaobooksearch")
	public ResponseEntity<String> kakaobooksearchpost(HttpServletRequest request,Model model) {
		String queryString="?target=title&query="+URLEncoder.encode(request.getParameter("bookname"));
		String restApiKey="restapikeynumber";
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		
		headers.add("Authorization","KakaoAK "+restApiKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=UTF-8");
		
		URI uri=URI.create(API_SERVER_HOST+SEARCH_PLACE_KEYWORD_PATH+queryString);
		RequestEntity<String> rq=new RequestEntity<String>(headers, HttpMethod.GET,uri);
		ResponseEntity<String> re=restTemplate.exchange(rq, String.class);
		
		return re;
	}
	
	@GetMapping("/adminpage")
	public void adminpageget(Model model) {
		List<Member> list=repo.findAll();
		model.addAttribute("userlist", list);
		
	
	}
	
	@PostMapping("/adminpage")
	public String adminpagepost(HttpServletRequest request) {
		String uid=request.getParameter("uid");
		repo.deleteById(uid);
		
		return "redirect:/adminpage";
	}
	
	@GetMapping("userboard")
	public void userboardget(@RequestParam("uid") String uid, Model model) {
		List<Board> list=boardrepo.findAll();
		
		model.addAttribute("userlist", list);
		model.addAttribute("uid", uid);
	}
	
	
	
}
