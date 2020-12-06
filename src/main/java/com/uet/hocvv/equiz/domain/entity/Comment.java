package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "comment")
public class Comment extends BaseEntity {
	
	private String content;
	//	id of user who commented
	private String createdBy;
	private String rootComment;
	//	can be ID of teacher,question, quiz, classroom, ..etc
	private String rootObject;
	
}
