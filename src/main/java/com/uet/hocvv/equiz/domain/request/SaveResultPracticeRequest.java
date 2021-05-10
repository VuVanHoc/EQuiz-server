package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class SaveResultPracticeRequest {
	
	private String studentId;
	private String activityId;
	private int totalAnswerCorrect;
	private int totalQuestion;
	private Long startTime;
	private Long endTime;
	/*
	 * If student practice random activity, we create new activity from data setup of
	 * activity and then set it as activityId
	 *
	 */
	private String dataSetup;
	private String activityType;
	private String level;
}
