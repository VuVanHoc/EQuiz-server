package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.domain.entity.Word;
import com.uet.hocvv.equiz.domain.request.SaveDataFromWordAPIRequest;
import com.uet.hocvv.equiz.domain.response.WordDTO;
import com.uet.hocvv.equiz.repository.WordRepository;
import com.uet.hocvv.equiz.service.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {
	@Autowired
	WordRepository wordRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void initDataFromFile() throws IOException {
		long totalWord = wordRepository.count();
		if (totalWord > 0) {
			return;
		}
		Resource resource = new ClassPathResource("words.txt");
		InputStream inputStream = resource.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		List<Word> list = bufferedReader.lines().filter(s -> !s.contains("'") && !s.contains("&") && !s.contains("/"))
				.map(Word::new)
				.collect(Collectors.toList());
		
		wordRepository.saveAll(list);
	}
	
	@Override
	public List<WordDTO> getRandomWord(int number, String level) {
		SampleOperation sampleOperation = Aggregation.sample(number);
		Aggregation aggregation = Aggregation.newAggregation(sampleOperation);
		AggregationResults<Word> aggregationResults = mongoTemplate.aggregate(aggregation, "words", Word.class);
		List<Word> words = aggregationResults.getMappedResults();
		return words.stream().map(word -> modelMapper.map(word, WordDTO.class)).collect(Collectors.toList());
	}
	
	@Override
	public String saveDataFromWordAPI(SaveDataFromWordAPIRequest saveDataFromWordAPIRequest) {
		Word word = wordRepository.findByValue(saveDataFromWordAPIRequest.getWord());
		if(word == null) {
			return CommonMessage.FAIL.name();
		}
		if(word.getDataFromWordApi() != null) {
			return CommonMessage.SUCCESS.name();
		}
		word.setDataFromWordApi(saveDataFromWordAPIRequest.getData());
		wordRepository.save(word);
		return CommonMessage.SUCCESS.name();
	}
}
