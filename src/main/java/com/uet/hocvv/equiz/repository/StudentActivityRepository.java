package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.StudentActivity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentActivityRepository extends PagingAndSortingRepository<StudentActivity, String> {
	
	List<StudentActivity> findByStudentId(String studentId, Pageable pageable);
	
	int countByStudentId(String studentId);
	
}
