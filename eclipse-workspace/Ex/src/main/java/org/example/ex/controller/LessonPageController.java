package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LessonPageController {

    @GetMapping("/lesson")
    public String showLessonManagementPage() {
        return "lesson"; // Trả về file students.html trong src/main/resources/templates
    }
}
