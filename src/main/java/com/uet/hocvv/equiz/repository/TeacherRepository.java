package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
	
	Teacher findByUserId(String userId);
}
