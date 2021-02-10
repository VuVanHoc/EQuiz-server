package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "answer")
public class Answer extends BaseEntity{
	
	private String content;
	private String questionId;
	private Boolean correct;
	
}
