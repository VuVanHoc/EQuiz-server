package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
}
