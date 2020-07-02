package com.example.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.model.User;
import com.example.board.repository.UserRepository;

@Controller
public class UserController {
	private static final RequestMethod[] RequestMethod = null;
	@Autowired
	UserRepository userRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	@ResponseBody
	public String signupPost(@ModelAttribute User user) {
		// 중복 아이디 가입 불가를 위해서 가입여부 확인
		User findUser = userRepository.findByEmail(user.getEmail());
		System.out.println("@@@@" + findUser);
		if (findUser == null) {
			userRepository.save(user);
		} else {
			return "0";
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

	@PostMapping("/signin")
	public String signinPost(@ModelAttribute User user) {
		User dbUser = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if (dbUser != null) {
			session.setAttribute("user_info", dbUser);
			return "redirect:/";
		}
		return "redirect:/";
	}
	
/* 강사님 풀이(1번 풀이 - history.back() 활용)
	@PostMapping("/signup")
	public String signupPost(@ModelAttribute User user) {
		// 중복 아이디 가입 불가를 위해서 가입여부 확인
		User findUser = userRepository.findByEmail(user.getEmail());
		System.out.println("@@@@" + findUser);
		if (findUser == null) {
			userRepository.save(user);
		} else {
			return "signup_error";
		}
		return "redirect:/";
	}
*/

	
/* 강사님 풀이(2번 풀이 - 입력된 파라미터 활용)
	@PostMapping("/signup")
	public String signupPost(@ModelAttribute User user,
	Model model) {
		// 중복 아이디 가입 불가를 위해서 가입여부 확인
		User findUser = userRepository.findByEmail(user.getEmail());
		System.out.println("@@@@" + findUser);
		if (findUser == null) {
			userRepository.save(user);
		} else {
			model.addAttribute("email", "user.getEmail());
			model.addAttribute("email", "user.getName());
			return "signup_error2";
		}
		return "redirect:/";
	}
*/
	
	
	
	
	
}
