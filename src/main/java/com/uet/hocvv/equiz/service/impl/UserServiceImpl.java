package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.config.security.CustomUserDetails;
import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;
import com.uet.hocvv.equiz.repository.UserRepository;
import com.uet.hocvv.equiz.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	
	final
	UserRepository userRepository;
	
	final
	ModelMapper modelMapper;
	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
//	using for Spring security
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException(s);
		}
		return new CustomUserDetails(user);
	}
	
	@Override
	public RestBody getUserById(String id) {
		return RestBody.success(userRepository.findById(id));
	}
	
	@Override
	public RestBody getUserByEmail(String email) {
		return RestBody.success(userRepository.findByEmail(email));
		
	}
	
	@Override
	public RestBody createUser(SignUpRequest signUpRequest) {
		
		User newUser = convertDtoToEntity(signUpRequest);
		
		return RestBody.success(userRepository.save(newUser));
		
		
	}
	
	@Override
	public RestBody updateUser(User user) {
		return null;
	}
	
	@Override
	public RestBody getAllUser() {
		return null;
	}
	
	@Override
	public RestBody deleteUser(String userId) {
		return null;
	}
	
	private User convertDtoToEntity(SignUpRequest signUpRequest) {
		return modelMapper.map(signUpRequest, User.class);
	}
	
	
}
