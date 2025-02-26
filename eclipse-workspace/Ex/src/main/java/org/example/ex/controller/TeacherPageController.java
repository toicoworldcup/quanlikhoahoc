package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherPageController {

    @GetMapping("/teacher")
    public String showTeacherManagementPage() {
        return "teacher"; // Trả về file students.html trong src/main/resources/templates
    }
}
