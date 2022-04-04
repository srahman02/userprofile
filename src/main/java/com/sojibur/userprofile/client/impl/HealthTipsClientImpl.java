package com.sojibur.userprofile.client.impl;

import com.sojibur.userprofile.client.HealthTipsClient;
import com.sojibur.userprofile.model.HealthTips;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HealthTipsClientImpl implements HealthTipsClient {

    private final RestTemplate restTemplate;

    public HealthTipsClientImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public HealthTips getHealthTipsById(String id) {
        String url = "http://localhost:8081/api/tips/";
        return restTemplate.getForObject(url+id, HealthTips.class);
    }
}
