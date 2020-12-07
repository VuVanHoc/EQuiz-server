package com.uet.hocvv.equiz.service;

import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;

import java.util.List;

public interface UserService {
	
	User signUp(SignUpRequest signUpRequest) throws Exception;
	
	User verifyAccount(String username) throws Exception;
	
	User getUserById(String id);
	
	User createUser(SignUpRequest createUserRequest);
	
	User updateUser(User user);
	
	List<User> getAllUser();
	
	String deleteUser(String userId);
	
}
