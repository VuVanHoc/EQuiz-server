package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.QuestionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "question")
public class Question  extends BaseEntity{
	
	private String content;
	private String description;
	private QuestionType questionType;
	private String createdBy;
	private List<String> hashTag;
	
}
