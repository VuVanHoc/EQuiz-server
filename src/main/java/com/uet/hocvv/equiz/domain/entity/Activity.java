package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.ActivityType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "activity")
public class Activity extends BaseEntity {
	
	private String title;
	private String description;
	private String code;
	private String createdBy;
	private ActivityType type;
	private String url;
	private List<String> tags;
	
	
}
