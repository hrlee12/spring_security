package com.cos.security1.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 시큐리티 session을 만들어줌. (Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User 오브젝트 타입 => UserDetails 타입 객체

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

import lombok.extern.slf4j.Slf4j;

// Security Session 안에 Authentication이라는 객체가 들어가 있음.
// Authentication 객체 안에 UserDetails객체(PrincipalDetails)가 있음.
@Slf4j
public class PrincipalDetails implements UserDetails {

	// private User user; // 콤포지션
	//
	// public PrincipalDetails(User user) {
	// 	this.user = user;
	// }
	//
	// // 해당 유저의 권한을 리턴하는 곳
	// @Override
	// public Collection<? extends GrantedAuthority> getAuthorities() {
	// 	Collection<GrantedAuthority> collect = new ArrayList<>();
	// 	collect.add(new GrantedAuthority(){
	// 		@Override
	// 		public String getAuthority() {
	//
	// 			return user.getRole();
	// 		}
	// 	});
	// 	return null;
	// }
	//
	// @Override
	// public String getPassword() {
	// 	return user.getPassword();
	// }
	//
	// @Override
	// public String getUsername() {
	// 	return user.getUsername();
	// }
	//
	// @Override
	// public boolean isAccountNonExpired() {
	// 	return true;
	// }
	//
	// @Override
	// public boolean isAccountNonLocked() {
	// 	return true;
	// }
	//
	// // 비밀번호가 만료가 안 됐니?
	// @Override
	// public boolean isCredentialsNonExpired() {
	// 	return true;
	// }
	//
	// @Override
	// public boolean isEnabled() {
	//
	// 	// 1년동안 회원이 로그인을 안하면 휴면계정으로 전환
	// 	// 현재시간 - user.getLoginTime => 1년 초과하면 return false;
	// 	return true;
	// }

	private static final long serialVersionUID = 1L;
	private User user;


	// 일반 시큐리티 로그인시 사용
	public PrincipalDetails(User user) {
		this.user = user;
	}


	public User getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return user.getRole();});
		return collet;
	}
}

