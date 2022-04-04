package com.sojibur.userprofile.client

import com.sojibur.userprofile.client.impl.HealthTipsClientImpl
import com.sojibur.userprofile.model.HealthTips
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class HealthTipsClientSpec extends Specification{

    RestTemplate mockRestTemplate = Mock()
    HealthTipsClient healthTipsClient

    def setup(){
        healthTipsClient = new HealthTipsClientImpl(mockRestTemplate)
    }

    def "should return health tips if the api call is successful"(){
        given:
        def mockId = "0000"
        def tips = new ArrayList()
        tips.add("Drink Water")
        tips.add("Drink More Water")
        def mockTips = [id:mockId, tips:tips] as HealthTips
        def url = "http://localhost:8081/api/tips/"+mockId

        when:
        HealthTips actualTips = healthTipsClient.getHealthTipsById(mockId)

        then:
        1 * mockRestTemplate.getForObject(url, HealthTips.class) >> mockTips
        actualTips == mockTips
    }
}
