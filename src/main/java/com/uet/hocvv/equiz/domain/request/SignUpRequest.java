package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class SignUpRequest {
	
	private String username;
	private String password;
	private String confirmPassword;
	private Long birthday;
	private Boolean gender;
//	private
	
}
