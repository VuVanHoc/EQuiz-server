package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.CreateQuestionRequest;
import com.uet.hocvv.equiz.domain.request.SearchQuestionDTO;
import com.uet.hocvv.equiz.domain.response.QuestionDTO;
import com.uet.hocvv.equiz.domain.response.ResponseListDTO;
import com.uet.hocvv.equiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/question")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> getListQuestion(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
	                                         @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
	                                         @RequestBody SearchQuestionDTO searchQuestionDTO) throws Exception {
		ResponseListDTO responseListDTO = questionService.getListQuestion(pageIndex, pageSize, searchQuestionDTO);
		RestBody restBody = RestBody.success(responseListDTO);
		return ResponseEntity.ok(restBody);
		
	}
	
	@PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody CreateQuestionRequest createQuestionRequest) throws Exception {
		QuestionDTO questionDTO = questionService.createOrUpdate(createQuestionRequest);
		RestBody restBody = RestBody.success(questionDTO);
		return ResponseEntity.ok(restBody);
	}
	
	@PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody CreateQuestionRequest createQuestionRequest) throws Exception {
		QuestionDTO questionDTO = questionService.createOrUpdate(createQuestionRequest);
		RestBody restBody = RestBody.success(questionDTO);
		return ResponseEntity.ok(restBody);
	}
	
	@DeleteMapping(value = "delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
		String result = questionService.delete(id);
		RestBody restBody = RestBody.success(result);
		return ResponseEntity.ok(restBody);
	}
}
