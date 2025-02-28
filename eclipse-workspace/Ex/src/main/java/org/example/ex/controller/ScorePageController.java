package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScorePageController {

    @GetMapping("/score")
    public String showScoreManagementPage() {
        return "score"; // Trả về file students.html trong src/main/resources/templates
    }
}
