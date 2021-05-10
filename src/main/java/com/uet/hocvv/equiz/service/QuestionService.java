package com.uet.hocvv.equiz.service;

import com.uet.hocvv.equiz.domain.request.CreateQuestionRequest;
import com.uet.hocvv.equiz.domain.request.SearchQuestionDTO;
import com.uet.hocvv.equiz.domain.response.QuestionDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
	
	ResponseListDTO getListQuestion(int pageIndex, int pageSize, SearchQuestionDTO searchQuestionDTO);
	
	QuestionDTO createOrUpdate(CreateQuestionRequest createQuestionRequest) throws Exception;
	
	QuestionDTO getDetail(String id);
	
	String delete(String id);
	
	
}
