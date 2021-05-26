package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.domain.entity.Answer;
import com.uet.hocvv.equiz.domain.entity.Question;
import com.uet.hocvv.equiz.domain.entity.Teacher;
import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.enu.LevelType;
import com.uet.hocvv.equiz.domain.enu.QuestionType;
import com.uet.hocvv.equiz.domain.request.CreateQuestionRequest;
import com.uet.hocvv.equiz.domain.request.SearchQuestionDTO;
import com.uet.hocvv.equiz.domain.response.AnswerDTO;
import com.uet.hocvv.equiz.domain.response.QuestionDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.repository.AnswerRepository;
import com.uet.hocvv.equiz.repository.QuestionRepository;
import com.uet.hocvv.equiz.repository.TeacherRepository;
import com.uet.hocvv.equiz.repository.UserRepository;
import com.uet.hocvv.equiz.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswerRepository answerRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TeacherRepository teacherRepository;
	
	@Override
	public ResponseListDTO getListQuestion(int pageIndex, int pageSize, SearchQuestionDTO searchQuestionDTO) {
		Sort sort = Sort.by("createdDate").descending();
		if (!searchQuestionDTO.getOrderBy().isEmpty()) {
			if (searchQuestionDTO.isOrderByAsc()) sort = Sort.by(searchQuestionDTO.getOrderBy()).ascending();
			else sort = Sort.by(searchQuestionDTO.getOrderBy()).descending();
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		List<Question> questions = questionRepository.findAllByOwnerIdAndDeletedIsFalse(searchQuestionDTO.getUserId(), pageable);
		List<QuestionDTO> questionDTOS = new ArrayList<>();
		User user = userRepository.findById(searchQuestionDTO.getOwnerId()).orElse(new User());
		Teacher teacher = teacherRepository.findByUserId(user.getId());
		for (Question question : questions) {
			QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
			questionDTO.setResponsibleAvatar(user.getAvatar());
			questionDTO.setResponsibleName(user.getFullName());
			questionDTO.setResponsiblePhone(teacher.getPhone());
			questionDTO.setResponsibleEmail(user.getUsername());
			
			questionDTOS.add(questionDTO);
		}
		
		int total = questionRepository.countByOwnerIdAndDeletedIsFalse(searchQuestionDTO.getUserId());
		return new ResponseListDTO(questionDTOS, total);
	}
	
	@Override
	public QuestionDTO createOrUpdate(CreateQuestionRequest createQuestionRequest) throws Exception {
		Question question;
		List<Answer> answerList = new ArrayList<>();
		if (createQuestionRequest.getId() != null) {
			Optional<Question> questionOptional = questionRepository.findById(createQuestionRequest.getId());
			if (!questionOptional.isPresent()) throw new Exception(CommonMessage.NOT_FOUND.name());
			question = questionOptional.get();
			question.setUpdatedDate(new Date());
			
			answerList = answerRepository.findAllByDeletedIsFalseAndQuestionId(question.getId());
			answerRepository.deleteAll(answerList);
			
		} else {
			question = new Question();
		}
		question.setContent(createQuestionRequest.getContent());
		question.setCreatedBy(createQuestionRequest.getCreatedBy());
		question.setHint(createQuestionRequest.getHint());
		question.setLevel(LevelType.valueOf(createQuestionRequest.getLevel()));
		question.setQuestionType(QuestionType.valueOf(createQuestionRequest.getQuestionType()));
		question.setSubject(createQuestionRequest.getSubject());
		question.setOwnerId(createQuestionRequest.getCreatedBy());
		questionRepository.save(question);
		
		for (AnswerDTO answerDTO : createQuestionRequest.getAnswerList()) {
			Answer answer = new Answer();
			answer.setQuestionId(question.getId());
			answer.setContent(answerDTO.getContent());
			answer.setCorrect(answerDTO.isCorrect());
			answerList.add(answer);
		}
		answerRepository.saveAll(answerList);
		return modelMapper.map(question, QuestionDTO.class);
	}
	
	@Override
	public QuestionDTO getDetail(String id) throws Exception {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (!optionalQuestion.isPresent()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		Question question = optionalQuestion.get();
		List<Answer> answerList = answerRepository.findAllByDeletedIsFalseAndQuestionId(question.getId());
		QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
		List<AnswerDTO> answerDTOList = new ArrayList<>();
		for(Answer answer : answerList) {
			AnswerDTO answerDTO = modelMapper.map(answer, AnswerDTO.class);
			answerDTOList.add(answerDTO);
		}
		questionDTO.setAnswerDTOList(answerDTOList);
		return questionDTO;
	}
	
	@Override
	public String delete(String id) throws Exception {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (!optionalQuestion.isPresent()) {
			throw new Exception(CommonMessage.NOT_FOUND.name());
		}
		Question question = optionalQuestion.get();
		question.setDeleted(true);
		question.setUpdatedDate(new Date());
		questionRepository.save(question);
		return CommonMessage.SUCCESS.name();
	}
}
