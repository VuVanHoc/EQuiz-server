package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.config.security.CustomUserDetails;
import com.uet.hocvv.equiz.domain.entity.Student;
import com.uet.hocvv.equiz.domain.entity.Teacher;
import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.enu.UserType;
import com.uet.hocvv.equiz.domain.request.ChangePasswordRequest;
import com.uet.hocvv.equiz.domain.request.ForgotPasswordRequest;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;
import com.uet.hocvv.equiz.domain.response.UserDTO;
import com.uet.hocvv.equiz.repository.StudentRepository;
import com.uet.hocvv.equiz.repository.TeacherRepository;
import com.uet.hocvv.equiz.repository.UserRepository;
import com.uet.hocvv.equiz.service.UserService;
import com.uet.hocvv.equiz.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
	@Value("${prefix.url}")
	String prefixUrl;
	@Value("${default.password.user}")
	String defaultPassword;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
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
			throw new Exception(CommonMessage.USERNAME_IS_REQUIRED.toString());
		}
		
		User user = userRepository.findByUsername(signUpRequest.getUsername());
		if (user != null) {
			throw new Exception(CommonMessage.USERNAME_EXISTED.toString());
		}
		user = new User();
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setFullName(signUpRequest.getFullname());
		user.setDefaultColor(generateRandomColor());
		if (UserType.STUDENT.toString().equals(signUpRequest.getUserType())) {
			user.setUserType(UserType.STUDENT);
			userRepository.save(user);
			Student student = new Student();
			student.setUserId(user.getId());
			studentRepository.save(student);
		} else {
			user.setUserType((UserType.TEACHER));
			userRepository.save(user);
			Teacher teacher = new Teacher();
			teacher.setUserId(user.getId());
			teacherRepository.save(teacher);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("param1", signUpRequest.getFullname());
		params.put("param2", prefixUrl + "/verifyEmail?id=" + user.getId());
		emailService.sendEmail(signUpRequest.getUsername(), "Xác nhận đăng ký tài khoản", "ConfirmSignUp.html", params);
		return user;
	}
	
	private String generateRandomColor() {
		int random = new Random().nextInt(Constants.COLOR_TYPE.length);
		return Constants.COLOR_TYPE[random];
	}
	
	@Override
	public User verifyAccount(String id) throws Exception {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if (!optionalUser.isPresent()) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.toString());
		}
		User user = optionalUser.get();
		user.setActive(true);
		user.setUpdatedDate(new Date());
		return userRepository.save(user);
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
	
	@Override
	public void changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
		Optional<User> user = userRepository.findById(changePasswordRequest.getUserId());
		if (!user.isPresent()) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.toString());
		}
		user.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		userRepository.save(user.get());
	}
	
	@Override
	public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception {
		User user = userRepository.findByUsername(forgotPasswordRequest.getEmail());
		if (user == null) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.toString());
		}
		String password = forgotPasswordRequest.getNewPassword();
		long expriedTime = new Date().getTime() + Constants.LINK_EXPRIED;
		String url = prefixUrl + "/verifyForgotPassword?email=" + forgotPasswordRequest.getEmail() + "&pass=" + password + "&expriedTime=" + expriedTime;
		Map<String, Object> params = new HashMap<>();
		params.put("param1", user.getFullName());
		params.put("param2", url);
		emailService.sendEmail(user.getUsername(), "Quên mật khẩu", "ForgotPassword.html", params);
		return CommonMessage.SUCCESS.toString();
	}
	
	@Override
	public void verifyForgotPassword(String email, String pass, Long expriedTime) throws Exception {
		if (expriedTime < new Date().getTime()) {
			throw new Exception(CommonMessage.VERIFY_LINK_HAS_EXPRIED.toString());
		}
		User user = userRepository.findByUsername(email);
		if (user == null) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.toString());
		}
		user.setPassword(passwordEncoder.encode(pass));
		user.setUpdatedDate(new Date());
		userRepository.save(user);
	}
	
	public UserDTO populateUserInfo(String username) throws Exception {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.toString());
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setFullName(user.getFullName());
		userDTO.setUserType(user.getUserType().toString());
		switch (user.getUserType()) {
			case TEACHER:
				Teacher teacher = teacherRepository.findByUserId(user.getId());
				userDTO.setAddress(teacher.getAddress());
				userDTO.setAvatar(user.getAvatar());
				userDTO.setBirthday(teacher.getBirthDay());
				userDTO.setDefaultColor(user.getDefaultColor());
				userDTO.setEmail(teacher.getEmail());
				userDTO.setPhone(teacher.getPhone());
				if (teacher.getGender() != null) {
					userDTO.setGender(teacher.getGender().toString());
				}
				userDTO.setWorkplace(teacher.getWorkplace());
				userDTO.setPrefixJob(teacher.getPrefixJob());
				break;
			case STUDENT:
				Student student = studentRepository.findByUserId(user.getId());
				userDTO.setAddress(student.getAddress());
				userDTO.setAvatar(user.getAvatar());
				userDTO.setBirthday(student.getBirthday());
				userDTO.setDefaultColor(user.getDefaultColor());
				userDTO.setEmail(student.getEmail());
				userDTO.setPhone(student.getPhone());
				if (student.getGender() != null) {
					userDTO.setGender(student.getGender().toString());
				}
				break;
		}
		return userDTO;
	}
	
	private User convertDtoToEntity(SignUpRequest signUpRequest) {
		return modelMapper.map(signUpRequest, User.class);
	}
	
	
}
