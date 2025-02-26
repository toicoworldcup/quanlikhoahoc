package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnrollPageController {

    @GetMapping("/enrollment")
    public String showEnrollManagementPage() {
        return "enroll"; // Trả về file students.html trong src/main/resources/templates
    }
}
