package org.example.secuiry.repository;

import org.example.secuiry.model.UserEntity;
import org.example.web.dto.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
	Optional<UserEntity> findById(String id);
	Optional<UserEntity> findByUserName(String userName);
}

