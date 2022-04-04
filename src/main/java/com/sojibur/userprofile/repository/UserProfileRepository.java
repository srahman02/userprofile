package com.sojibur.userprofile.repository;

import com.sojibur.userprofile.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends MongoRepository<User, String> {
}
