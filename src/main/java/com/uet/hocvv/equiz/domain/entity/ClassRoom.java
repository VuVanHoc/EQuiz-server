package com.uet.hocvv.equiz.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "class_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoom extends BaseEntity {

//	id of teacher created classroom
	private String createdBy;
	private String name;
	private String description;
	private String password;
	private boolean isPublic;
	
}
