package com.uet.hocvv.equiz.domain.response;

import lombok.Data;

@Data
public class ClassroomDTO {
//	Info of classroom
	private String id;
	private String name;
	private String code;
	private String password;
	private int totalStudent;
	private String description;
	private long createdDate;
	private boolean isPrivate;
	
//	Info of responsible
	private String responsibleId;
	private String responsibleEmail;
	private String responsibleName;
	private String responsiblePhone;
	private String responsibleAvatar;
	
	
}
