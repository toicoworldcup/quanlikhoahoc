package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoursePageController {

    @GetMapping("/course")
    public String showCourseManagementPage() {
        return "course"; // Trả về file students.html trong src/main/resources/templates
    }
}
