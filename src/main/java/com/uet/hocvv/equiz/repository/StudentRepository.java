package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
	
	Student findByUserId(String userId);
}
