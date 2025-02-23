package org.example.ex.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.ex.entity.*;
import org.example.ex.export.CourseExcelExporter;
import org.example.ex.repository.CourseRepository;
import org.example.ex.service.CourseService;
import org.example.ex.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    // Lấy tất cả chủ đề
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // Thêm chủ đề mới
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    // Cập nhật thông tin chủ đề
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    // Xóa chủ đề theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Xóa khoa hoc thành công với ID: " + id);
    }

    // Lấy danh sách bai hoc theo id khoa hoc
    @GetMapping("/{id}/lessons")
    public ResponseEntity<Set<Lesson>> getLessonsByCourseId(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getLessonsByCourseId(id));
    }
    // Lấy danh sách giao vien theo id khoa hoc

    @GetMapping("/{id}/teachers")
    public ResponseEntity<Set<Teacher>> getTeachersByCourseId(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getTeachersByCourseId(id));
    }


    // Gán giao vien vao khoa hoc
    @PostMapping("/{courseId}/add-teacher/{teacherId}")
    public ResponseEntity<Course> addTeacherToCourse(
            @PathVariable int teacherId,
            @PathVariable int courseId) {
        Course updateCourse = courseService.addTeacherToCourse(courseId, teacherId);
        return ResponseEntity.ok(updateCourse);
    }
    // Gán bai hoc vao khoa hoc
    @PostMapping("/{courseId}/add-lesson/{lessonId}")
    public ResponseEntity<Course> addLessonToCourse(
            @PathVariable int lessonId,
            @PathVariable int courseId) {
        Course updateCourse = courseService.addLessonToCourse(courseId, lessonId);
        return ResponseEntity.ok(updateCourse);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String headerValue = "attachment; filename=courses_" + currentDateTime + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);

        List<Course> courseList = courseRepository.findAll();
        CourseExcelExporter excelExporter = new CourseExcelExporter(courseList);
        excelExporter.export(response);
    }


}
