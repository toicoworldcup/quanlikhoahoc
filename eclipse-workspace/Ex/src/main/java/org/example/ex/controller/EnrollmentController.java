package org.example.ex.controller;

import org.example.ex.entity.*;
import org.example.ex.service.CourseService;
import org.example.ex.service.EnrollmentService;
import org.example.ex.service.LessonService;
import org.example.ex.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // Lấy tất cả chủ đề
    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    // Thêm chủ đề mới
    @PostMapping
    public ResponseEntity<Enrollment> addEnrollment(@RequestBody Enrollment enrollment) {
        return ResponseEntity.ok(enrollmentService.addEnrollment(enrollment));
    }

    // Cập nhật thông tin chủ đề
    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable int id, @RequestBody Enrollment enrollment) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, enrollment));
    }

    // Xóa chủ đề theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable int id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.ok("Xóa dang ki thành công với ID: " + id);
    }






}
