package com.sojibur.userprofile.controller;

import com.sojibur.userprofile.model.HealthTips;
import com.sojibur.userprofile.service.HealthTipsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthTipsController {

    private final HealthTipsService healthTipsService;

    public HealthTipsController(HealthTipsService healthTipsService) {
        this.healthTipsService = healthTipsService;
    }

    @GetMapping("/tips/{id}")
    public ResponseEntity<HealthTips> getHealthTips(@PathVariable String id){
        return ResponseEntity.ok().body(healthTipsService.getHealthTipsById(id));
    }
}
