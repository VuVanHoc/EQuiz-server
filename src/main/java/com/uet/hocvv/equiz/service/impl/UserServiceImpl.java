package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.MessageError;
import com.uet.hocvv.equiz.config.security.CustomUserDetails;
import com.uet.hocvv.equiz.domain.entity.Student;
import com.uet.hocvv.equiz.domain.entity.Teacher;
import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.enu.UserType;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;
import com.uet.hocvv.equiz.repository.StudentRepository;
import com.uet.hocvv.equiz.repository.TeacherRepository;
import com.uet.hocvv.equiz.repository.UserRepository;
import com.uet.hocvv.equiz.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	EmailServiceImpl emailService;
	
	
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
	public User signUp(SignUpRequest signUpRequest) throws Exception {
		if (StringUtils.isEmpty(signUpRequest.getUsername())) {
			throw new Exception(MessageError.USERNAME_IS_REQUIRED.toString());
		}
		
		User user = userRepository.findByUsername(signUpRequest.getUsername());
		if (user != null) {
			throw new Exception(MessageError.USERNAME_EXISTED.toString());
		}
		user = new User();
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		if (UserType.STUDENT.toString().equals(signUpRequest.getUserType())) {
			user.setUserType(UserType.STUDENT);
			userRepository.save(user);
			
			Student student = new Student();
			student.setUserId(user.getId());
			student.setFirstName(signUpRequest.getFirstName());
			student.setLastName(signUpRequest.getLastName());
			student.setGender(signUpRequest.getGender());
			studentRepository.save(student);
		} else {
			user.setUserType((UserType.TEACHER));
			userRepository.save(user);
			
			Teacher teacher = new Teacher();
			teacher.setUserId(user.getId());
			teacher.setFirstName(signUpRequest.getFirstName());
			teacher.setLastName(signUpRequest.getLastName());
			teacher.setGender(signUpRequest.getGender());
			teacherRepository.save(teacher);
		}
		return user;
	}
	
	@Override
	public User getUserById(String id) {
		return userRepository.findById(id).get();
	}
	
	
	@Override
	public User createUser(SignUpRequest signUpRequest) {
		User newUser = convertDtoToEntity(signUpRequest);
		return userRepository.save(newUser);
	}
	
	@Override
	public User updateUser(User user) {
		return null;
	}
	
	@Override
	public List<User> getAllUser() {
		return null;
	}
	
	@Override
	public String deleteUser(String userId) {
		return null;
	}
	
	private User convertDtoToEntity(SignUpRequest signUpRequest) {
		return modelMapper.map(signUpRequest, User.class);
	}
	
	
}
