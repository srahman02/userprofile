package com.sojibur.userprofile.service

import com.sojibur.userprofile.client.HealthTipsClient
import com.sojibur.userprofile.model.HealthTips
import com.sojibur.userprofile.service.impl.HealthTipsServiceImpl
import spock.lang.Specification

class HealthTipsServiceSpec extends Specification{
    HealthTipsService mockHealthTipsService
    HealthTipsClient mockHealthTipsClient

    def setup(){
        mockHealthTipsClient = Mock()
        mockHealthTipsService = new HealthTipsServiceImpl(mockHealthTipsClient)
    }

    def "should return health tips if the client call is successful"(){
        given:
        def mockUserId = "0000"
        def tips = new ArrayList()
        tips.add("Drink Water")
        tips.add("Drink More Water")
        def mockTips = [id:mockUserId, tips:tips] as HealthTips

        when:
        HealthTips actualTips = mockHealthTipsService.getHealthTipsById(mockUserId)

        then:
        1 * mockHealthTipsClient.getHealthTipsById(mockUserId) >> mockTips
        actualTips == mockTips
    }
}
