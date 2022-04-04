package com.sojibur.userprofileinner.controller;

import com.sojibur.userprofileinner.model.User;
import com.sojibur.userprofileinner.model.Users;
import com.sojibur.userprofileinner.service.UserProfileService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @GetMapping("/users")
    public ResponseEntity<Users> getAllUsers(){
        return ResponseEntity.ok().body(userProfileService.getAllUser());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<>(userProfileService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        return ResponseEntity.ok().body(userProfileService.findUserById(userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId){
        userProfileService.deleteUserById(userId);
        return ResponseEntity.ok().body("RECORD_SUCCESSFULLY_DELETED");
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user){
        return ResponseEntity.ok().body(userProfileService.updateUser(userId, user));
    }
}
