package com.uet.hocvv.equiz.domain.response;

import lombok.Data;

@Data
public class NotificationDTO {
	private String type;
	private Long createdDate;
	private String content;
	private String id;
	private Boolean read;
	private String image;
	private String objectId;
}
