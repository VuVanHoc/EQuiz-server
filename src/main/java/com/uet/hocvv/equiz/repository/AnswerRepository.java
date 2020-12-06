package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {
}
