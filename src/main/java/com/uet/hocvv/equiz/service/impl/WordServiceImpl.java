package com.uet.hocvv.equiz.service.impl;

import com.uet.hocvv.equiz.common.CommonMessage;
import com.uet.hocvv.equiz.domain.entity.word.Dictionary;
import com.uet.hocvv.equiz.domain.enu.LevelType;
import com.uet.hocvv.equiz.domain.request.SaveDataFromWordAPIRequest;
import com.uet.hocvv.equiz.repository.DictionaryRepository;
import com.uet.hocvv.equiz.repository.WordRepository;
import com.uet.hocvv.equiz.service.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
	@Autowired
	WordRepository wordRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	DictionaryRepository dictionaryRepository;
	
	@Override
	public void initDataFromFile() throws IOException {
//		long totalWord = wordRepository.count();
//		if (totalWord > 0) {
//			return;
//		}
//		Resource resource = new ClassPathResource("test.txt");
//		InputStream inputStream = resource.getInputStream();
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//		List<Word> list = bufferedReader.lines().filter(s -> !s.contains("'") && !s.contains("&") && !s.contains("/"))
//				.map(Word::new)
//				.collect(Collectors.toList());
//
//		wordRepository.saveAll(list);

//		List<Dictionary> dictionaries = new ArrayList<>();
//
//		bufferedReader.lines().forEach(line -> {
//			if (line != null && line.matches("@(.*?)\\s\\/(.*?)\\/")) {
//				System.out.println("~line:" + line);
//
//				Dictionary dictionary = new Dictionary();
//				dictionary.setWord(line.trim().substring());
//			}
//		});
	}
	
	@Override
	public List<Dictionary> getRandomWord(int number, String level) {
		SampleOperation sampleOperation = Aggregation.sample(number);
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("level").in(level)), sampleOperation);
		AggregationResults<Dictionary> aggregationResults = mongoTemplate.aggregate(aggregation, "dictionary", Dictionary.class);
		return aggregationResults.getMappedResults();
	}
	
	@Override
	public String saveDataFromWordAPI(SaveDataFromWordAPIRequest saveDataFromWordAPIRequest) {
//		Word word = wordRepository.findByValue(saveDataFromWordAPIRequest.getWord());
//		if (word == null) {
//			return CommonMessage.FAIL.name();
//		}
//		if (word.getDataFromWordApi() != null) {
//			return CommonMessage.SUCCESS.name();
//		}
//		word.setDataFromWordApi(saveDataFromWordAPIRequest.getData());
//		wordRepository.save(word);
		return CommonMessage.SUCCESS.name();
	}
	
	@Override
	public void initData(List<Dictionary> dictionaries) {
		dictionaries.forEach(dictionary -> {
			if (dictionary.getWord().length() <= 5) {
				dictionary.setLevel(LevelType.EASY);
				
			} else if (dictionary.getWord().length() > 5 && dictionary.getWord().length() <= 8) {
				dictionary.setLevel(LevelType.MEDIUM);
				
			} else {
				dictionary.setLevel(LevelType.HARD);
			}
		});
		dictionaryRepository.saveAll(dictionaries);
	}
}
