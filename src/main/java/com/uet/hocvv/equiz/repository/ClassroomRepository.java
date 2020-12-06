package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Classroom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends MongoRepository<Classroom, String> {
}
