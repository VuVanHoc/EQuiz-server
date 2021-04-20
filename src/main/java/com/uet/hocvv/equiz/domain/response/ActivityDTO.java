package com.uet.hocvv.equiz.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uet.hocvv.equiz.domain.enu.ActivityType;
import lombok.Data;

@Data
public class ActivityDTO {
	
	private String id;
	private String name;
	private String ownerId;
	private String createdBy;
	private String type;
	private String dataSetup;
	private String level;
	private String subject;
	private Long createdDate;
	private String description;
	private boolean sharePublic;
	private String historyActivityId;
//	Info for list history practice of student
	private Long startTime;
	private Long endTime;
	private Double score;
	private int totalAnswerCorrect;
	private int totalQuestion;
	
//	Info of user created this activity
	private String responsibleEmail;
	private String responsibleName;
	private String responsiblePhone;
	private String responsibleAvatar;
	
}
