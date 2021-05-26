package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
	
	List<Question> findAllByOwnerIdAndDeletedIsFalse(String ownerId, Pageable pageable);
	
	int countByOwnerIdAndDeletedIsFalse(String ownerId);
	
}
