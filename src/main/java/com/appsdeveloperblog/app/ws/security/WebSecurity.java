package com.appsdeveloperblog.app.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloperblog.app.ws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(
			UserService userService
			, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService=userService;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
		http.csrf()
			.disable()
				.authorizeRequests()
					.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL).permitAll()
					.anyRequest().authenticated()
				.and().addFilter(getAuthenticateFilter());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth);
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		
	}
	
	public AuthenticateFilter getAuthenticateFilter() throws Exception{
		final AuthenticateFilter filter=new AuthenticateFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;		
	}
	
}