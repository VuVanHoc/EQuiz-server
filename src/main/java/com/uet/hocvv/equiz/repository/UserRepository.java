package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmail(String s);
	
	Optional<User> findById(String s);
	
	User findByUsername(String username);
	
	List<User> findAllByDeletedIsFalse();
}
