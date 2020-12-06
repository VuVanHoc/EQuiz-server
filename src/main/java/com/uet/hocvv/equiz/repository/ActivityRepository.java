package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
}
