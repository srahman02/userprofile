package com.sojibur.userprofile.service.impl;

import com.sojibur.userprofile.exception.InternalServerException;
import com.sojibur.userprofile.exception.UserNotFoundException;
import com.sojibur.userprofile.model.User;
import com.sojibur.userprofile.model.Users;
import com.sojibur.userprofile.repository.UserProfileRepository;
import com.sojibur.userprofile.service.UserProfileService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }
    @Override
    @SneakyThrows
    public Users getAllUser() {
        try{
            Users users = new Users();
            users.setUsers(userProfileRepository.findAll());
            return users;
        }
        catch(Exception ex){
            throw new InternalServerException("INTERNAL_SERVER_ERROR");
        }
    }

    @Override
    public User createUser(User user) {
        try{
            return userProfileRepository.save(user);
        }
        catch (Exception ex){
            throw new InternalServerException("INTERNAL_SERVER_ERROR");
        }
    }

    @Override
    public User findUserById(String userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("USER_NOT_FOUND_FOR_THE_GIVEN_ID: " + userId));
    }

    @Override
    public void deleteUserById(String userId) {
        User user = userProfileRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("DELETE_FAILED_USER_NOT_FOUND_FOR_THE_GIVEN_ID: " + userId));
        userProfileRepository.delete(user);
    }

    @Override
    public User updateUser(String userId, User user) {
        User savedUser = userProfileRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("UPDATE_FAILED_USER_NOT_FOUND_FOR_THE_GIVEN_ID: " + userId));

        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setEmail(user.getEmail());
        savedUser.setAddress(user.getAddress());
        savedUser.setHobbies(user.getHobbies());
        savedUser.setRegions(user.getRegions());
        return userProfileRepository.save(savedUser);
    }
}
