package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/student")
    public String showStudentManagementPage() {
        return "student-api"; // Trả về file students.html trong src/main/resources/templates
    }
}
