package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EHController {

    @GetMapping("/eh")
    public String showEHManagementPage() {
        return "eh"; // Trả về file students.html trong src/main/resources/templates
    }
}
