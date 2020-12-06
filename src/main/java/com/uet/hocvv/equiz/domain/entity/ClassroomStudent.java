package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "classroom_student")
public class ClassroomStudent extends BaseEntity {
	
	private String classroomId;
	private String studentId;
}
