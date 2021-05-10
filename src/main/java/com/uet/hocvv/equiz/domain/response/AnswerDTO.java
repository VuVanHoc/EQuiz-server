package com.uet.hocvv.equiz.domain.response;

import lombok.Data;

@Data
public class AnswerDTO {
	
	private String id;
	private String content;
	private boolean correct;
	private String questionId;
}
