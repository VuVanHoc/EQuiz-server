package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.ClassroomStudent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomStudentRepository extends MongoRepository<ClassroomStudent, String> {

	int countClassroomStudentByClassroomId(String id);
	
	List<ClassroomStudent> findAllByDeletedIsFalseAndStudentId(String studentId);
	
	List<ClassroomStudent> findAllByDeletedIsFalseAndUserId(String userId);
	
	int countClassroomStudentByUserIdAndDeletedIsFalse(String userId);
	
	ClassroomStudent findByUserIdAndStudentIdAndClassroomIdAndDeletedIsFalse(String userId, String studentId, String classroomId);
	
}
