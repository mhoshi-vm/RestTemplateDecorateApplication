package com.example.demo;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


public class AwsRestTemplateSpanDecorator implements ClientHttpRequestInterceptor {

    private Tracer tracer;

    public AwsRestTemplateSpanDecorator(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        System.out.println(tracer.toString());
        String uri = String.valueOf(request.getURI());
        if (uri.matches("http://localhost:7071/api/hello")) {
            System.out.println(tracer.toString());
            Span currentSpan = tracer.currentSpan();
            currentSpan.tag("component", "java-aws-sdk ");
            currentSpan.tag("peer.service", "AWSLambda");
        }
        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }
}
