package com.diegoalves.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diegoalves.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

}
