package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class CreateClassroomRequest {
	
	private String id;
	private String name;
	private String classroomType;
	private String password;
	private String description;
//	Id of user create classroom
	private String responsibleId;
	
}
