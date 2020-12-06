package com.uet.hocvv.equiz.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz extends BaseEntity{
	
	private String code;
	private String name;
	private String description;
	private String createdBy;
	private List<String> listQuestions;
	
	
}
