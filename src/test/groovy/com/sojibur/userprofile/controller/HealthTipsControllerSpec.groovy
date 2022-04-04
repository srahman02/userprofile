package com.sojibur.userprofile.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sojibur.userprofile.exception.ApiError
import com.sojibur.userprofile.exception.GlobalExceptionHandler
import com.sojibur.userprofile.exception.TipsNotFoundException
import com.sojibur.userprofile.model.HealthTips
import com.sojibur.userprofile.service.HealthTipsService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HealthTipsControllerSpec extends Specification{
    ObjectMapper objectMapper
    MockMvc mockMvc
    HealthTipsService mockHealthTipsService
    def mockHealthTipsController

    def setup() {
        objectMapper = new ObjectMapper()
        mockHealthTipsService = Mock()
        mockHealthTipsController = new HealthTipsController(mockHealthTipsService)
        mockMvc = MockMvcBuilders.standaloneSetup(mockHealthTipsController)
                .setControllerAdvice(new GlobalExceptionHandler()).build()
    }

    def "should return a user when a GET call to /api/users with an id is successful"() {
        given:
        def mockUserId = "0000"
        def tips = new ArrayList()
        tips.add("Drink Water")
        tips.add("Drink More Water")
        def mockTips = [id:mockUserId, tips:tips] as HealthTips

        when:
        def response = mockMvc.perform(get("/api/tips/"+mockUserId))
                .andExpect(status().isOk())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        HealthTips actualTips = objectMapper.readValue(content, HealthTips.class)

        then:
        1 * mockHealthTipsService.getHealthTipsById(mockUserId) >> mockTips
        actualTips == mockTips
    }

    def "should throw TipsNotFoundException if GET call to /api/users with an id fails"() {
        given:
        def mockUserId = "0000"

        when:
        def response = mockMvc.perform(get("/api/tips/"+mockUserId))
                .andExpect(status().isNotFound())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        ApiError apiError = objectMapper.readValue(content, ApiError.class)

        then:
        1 * mockHealthTipsService.getHealthTipsById(mockUserId) >> {throw new TipsNotFoundException("user-not-found")}
        apiError.status == HttpStatus.NOT_FOUND
        apiError.errors[0].message == "user-not-found"
    }
}
