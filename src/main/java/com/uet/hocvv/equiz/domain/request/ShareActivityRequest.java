package com.uet.hocvv.equiz.domain.request;

import lombok.Data;

@Data
public class ShareActivityRequest {

	private String activityName;
	private String activityId;
	private String type;
	private String email;
}
