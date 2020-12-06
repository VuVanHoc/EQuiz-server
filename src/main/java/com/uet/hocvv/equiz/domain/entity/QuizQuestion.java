package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "quiz_question")
public class QuizQuestion extends BaseEntity {
	
	private String quizId;
	private String questionId;
}
