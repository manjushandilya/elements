package com.ads.elements.repository;

import com.ads.elements.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(final String role);

}
