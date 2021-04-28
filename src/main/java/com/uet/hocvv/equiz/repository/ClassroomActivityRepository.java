package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.ClassroomActivity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomActivityRepository extends PagingAndSortingRepository<ClassroomActivity, String> {
	
	List<ClassroomActivity> findByClassroomIdAndDeletedIsFalse(String classroomId, Pageable pageable);
	
	int countByClassroomIdAndDeletedIsFalse(String classroomId);
}
