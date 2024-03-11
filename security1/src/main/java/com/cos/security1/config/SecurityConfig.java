package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  // secured 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			// 경로에 대한 접근권한 설정
			// 인증(로그인)된 사용자만 접근 가능.
			// authentication : 인증, authorization : 권한부여
			.antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			// 스프링 부트는 기본적으로 모든 경로에 대해서 login을 해야 접근할 수 있게 막아둠.
			// 이 설정을 통해서 로그인을 하지 않아도 접근가능하게 설정.
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm")
			// /login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다.
			// 컨트롤러에 /login 안 만들어도 됨
			.loginProcessingUrl("/login")
			// 로그인이 성공되면 호출할 주소
			.defaultSuccessUrl("/");



	}
}
