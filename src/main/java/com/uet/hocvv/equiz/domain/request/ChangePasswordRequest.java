package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	private String userId;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;
}
