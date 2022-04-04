package com.sojibur.userprofile.service;

import com.sojibur.userprofile.model.User;
import com.sojibur.userprofile.model.Users;

public interface UserProfileService {
    Users getAllUser();
    User createUser(User user);
    User findUserById(String userId);
    void deleteUserById(String userId);
    User updateUser(String userId, User user);
}
