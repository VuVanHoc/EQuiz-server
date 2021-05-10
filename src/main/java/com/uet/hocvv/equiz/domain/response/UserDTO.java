package com.uet.hocvv.equiz.domain.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
	
	private String userId;
	private String teacherId;
	private String studentId;
	private String fullName;
	private String userType;
	private String defaultColor;
	private String avatar;
	private String email;
	private String phone;
	private String address;
	private Date birthday;
	private String gender;
	private String prefixJob;
	private String workplace;
	private String username;
	
	
}
