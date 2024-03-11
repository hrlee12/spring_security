package com.cos.security1.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final UserRepository userRepository;// 스프링 시큐리티가 해당주소를 낚아챔. - SecurityConfig 파일 생성 후 작동 안 함.
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({"", "/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : templates (prefix), .mustache (suffix) 생략 가능 ! 의존성 주입했기 때문에 기본으로 설정됨.
		return "index";
	}

	@GetMapping("/user")
	@ResponseBody
	public String user() {

		SecurityContext context = SecurityContextHolder.getContext();

		// 인증 객체를 얻습니다.
		Authentication authentication = context.getAuthentication();

		// 로그인한 사용자정보를 가진 객체를 얻습니다.
		// Principal principal = authentication.getPrincipal();

		// 사용자가  가진 모든 롤 정보를 얻습니다.
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		System.out.println("hi");
		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());
		}
			출처: https://offbyone.tistory.com/217 [쉬고 싶은 개발자:티스토리]
		return "user";
	}

	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "manager";
	}


	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user.toString());
		user.setRole("ROLE_USER");
		// 비밀번호가 암호화가 되어있어야 시큐리티로 로그인 가능
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);	// 회원가입
		return "redirect:/loginForm";
	}

	// secure는 한개만 걸 수 있고 preauthorize는 여러개 걸 수 있음.
	// @Secured("ROLE_ADMIN")

	@GetMapping("/data")
	public @ResponseBody String info() {
		return "개인정보";
	}

}
