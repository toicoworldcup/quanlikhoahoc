package org.example.ex.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.ex.entity.Enrollment;
import org.example.ex.entity.Student;
import org.example.ex.entity.Topic;
import org.example.ex.export.StudentExcelExporter;
import org.example.ex.repository.StudentRepository;
import org.example.ex.service.StudentImportService;
import org.example.ex.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentImportService studentImportService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    // 3️⃣ Sửa thông tin học viên
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String headerValue = "attachment; filename=students_" + currentDateTime + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);

        List<Student> studentList = studentRepository.findAll();
        StudentExcelExporter excelExporter = new StudentExcelExporter(studentList);
        excelExporter.export(response);
    }

    @PostMapping("/import")
    public String importStudentsFromExcel(@RequestParam String filePath) {
        studentImportService.importStudentsFromExcel(filePath);
        return "✅ Import file Excel thành công!";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Xóa học viên với ID " + id + " thành công!");
    }

    @GetMapping("/{id}/enrollments")
    public ResponseEntity<Set<Enrollment>> getEnrollmentsByStudentId(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getEnrollmentsByStudentId(id));
    }

    @GetMapping("/{id}/topics")
    public ResponseEntity<Set<Topic>> getTopicsByStudentId(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getTopicsByStudentId(id));
    }

    }




