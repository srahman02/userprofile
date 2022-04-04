package com.sojibur.userprofile.excption


import com.sojibur.userprofile.exception.InternalServerException
import com.sojibur.userprofile.exception.RestErrorHandler
import com.sojibur.userprofile.exception.TipsNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.mock.http.client.MockClientHttpResponse
import spock.lang.Specification

class RestErrorHandlerSpec extends Specification{
    RestErrorHandler errorHandler

    def setup(){
        errorHandler = new RestErrorHandler()
    }

    def "should throw correct exception if health tips api call fails"(response, exception){
        when:
         errorHandler.handleError(response)

        then:
        thrown exception

        where:
        response                                                                  | exception
        new MockClientHttpResponse(new byte[0], HttpStatus.INTERNAL_SERVER_ERROR) | InternalServerException
        new MockClientHttpResponse(new byte[0], HttpStatus.NOT_FOUND)             | TipsNotFoundException
    }
}
