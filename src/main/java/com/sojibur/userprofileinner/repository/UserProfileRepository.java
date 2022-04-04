package com.sojibur.userprofileinner.repository;

import com.sojibur.userprofileinner.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends MongoRepository<User, String> {
}
