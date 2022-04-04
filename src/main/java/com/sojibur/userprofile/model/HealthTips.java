package com.sojibur.userprofile.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class HealthTips {
    @Id
    private String id;
    private List<String> tips;
}
