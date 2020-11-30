package com.uet.hocvv.equiz;

import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EquizApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(EquizApplication.class, args);
	}
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		// Khi chương trình chạy
		// Insert vào csdl một user.
		User user = new User();
		user.setUsername("hocvv");
		user.setPassword(passwordEncoder.encode("123123"));
		userRepository.save(user);
		System.out.println(user);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
