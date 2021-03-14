package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Classroom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends PagingAndSortingRepository<Classroom, String> {
	
	List<Classroom> findByResponsibleAndDeleted(String responsible, boolean deleted, Pageable pageable);
	
	int countByResponsibleAndDeleted(String responsible, boolean deleted);
}
