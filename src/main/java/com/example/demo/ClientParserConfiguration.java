package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.http.HttpRequestParser;
import org.springframework.cloud.sleuth.instrument.web.HttpClientRequestParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ClientParserConfiguration {


    @Value("${wavefront.application.name}")
    String applicationName;

    @Value("${rest.target}")
    String restTarget;

    @Bean(name = HttpClientRequestParser.NAME)
    HttpRequestParser myHttpClientRequestParser() {
        return (request, context, span) -> {
            // Span customization
            Object unwrap = request.unwrap();
            if(request.url().matches(restTarget)){
                span.name("My Azure Functions");
                span.tag("_outboundExternalService", "Azure Functions");
                span.tag("_externalApplication", applicationName);
            }
        };
    }

}
