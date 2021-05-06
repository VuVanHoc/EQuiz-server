package com.uet.hocvv.equiz.controller;

import com.uet.hocvv.equiz.domain.RestBody;
import com.uet.hocvv.equiz.domain.request.SaveDataFromWordAPIRequest;
import com.uet.hocvv.equiz.domain.response.WordDTO;
import com.uet.hocvv.equiz.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/word")
public class WordController {
	
	@Autowired
	WordService wordService;
	
	@GetMapping("randomWords")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> getRandomWord(@RequestParam(value = "number") int number, @RequestParam(value = "level", defaultValue = "EASY") String level) {
		List<WordDTO> wordDTOS = wordService.getRandomWord(number, level);
		RestBody restBody = RestBody.success(wordDTOS);
		return ResponseEntity.ok(restBody);
	}
	
	@PostMapping("saveDataFromWordAPI")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> saveDataFromWordAPI(@RequestBody SaveDataFromWordAPIRequest saveDataFromWordAPIRequest) {
		String s = wordService.saveDataFromWordAPI(saveDataFromWordAPIRequest);
		return ResponseEntity.ok(s);
	}
	
	@GetMapping("initDataDictionary")
	public String initData() throws IOException {
		wordService.initDataFromFile();
		return "SUCCESS";
	}
}
