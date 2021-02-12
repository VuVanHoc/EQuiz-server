package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
	
	private String email;
	private String newPassword;
	private String cfNewPassword;
}
