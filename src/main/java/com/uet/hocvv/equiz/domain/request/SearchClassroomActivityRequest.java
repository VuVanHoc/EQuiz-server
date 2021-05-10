package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class SearchClassroomActivityRequest {
	
	private String classroomId;
	private String searchText;
	private String orderBy;
	private boolean orderByAsc;
	private String filterActivityType;
	
}
