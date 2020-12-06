package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.ClassroomQuiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomQuizRepository extends MongoRepository<ClassroomQuiz, String> {
}
