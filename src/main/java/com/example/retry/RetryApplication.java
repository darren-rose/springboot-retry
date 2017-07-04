package com.example.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@EnableRetry
@SpringBootApplication
public class RetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetryApplication.class, args);
	}

}

@RestController
class Rest {
    public static final Logger LOGGER = Logger.getLogger(Rest.class.getName());

    @Autowired RetryService retryService;

    @GetMapping("/hello")
    public String hello(){
        LOGGER.info("hello");
        return retryService.serviceCall();
    }

}

@Service
class RetryService {
    public static final Logger LOGGER = Logger.getLogger(RetryService.class.getName());

    private int calls;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 100, multiplier = 2.0, maxDelay = 1000))
    public String serviceCall(){
        LOGGER.info("serviceCall " + LocalDateTime.now());
        if(calls<5){
            calls++;
            LOGGER.info("serviceCall failed");
            throw new IllegalArgumentException("fail 4 times");
        }
        return "serviceCall";
    }

    @Recover
    public String recovery(){
        LOGGER.info("recovery");
        return "recoveryValue";
    }

}