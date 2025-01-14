package com.user.repository;

import com.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String message);

    Optional<User> findByEmailId(String emailId);
}
