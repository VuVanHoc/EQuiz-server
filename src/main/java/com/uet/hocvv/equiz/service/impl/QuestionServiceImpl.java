package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.domain.entity.Answer;
import com.uet.hocvv.equiz.domain.entity.Question;
import com.uet.hocvv.equiz.domain.enu.LevelType;
import com.uet.hocvv.equiz.domain.enu.QuestionType;
import com.uet.hocvv.equiz.domain.request.CreateQuestionRequest;
import com.uet.hocvv.equiz.domain.request.SearchQuestionDTO;
import com.uet.hocvv.equiz.domain.response.AnswerDTO;
import com.uet.hocvv.equiz.domain.response.QuestionDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.repository.AnswerRepository;
import com.uet.hocvv.equiz.repository.QuestionRepository;
import com.uet.hocvv.equiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswerRepository answerRepository;
	
	@Override
	public ResponseListDTO getListQuestion(int pageIndex, int pageSize, SearchQuestionDTO searchQuestionDTO) {
		return null;
	}
	
	@Override
	public QuestionDTO createOrUpdate(CreateQuestionRequest createQuestionRequest) throws Exception {
		Question question;
		if (createQuestionRequest.getId() != null) {
			Optional<Question> questionOptional = questionRepository.findById(createQuestionRequest.getId());
			if (!questionOptional.isPresent()) throw new Exception(CommonMessage.NOT_FOUND.name());
			question = questionOptional.get();
			question.setUpdatedDate(new Date());
			
			List<Answer> answerList = answerRepository.findAllByDeletedIsFalseAndQuestionId(question.getId());
			if(!question.getQuestionType().name().equals(createQuestionRequest.getQuestionType())) {
//				remove all answer of old type
				answerRepository.deleteAll(answerList);
			} else  {
				Map<String, Answer> answerMap = answerList.stream().collect(Collectors.toMap(Answer::getId, answer -> answer));
				for(AnswerDTO answerDTO : createQuestionRequest.getAnswerList()) {
				
				}
			
			}
		} else {
			question = new Question();
		}
		question.setContent(createQuestionRequest.getContent());
		question.setCreatedBy(createQuestionRequest.getCreatedBy());
		question.setHint(createQuestionRequest.getHint());
		question.setLevel(LevelType.valueOf(createQuestionRequest.getLevel()));
		question.setQuestionType(QuestionType.valueOf(createQuestionRequest.getQuestionType()));
		
		questionRepository.save(question);
		
		
		return null;
	}
	
	@Override
	public QuestionDTO getDetail(String id) {
		return null;
	}
	
	@Override
	public String delete(String id) {
		return null;
	}
	
//	public void createList
}
