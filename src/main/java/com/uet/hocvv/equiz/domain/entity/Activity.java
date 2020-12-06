package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "activity")
public class Activity extends BaseEntity {
	
	private String title;
	private String description;
	private String code;
	private String createdBy;
}
