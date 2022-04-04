package com.sojibur.userprofileinner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private String[] hobbies;
    private String[] regions;
}
