package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopicPageController {

    @GetMapping("/topic")
    public String showTopicManagementPage() {
        return "topic"; // Trả về file students.html trong src/main/resources/templates
    }
}
