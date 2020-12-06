package com.uet.hocvv.equiz.repository;

import com.uet.hocvv.equiz.domain.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
