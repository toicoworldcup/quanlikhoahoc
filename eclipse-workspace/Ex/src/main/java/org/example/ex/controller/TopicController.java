package org.example.ex.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.ex.entity.Course;
import org.example.ex.entity.Student;
import org.example.ex.entity.Topic;
import org.example.ex.export.TopicExcelExporter;
import org.example.ex.repository.TopicRepository;
import org.example.ex.service.TopicImportService;
import org.example.ex.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicImportService topicImportService;

    // Lấy tất cả chủ đề
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    // Thêm chủ đề mới
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic) {
        return ResponseEntity.ok(topicService.addTopic(topic));
    }

    // Cập nhật thông tin chủ đề
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Topic> updateTopic(@PathVariable int id, @RequestBody Topic topic) {
        return ResponseEntity.ok(topicService.updateTopic(id, topic));
    }

    // Xóa chủ đề theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable int id) {
        topicService.deleteTopic(id);
        return ResponseEntity.ok("Xóa chủ đề thành công với ID: " + id);
    }

    // Lấy danh sách khóa học theo ID chủ đề
    @GetMapping("/{id}/courses")
    public ResponseEntity<Set<Course>> getCoursesByTopicId(@PathVariable int id) {
        return ResponseEntity.ok(topicService.getCoursesByTopicId(id));
    }

    // Lấy danh sách học viên theo ID chủ đề
    @GetMapping("/{id}/students")
    public ResponseEntity<Set<Student>> getStudentsByTopicId(@PathVariable int id) {
        return ResponseEntity.ok(topicService.getStudentsByTopicId(id));
    }
    // Gán sinh viên vào chủ đề
    @PostMapping("/{topicId}/add-student/{studentId}")
    public ResponseEntity<Topic> addStudentToTopic(
            @PathVariable int topicId,
            @PathVariable int studentId) {
        Topic updatedTopic = topicService.addStudentToTopic(topicId, studentId);
        return ResponseEntity.ok(updatedTopic);
    }

    // Gán khóa học vào chủ đề
    @PostMapping("/{topicId}/add-course/{courseId}")
    public ResponseEntity<Topic> addCourseToTopic(
            @PathVariable int topicId,
            @PathVariable int courseId) {
        Topic updatedTopic = topicService.addCourseToTopic(topicId, courseId);
        return ResponseEntity.ok(updatedTopic);
    }

    @PostMapping("/import")
    public String importTopicsFromExcel(@RequestParam String filePath) {
        topicImportService.importTopicsFromExcel(filePath);
        return "✅ Import file Excel thành công!";
    }


    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=topics_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Topic> topicList = topicRepository.findAll();
        TopicExcelExporter excelExporter = new TopicExcelExporter(topicList);
        excelExporter.export(response);
    }

}
