package com.uet.hocvv.equiz.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "activity")
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends BaseEntity {
	
	private String title;
	private String description;
	
}
