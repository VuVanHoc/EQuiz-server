package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.ChangePasswordRequest;
import com.uet.hocvv.equiz.domain.request.ForgotPasswordRequest;
import com.uet.hocvv.equiz.domain.request.UpdateUserInfoRequest;
import com.uet.hocvv.equiz.domain.response.UserDTO;
import com.uet.hocvv.equiz.service.UserService;
import com.uet.hocvv.equiz.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
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
		RestBody restBody = RestBody.success("SUCCESS");
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("forgotPassword")
	public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws Exception {
		String result = userService.forgotPassword(forgotPasswordRequest);
		RestBody restBody = RestBody.success(null);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("updateUserInfo")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> updateUserInfo(@RequestBody UpdateUserInfoRequest updateUserInfoRequest) throws Exception {
		UserDTO userDTO = userService.updateUserInfo(updateUserInfoRequest);
		RestBody restBody = RestBody.success(userDTO);
		return ResponseEntity.ok(restBody);
	}
	
	@GetMapping("getInfo/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Object> getUserInfo(@PathVariable("id") String userId) throws Exception {
		UserDTO userDTO = userService.getUserInfo(userId);
		RestBody restBody = RestBody.success(userDTO);
		return ResponseEntity.ok(restBody);
	}
}
