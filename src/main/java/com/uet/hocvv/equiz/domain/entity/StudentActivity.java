package com.uet.hocvv.equiz.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "student_activity")
public class StudentActivity extends BaseEntity {
	
	private String studentId;
	private String activityId;
	private Long startTime;
	private Long endTime;
	private Double score;
	private int totalQuestion;
	private int totalAnswerCorrect;
	
}
