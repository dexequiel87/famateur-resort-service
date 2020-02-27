package com.degg.famateur.repository.mongo;

import com.degg.famateur.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
}
