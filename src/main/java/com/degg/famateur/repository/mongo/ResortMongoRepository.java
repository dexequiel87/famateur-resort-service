package com.degg.famateur.repository.mongo;

import com.degg.famateur.model.Resort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResortMongoRepository extends MongoRepository<Resort, String> {

}
