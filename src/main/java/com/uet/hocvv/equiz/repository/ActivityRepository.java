package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activity, String> {
	
	List<Activity> findByOwnerIdAndDeletedIsFalse(String ownerId, Pageable pageable);
	
	int countByOwnerIdAndDeletedIsFalse(String ownerId);
}
