package com.venkatasai.ratelimitapp.controller;


import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final String ORDER_SERVICE ="orderService" ;

    @GetMapping("/api/limit")

    @RateLimiter(name = "orderService"
    ,fallbackMethod = "fallbackratelimit")
    public ResponseEntity<String> testRate(){
        return new ResponseEntity<String>("Limit API called", HttpStatus.OK);
    }



    public ResponseEntity<String> fallbackratelimit(Exception e) {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1).truncatedTo(ChronoUnit.HOURS);
        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();
        return new  ResponseEntity<String>("TOO many requests retry after "+minutes+" minutes",HttpStatus.TOO_MANY_REQUESTS);
    }


}
