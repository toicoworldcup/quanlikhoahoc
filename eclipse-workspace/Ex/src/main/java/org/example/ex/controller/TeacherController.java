package org.example.ex.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.ex.entity.*;
import org.example.ex.export.TeacherExcelExporter;
import org.example.ex.repository.TeacherRepository;
import org.example.ex.service.StudentService;
import org.example.ex.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherRepository teacherRepository;
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }
    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.addTeacher(teacher));
    }

    // 3️⃣ Sửa thông tin học viên
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.updateStudent(id, teacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Xóa giao vien với ID " + id + " thành công!");
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<Set<Course>> getCoursesByTeacherId(@PathVariable int id) {
        return ResponseEntity.ok(teacherService.getCoursesByTeacherId(id));
    }
    @GetMapping("/export/excel")
    public void exportTeachersToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String currentDateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String headerValue = "attachment; filename=teachers_" + currentDateTime + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);

        List<Teacher> teacherList = teacherRepository.findAll();
        TeacherExcelExporter excelExporter = new TeacherExcelExporter(teacherList);
        excelExporter.export(response);
    }



}




