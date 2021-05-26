package com.uet.hocvv.equiz.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
	
	private String id;
	private String content;
	private String questionType;
	private String createdBy;
	private String ownerId;
	private String hint;
	private String level;
	private String subject;
	
	private String responsibleEmail;
	private String responsibleName;
	private String responsiblePhone;
	private String responsibleAvatar;
	private List<AnswerDTO> answerDTOList;
	
}
