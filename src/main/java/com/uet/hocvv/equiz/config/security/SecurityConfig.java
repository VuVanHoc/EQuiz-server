package com.uet.hocvv.equiz.config.security;

import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import com.uet.hocvv.equiz.utils.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserServiceImpl userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userService) // Cung cáp userservice cho spring security
				.passwordEncoder(passwordEncoder); // cung cấp password encoder
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and().csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/auth/login/**",
						"/api/auth/signup/**",
						"/verifyEmail",
						"/api/user/forgotPassword",
						"/verifyForgotPassword/**"
				)
				.permitAll()
				.antMatchers("/v3/api-docs",
						"/v2/api-docs",
						"/swagger-resources",
						"/swagger-resources/**",
						"/configuration/ui",
						"/configuration/security",
						"/swagger-ui/**",
						"/webjars/**",
						"/resources/**").permitAll()
				.anyRequest().authenticated();
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
