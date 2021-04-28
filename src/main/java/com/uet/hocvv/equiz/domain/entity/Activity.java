package com.uet.hocvv.equiz.domain.entity;

import com.uet.hocvv.equiz.domain.enu.ActivityType;
import com.uet.hocvv.equiz.domain.enu.LevelType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "activity")
public class Activity extends BaseEntity {
	
	private String name;
	private String description;
	private String createdBy; //userId of user who created this activity
	private String ownerId; // userId of user has this activity. Can be created or shared user
	private ActivityType type;
	private String url;
	private List<String> tags;
	private String dataSetup;
	private String subject; //chủ đề của trò chơi. (môn học)
	private LevelType level;
	private boolean sharePublic = false;
}
