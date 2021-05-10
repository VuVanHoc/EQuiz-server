package com.uet.hocvv.equiz.domain.request;

import com.uet.hocvv.equiz.domain.response.AnswerDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionRequest {
	
	private String id;
	private String content;
	private String questionType;
	private String level;
	private String hint;
	private List<AnswerDTO> answerList;
	private String createdBy;
	
}
