package com.uet.hocvv.equiz.service;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;

public interface UserService {
	
	
	RestBody getUserById(String id);
	
	RestBody getUserByEmail(String email);
	
	RestBody createUser(SignUpRequest createUserRequest);
	
	RestBody updateUser(User user);
	
	RestBody getAllUser();
	
	RestBody deleteUser(String userId);
	
}
