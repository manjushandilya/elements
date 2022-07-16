package com.ads.elements.repository;

import com.ads.elements.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(final String email);

}
