package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.service.impl.EmailServiceImpl;
import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	
	final
	UserServiceImpl userService;
	final
	EmailServiceImpl emailService;
	
	public UserController(UserServiceImpl userService, EmailServiceImpl emailService) {
		this.userService = userService;
		this.emailService = emailService;
	}
	
	
	@GetMapping("getAllUser")
	public ResponseEntity<?> getUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}
	
	@GetMapping("getUserInfo")
	public ResponseEntity<Object> getUserInfo() {
		return ResponseEntity.ok(null);
	}
}
