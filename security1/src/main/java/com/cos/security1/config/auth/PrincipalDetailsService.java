package com.cos.security1.config.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 시큐리티 설정에서 loginProcessingUrl("/login")
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는
// loadUserByUsername 함수가 실행.
@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	// 시큐리티 session > Authentication > UserDetails
	// loadUserByUserName에서 return된 UserDetail은 Authentication 안에 들어감.
	// 그리고 Authentication이 시큐리티 session 내부로 들어감.
	// 시큐리티 session(내부 Authentication(내부 UserDetail))

	// input의 name과 같은 이름의 매개변수
	// 다르면 매개변수에 안 들어감.

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}else {
			return new PrincipalDetails(user);
		}

	}


}