package com.uet.hocvv.equiz.service;

import com.uet.hocvv.equiz.domain.request.SaveDataFromWordAPIRequest;
import com.uet.hocvv.equiz.domain.response.WordDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface WordService {
	
	void initDataFromFile() throws IOException;
	
	List<WordDTO> getRandomWord(int number);
	
	String saveDataFromWordAPI(SaveDataFromWordAPIRequest saveDataFromWordAPIRequest);
}
