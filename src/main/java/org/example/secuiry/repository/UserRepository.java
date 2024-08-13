package org.example.secuiry.repository;

import org.example.secuiry.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
	Optional<UserEntity> findById(String id);
	Optional<UserEntity> findByUserName(String userName);
}

