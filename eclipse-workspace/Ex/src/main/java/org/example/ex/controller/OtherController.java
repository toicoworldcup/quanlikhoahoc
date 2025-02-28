package org.example.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    @GetMapping("/other")
    public String showOtherPage() {
        return "other"; // Trả về file students.html trong src/main/resources/templates
    }
}
