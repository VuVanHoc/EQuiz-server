package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.ClassroomStudent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomStudentRepository extends MongoRepository<ClassroomStudent, String> {

	int countClassroomStudentByClassroomId(String id);
}
