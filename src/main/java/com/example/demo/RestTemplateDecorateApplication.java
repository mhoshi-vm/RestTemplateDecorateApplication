package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@SpringBootApplication
public class RestTemplateDecorateApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateDecorateApplication.class, args);
    }

}

@RestController
class RestExec {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExec.class);

    @Value("${rest.target}")
    String restTarget;

    final RestTemplate restTemplate;

    public RestExec(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping
    public String Rest(@RequestHeader Map<String, String> header) {

        ResponseEntity<String> response = restTemplate.getForEntity(restTarget, String.class);
        ;
        LOGGER.info(response.getBody());

        return "REST Complete";
    }

}