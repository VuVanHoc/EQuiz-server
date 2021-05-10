package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class CreateActivityRequest {
	
	private String id;
	private String dataSetup;
	private String level;
	private String name;
	private String type;
	private String responsibleId;
	private String description;
	private String subject;
	
}
