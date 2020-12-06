package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class SignUpRequest {
	
	private String username;
	private String password;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private String userType;
	private String gender;
}
