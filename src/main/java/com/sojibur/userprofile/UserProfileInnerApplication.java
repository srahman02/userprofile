package com.sojibur.userprofile;

import com.sojibur.userprofile.exception.RestErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserProfileInnerApplication {

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder){
		return builder.errorHandler(new RestErrorHandler()).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserProfileInnerApplication.class, args);
	}

}
