package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.NotificationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "notification")
public class Notification extends BaseEntity {
	
	private NotificationType type;
	private String content;
	private Boolean read;
	private String image;
	private String objectId;
	private String userId;
	
}
