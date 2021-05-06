package com.uet.hocvv.equiz.service;

import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.request.ChangePasswordRequest;
import com.uet.hocvv.equiz.domain.request.ForgotPasswordRequest;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;
import com.uet.hocvv.equiz.domain.request.UpdateUserInfoRequest;
import com.uet.hocvv.equiz.domain.response.UserDTO;

import java.util.List;

public interface UserService {
	
	User getByUsername(String username) throws Exception;
	
	User signUp(SignUpRequest signUpRequest) throws Exception;
	
	User verifyAccount(String username) throws Exception;
	
	User getUserById(String id);
	
	User createUser(SignUpRequest createUserRequest);
	
	User updateUser(User user);
	
	List<User> getAllUser();
	
	String deleteUser(String userId);
	
	void changePassword(ChangePasswordRequest changePasswordRequest) throws Exception;
	
	String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception;
	
	void verifyForgotPassword(String email, String pass, Long expriedTime) throws Exception;
	
	UserDTO updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest) throws Exception;
	
	UserDTO getUserInfo(String userId) throws Exception;
}
