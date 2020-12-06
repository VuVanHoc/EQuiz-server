package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.User;
import com.uet.hocvv.equiz.domain.enu.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	
	Optional<User> findById(String s);
	
	User findByUsername(String username);
	
	List<User> findAllByDeletedIsFalse();
}
