package com.uet.hocvv.equiz.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "classroom_quiz")
public class ClassroomQuiz extends BaseEntity{
	
	private String classroomId;
	private String quizId;
}
