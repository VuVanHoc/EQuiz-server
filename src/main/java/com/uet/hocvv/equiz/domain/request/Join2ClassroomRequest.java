package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class Join2ClassroomRequest {
	
	private String studentId;
	private String classCode;
	private String password;
	
}
