package org.example.ex.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.ex.entity.*;
import org.example.ex.export.LessonExcelExporter;
import org.example.ex.repository.LessonRepository;
import org.example.ex.service.CourseService;
import org.example.ex.service.LessonImportService;
import org.example.ex.service.LessonService;
import org.example.ex.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonImportService lessonImportService;

    // Lấy tất cả chủ đề
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    // Thêm chủ đề mới
    @PostMapping
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson) {
        return ResponseEntity.ok(lessonService.addLesson(lesson));
    }

    @PostMapping("/import")
    public ResponseEntity<String> importLessonsFromExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Không có file nào được gửi!");
        }

        lessonImportService.importLessonsFromExcel(file);
        return ResponseEntity.ok("✅ Import file Excel thành công!");
    }

    // Cập nhật thông tin chủ đề
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable int id, @RequestBody Lesson lesson) {
        return ResponseEntity.ok(lessonService.updateLesson(id, lesson));
    }
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String headerValue = "attachment; filename=lessons_" + currentDateTime + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);

        List<Lesson> lessonList = lessonRepository.findAll();
        LessonExcelExporter excelExporter = new LessonExcelExporter(lessonList);
        excelExporter.export(response);
    }

    // Xóa chủ đề theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable int id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok("Xóa bai hoc thành công với ID: " + id);
    }





}
