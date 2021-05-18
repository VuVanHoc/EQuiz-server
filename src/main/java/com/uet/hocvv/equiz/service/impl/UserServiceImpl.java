package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.config.security.CustomUserDetails;
import com.uet.hocvv.equiz.domain.entity.Notification;
import com.uet.hocvv.equiz.domain.entity.Student;
import com.uet.hocvv.equiz.domain.entity.Teacher;
import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.enu.GenderType;
import com.uet.hocvv.equiz.domain.enu.UserType;
import com.uet.hocvv.equiz.domain.request.ChangePasswordRequest;
import com.uet.hocvv.equiz.domain.request.ForgotPasswordRequest;
import com.uet.hocvv.equiz.domain.request.SignUpRequest;
import com.uet.hocvv.equiz.domain.request.UpdateUserInfoRequest;
import com.uet.hocvv.equiz.domain.response.NotificationDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.domain.response.UserDTO;
import com.uet.hocvv.equiz.repository.NotificationRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
	@Autowired
	NotificationRepository notificationRepository;
	
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
	public User getByUsername(String username) throws Exception {
		return userRepository.findByUsername(username);
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
		user.setActive(true);
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
//		Map<String, Object> params = new HashMap<>();
//		params.put("param1", signUpRequest.getFullname());
//		params.put("param2", prefixUrl + "/verifyEmail?id=" + user.getId());
//		emailService.sendEmail(signUpRequest.getUsername(), "Xác nhận đăng ký tài khoản", "ConfirmSignUp.html", params);
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
			throw new Exception(CommonMessage.USER_NOT_FOUND.name());
		}
		if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.get().getPassword())) {
			throw new Exception(CommonMessage.OLD_PASSWORD_NOT_CORRECT.name());
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
	
	@Override
	public UserDTO updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest) throws Exception {
		Optional<User> userOptional = userRepository.findById(updateUserInfoRequest.getUserId());
		if (!userOptional.isPresent()) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.name());
		}
		User user = userOptional.get();
		user.setFullName(updateUserInfoRequest.getFullName());
		user.setAvatar(updateUserInfoRequest.getAvatar());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (user.getUserType().equals(UserType.TEACHER)) {
			Teacher teacher = teacherRepository.findByUserId(user.getId());
			if (teacher == null) {
				throw new Exception(CommonMessage.USER_NOT_FOUND.name());
			}
			teacher.setAddress(updateUserInfoRequest.getAddress());
			teacher.setPhone(updateUserInfoRequest.getPhone());
			if (updateUserInfoRequest.getGender() != null) {
				teacher.setGender(GenderType.valueOf(updateUserInfoRequest.getGender()));
			}
			teacher.setEmail(updateUserInfoRequest.getEmail());
			if (updateUserInfoRequest.getBirthday() != null) {
				teacher.setBirthDay(simpleDateFormat.parse(updateUserInfoRequest.getBirthday()));
				
			}
			teacherRepository.save(teacher);
		} else if (user.getUserType().equals(UserType.STUDENT)) {
			Student student = studentRepository.findByUserId(user.getId());
			if (student == null) {
				throw new Exception(CommonMessage.USER_NOT_FOUND.name());
			}
			if (updateUserInfoRequest.getGender() != null) {
				student.setGender(GenderType.valueOf(updateUserInfoRequest.getGender()));
			}
			student.setAddress(updateUserInfoRequest.getAddress());
			student.setPhone(updateUserInfoRequest.getPhone());
			student.setEmail(updateUserInfoRequest.getEmail());
			if (updateUserInfoRequest.getBirthday() != null) {
				student.setBirthday(simpleDateFormat.parse(updateUserInfoRequest.getBirthday()));
			}
			studentRepository.save(student);
		}
		userRepository.save(user);
		
		return modelMapper.map(user, UserDTO.class);
	}
	
	@Override
	public UserDTO getUserInfo(String userId) throws Exception {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.name());
		}
		User user = userOptional.get();
		return populateUserInfo(user.getUsername());
	}
	
	@Override
	public ResponseListDTO getNotifications(String userId, int pageIndex, int pageSize) {
		
		Sort sort = Sort.by("createdDate").descending();
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		
		List<Notification> notifications = notificationRepository.findByUserIdAndDeletedIsFalse(userId, pageable);
		if (notifications == null) {
			return new ResponseListDTO(null, 0);
		}
		List<NotificationDTO> notificationDTOS = new ArrayList<>();
		for (Notification notification : notifications) {
			notificationDTOS.add(modelMapper.map(notification, NotificationDTO.class));
		}
		int total = notificationRepository.countByUserIdAndDeletedIsFalse(userId);
		return new ResponseListDTO(notificationDTOS, total);
	}
	
	@Override
	public String updateListNotification(String userId, String notificationId, Boolean updateAll, Boolean read) throws Exception {
		if (updateAll) {
			List<Notification> notifications = notificationRepository.findByUserIdAndReadIsFalse(userId);
			if (notifications == null) {
				return CommonMessage.SUCCESS.name();
			}
			for (Notification notification : notifications) {
				notification.setRead(true);
				notification.setUpdatedDate(new Date());
			}
			notificationRepository.saveAll(notifications);
		} else {
			Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
			if(!optionalNotification.isPresent()) {
				throw new Exception(CommonMessage.NOT_FOUND.name());
			}
			Notification notification = optionalNotification.get();
			notification.setRead(read);
			notification.setUpdatedDate(new Date());
			notificationRepository.save(notification);
		}
		return CommonMessage.SUCCESS.name();
	}
	
	@Override
	public String deleteNotification(String id) throws Exception {
		Optional<Notification> optionalNotification = notificationRepository.findById(id);
		if(!optionalNotification.isPresent()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		Notification notification = optionalNotification.get();
		notification.setDeleted(true);
		notification.setUpdatedDate(new Date());
		notificationRepository.save(notification);
		return CommonMessage.SUCCESS.name();
	}
	
	public UserDTO populateUserInfo(String username) throws Exception {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new Exception(CommonMessage.USER_NOT_FOUND.toString());
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setFullName(user.getFullName());
		userDTO.setUserType(user.getUserType().toString());
		userDTO.setUserId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getUsername());
		switch (user.getUserType()) {
			case TEACHER:
				Teacher teacher = teacherRepository.findByUserId(user.getId());
				userDTO.setAddress(teacher.getAddress());
				userDTO.setAvatar(user.getAvatar());
				userDTO.setBirthday(teacher.getBirthDay());
				userDTO.setDefaultColor(user.getDefaultColor());
//				userDTO.setEmail(teacher.getEmail());
				userDTO.setPhone(teacher.getPhone());
				if (teacher.getGender() != null) {
					userDTO.setGender(teacher.getGender().toString());
				}
				userDTO.setWorkplace(teacher.getWorkplace());
				userDTO.setPrefixJob(teacher.getPrefixJob());
				userDTO.setTeacherId(teacher.getId());
				break;
			case STUDENT:
				Student student = studentRepository.findByUserId(user.getId());
				userDTO.setAddress(student.getAddress());
				userDTO.setAvatar(user.getAvatar());
				userDTO.setBirthday(student.getBirthday());
				userDTO.setDefaultColor(user.getDefaultColor());
//				userDTO.setEmail(student.getEmail());
				userDTO.setPhone(student.getPhone());
				if (student.getGender() != null) {
					userDTO.setGender(student.getGender().toString());
				}
				userDTO.setStudentId(student.getId());
				break;
		}
		return userDTO;
	}
	
	private User convertDtoToEntity(SignUpRequest signUpRequest) {
		return modelMapper.map(signUpRequest, User.class);
	}
	
	
}
