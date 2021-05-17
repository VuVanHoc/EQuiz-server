package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, String> {
	
	List<Notification> findByUserIdAndDeletedIsFalse(String userId, Pageable pageable);
	
	int countByUserIdAndDeletedIsFalse(String userId);
	
	int countByUserIdAndReadIsFalseAndDeletedIsFalse(String userId);
	
	List<Notification> findByUserIdAndReadIsFalse(String userId);
	
}
