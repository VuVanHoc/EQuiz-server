package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.word.Dictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DictionaryRepository extends MongoRepository<Dictionary, String> {
}
