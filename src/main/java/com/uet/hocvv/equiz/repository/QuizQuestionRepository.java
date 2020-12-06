package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.QuizQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestionRepository extends MongoRepository<QuizQuestion, String> {
}
