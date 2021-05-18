package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignActivityRequest {
	
	private String activityId;
	private String activityName;
	private Long endTime;
	private List<String> classroomIds;
	private String userId;
	
}
