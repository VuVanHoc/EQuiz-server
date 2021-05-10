package com.uet.hocvv.equiz;

import com.uet.hocvv.equiz.service.WordService;
import com.uet.hocvv.equiz.utils.JwtAuthenticationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EquizApplication implements CommandLineRunner {
	
	@Autowired
	WordService wordService;
	
	public static void main(String[] args) {
		SpringApplication.run(EquizApplication.class, args);
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	@Override
	public void run(String... args) throws Exception {
//		wordService.initDataFromFile();
	}
}
