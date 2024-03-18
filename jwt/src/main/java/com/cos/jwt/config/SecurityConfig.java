package com.cos.jwt.config;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		// 세션을 쓰지 않겠음
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			// 인증 없을 땐 @CrossOrigin
			// 인증(로그인) 필요할 땐 시큐리티 필터에 등록
			.addFilter(corsFilter)
			// 폼로그인 X. 기본적인 http 로그인 방식 X
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();
	}
}
