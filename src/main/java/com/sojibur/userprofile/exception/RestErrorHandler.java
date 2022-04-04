package com.sojibur.userprofile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class RestErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.series() == HttpStatus.Series.SERVER_ERROR){
            throw new InternalServerException("INTERNAL_SERVER_ERROR");
        } else if(statusCode == HttpStatus.NOT_FOUND){
            throw new TipsNotFoundException("TIPS_NOT_FOUND_FOR_THE_GIVEN_ID");
            }
        throw new InternalServerException("INTERNAL_SERVER_ERROR");
        }
}
