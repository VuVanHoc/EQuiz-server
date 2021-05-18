package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.ChangePasswordRequest;
import com.uet.hocvv.equiz.domain.request.ForgotPasswordRequest;
import com.uet.hocvv.equiz.domain.request.UpdateUserInfoRequest;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.domain.response.UserDTO;
import com.uet.hocvv.equiz.repository.NotificationRepository;
import com.uet.hocvv.equiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	NotificationRepository notificationRepository;
	
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
	
	@GetMapping("getNotifications")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Object> getNotifications(@RequestParam("userId") String userId,
	                                               @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
	                                               @RequestParam(value = "pageSize", defaultValue = "1000") int pageSize) throws Exception {
		ResponseListDTO responseListDTO = userService.getNotifications(userId, pageIndex, pageSize);
		RestBody restBody = RestBody.success(responseListDTO);
		return ResponseEntity.ok(restBody);
	}
	@GetMapping("getNumberUnreadNotifications")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Object> getNumberUnreadNotifications(@RequestParam("userId") String userId) throws Exception {
		int total = notificationRepository.countByUserIdAndReadIsFalseAndDeletedIsFalse(userId);
		RestBody restBody = RestBody.success(total);
		return ResponseEntity.ok(restBody);
	}
	@PostMapping("updateNotifications")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Object> updateNotifications(@RequestParam("userId") String userId,
	                                                  @RequestParam("notificationId") String notificationId,
	                                                  @RequestParam("updateAll") Boolean updateAll,
	                                                  @RequestParam("read") Boolean read) throws Exception {
		String result = userService.updateListNotification(userId, notificationId, updateAll, read);
		RestBody restBody = RestBody.success(result);
		return ResponseEntity.ok(restBody);
	}
	
	@DeleteMapping("deleteNotification/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Object> deleteNotification(@PathVariable("id") String id) throws Exception {
		String result = userService.deleteNotification(id);
		RestBody restBody = RestBody.success(result);
		return ResponseEntity.ok(restBody);
	}
}
