package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.ChangePasswordRequest;
import com.uet.hocvv.equiz.domain.request.ForgotPasswordRequest;
import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
	
	@PostMapping("changePassword")
	public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws Exception {
		userService.changePassword(changePasswordRequest);
		RestBody restBody = RestBody.success(null);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("forgotPassword")
	public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws Exception {
		String result = userService.forgotPassword(forgotPasswordRequest);
		RestBody restBody = RestBody.success(result);
		return ResponseEntity.ok(restBody);
	}
}
