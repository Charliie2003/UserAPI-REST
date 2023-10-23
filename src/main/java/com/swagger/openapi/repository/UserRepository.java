package com.swagger.openapi.repository;
import com.swagger.openapi.service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
