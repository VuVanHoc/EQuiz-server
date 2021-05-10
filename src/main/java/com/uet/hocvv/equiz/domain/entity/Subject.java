package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "subject")
public class Subject extends BaseEntity{
	
	private String title;
	
}
