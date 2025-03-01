package org.example.ex.controller;

import org.example.ex.entity.*;
import org.example.ex.service.CourseService;
import org.example.ex.service.EmailHistoryService;
import org.example.ex.service.LessonService;
import org.example.ex.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/emailhistories")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

    // Lấy tất cả chủ đề
    @GetMapping
    public ResponseEntity<List<EmailHistory>> getAllEmailHistory() {
        return ResponseEntity.ok(emailHistoryService.getAllEmailHistory());
    }


    // Cập nhật thông tin chủ đề
    @PutMapping("/{id}")
    public ResponseEntity<EmailHistory> updateEmailHistory(@PathVariable int id, @RequestBody EmailHistory emailHistory) {
        return ResponseEntity.ok(emailHistoryService.updateEmailHistory(id, emailHistory));
    }

    // Xóa chủ đề theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmailHistory(@PathVariable int id) {
        emailHistoryService.deleteEmailHistory(id);
        return ResponseEntity.ok("Xóa lich su email thành công với ID: " + id);
    }
    @PostMapping("/send")
    public EmailHistory addEmailHistory(@RequestBody EmailHistory emailHistory) {
        emailHistory.setSendTime(LocalDateTime.now());  //
        return emailHistoryService.addEmailHistory(emailHistory);
    }
    //  API gửi email danh sách điểm theo enrollmentId
    @PostMapping("/send-score-report/{enrollmentId}")
    public String sendScoreReport(@PathVariable int enrollmentId) {
        emailHistoryService.sendScoreReportByEnrollment(enrollmentId);
        return "📩 Báo cáo điểm đã được gửi thành công!";
    }





}
