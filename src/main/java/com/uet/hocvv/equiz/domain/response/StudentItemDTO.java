package com.uet.hocvv.equiz.domain.response;

import lombok.Data;

import java.util.Date;

@Data
public class StudentItemDTO {
	
	private String id;
	private String fullName;
	private Date birthday;
	private String avatar;
	private String email;
	private boolean favorite;
	
	
}
