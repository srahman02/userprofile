package com.sojibur.userprofileinner.service;

import com.sojibur.userprofileinner.model.User;
import com.sojibur.userprofileinner.model.Users;
import org.bson.types.ObjectId;

public interface UserProfileService {
    Users getAllUser();
    User createUser(User user);
    User findUserById(String userId);
    void deleteUserById(String userId);
    User updateUser(String userId, User user);
}
