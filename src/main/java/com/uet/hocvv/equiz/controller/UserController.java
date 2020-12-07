package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("getAllUser")
	public ResponseEntity<?> getUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}
	
	@GetMapping("getUserInfo")
	public ResponseEntity<Object> getUserInfo() {
		return ResponseEntity.ok(null);
	}
}
