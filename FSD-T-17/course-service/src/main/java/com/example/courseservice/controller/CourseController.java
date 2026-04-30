package com.example.courseservice.controller;

import com.example.courseservice.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/students")
    public Object getStudentsFromStudentService(
            @RequestParam(name = "mode", defaultValue = "basic") String mode) {
        if ("cb".equalsIgnoreCase(mode)) {
            return courseService.fetchStudentsWithCircuitBreaker();
        }
        return courseService.fetchStudentsWithTryCatch();
    }
}
