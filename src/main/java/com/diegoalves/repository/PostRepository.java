package com.diegoalves.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diegoalves.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}
