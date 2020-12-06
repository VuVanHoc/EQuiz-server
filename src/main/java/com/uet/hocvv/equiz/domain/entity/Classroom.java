package com.uet.hocvv.equiz.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "class_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classroom extends BaseEntity {
	
	private String name;
	private String code;
	private String description;
	private String password;
	private boolean isPublic;
	private String createdBy;
	private String responsible;
	private String link;
	private Double ratingNumber;
	
}
