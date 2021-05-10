package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Word;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordRepository extends MongoRepository<Word, String> {

	Word findByValue(String value);
	
}
