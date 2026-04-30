package com.example.courseservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CourseService {

    private static final String STUDENT_SERVICE_URL = "http://localhost:8081/students";
    private static final String STUDENT_SERVICE_CB = "studentServiceCB";

    private final RestTemplate restTemplate;

    public CourseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object fetchStudentsWithTryCatch() {
        try {
            return restTemplate.getForObject(STUDENT_SERVICE_URL, Object.class);
        } catch (Exception ex) {
            return "Student Service is currently unavailable";
        }
    }

    @CircuitBreaker(name = STUDENT_SERVICE_CB, fallbackMethod = "studentServiceFallback")
    public Object fetchStudentsWithCircuitBreaker() {
        return restTemplate.getForObject(STUDENT_SERVICE_URL, Object.class);
    }

    public Object studentServiceFallback(Throwable throwable) {
        return "Fallback: Student Service Down!";
    }
}
