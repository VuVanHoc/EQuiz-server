package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class UpdateUserInfoRequest {
	
	private String userId;
	private String fullName;
	private String phone;
	private String birthday;
	private String gender;
	private String avatar;
	private String address;
	private String email;
	
}
