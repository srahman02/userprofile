package com.sojibur.userprofile.service.impl;

import com.sojibur.userprofile.client.HealthTipsClient;
import com.sojibur.userprofile.model.HealthTips;
import com.sojibur.userprofile.service.HealthTipsService;
import org.springframework.stereotype.Service;

@Service
public class HealthTipsServiceImpl implements HealthTipsService {

    private final HealthTipsClient healthTipsClient;

    public HealthTipsServiceImpl(HealthTipsClient healthTipsClient){
        this.healthTipsClient = healthTipsClient;
    }

    @Override
    public HealthTips getHealthTipsById(String id) {
        return healthTipsClient.getHealthTipsById(id);
    }
}
