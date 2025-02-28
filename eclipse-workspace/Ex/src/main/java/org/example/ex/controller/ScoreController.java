    package org.example.ex.controller;

    import jakarta.servlet.http.HttpServletResponse;
    import org.example.ex.entity.*;
    import org.example.ex.export.LessonExcelExporter;
    import org.example.ex.repository.LessonRepository;
    import org.example.ex.service.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;
    import java.util.Map;
    import java.util.Set;

    @RestController
    @RequestMapping("/scores")
    public class ScoreController {

        @Autowired
        private ScoreService lessonService;
        @Autowired
        private ScoreService scoreService;


        // Lấy tất cả chủ đề
        @GetMapping
        public ResponseEntity<List<Score>> getAllScores() {
            return ResponseEntity.ok(scoreService.getAllScores());
        }

        // Thêm chủ đề mới
        @PostMapping("/enrollment/{enrollmentId}/lesson/{lessonId}")
        public ResponseEntity<Score> addScore(@PathVariable int enrollmentId,@PathVariable int lessonId,@RequestBody Score score) {
            return ResponseEntity.ok(scoreService.addScore(enrollmentId,lessonId,score));
        }


        // Cập nhật thông tin chủ đề
        @PutMapping("/{id}")
        public ResponseEntity<Score> updateScore(@PathVariable int id, @RequestBody Score score) {
            return ResponseEntity.ok(scoreService.updateScore(id, score));
        }

        // Xóa chủ đề theo ID
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteScore(@PathVariable int id) {
            scoreService.deleteScore(id);
            return ResponseEntity.ok("Xóa diem thành công với ID: " + id);
        }

        @GetMapping("/student/{studentId}")
        public ResponseEntity<List<Score>> getScoresByStudentId(@PathVariable int studentId) {
            List<Score> scores = scoreService.getScoresByStudentId(studentId);
            return ResponseEntity.ok(scores);
        }
        @GetMapping("/enrollment/{enrollmentId}")
        public ResponseEntity<List<Score>> getScoresByEnrollmentId(@PathVariable int enrollmentId) {
            List<Score> scores = scoreService.getScoresByEnrollmentId(enrollmentId);
            return ResponseEntity.ok(scores);
        }

        @GetMapping("/student")
        public ResponseEntity<List<Map<String, Object>>> getScoresByStudentName(@RequestParam String studentName) {
            List<Map<String, Object>> response = scoreService.getScoresByStudentName(studentName);
            return ResponseEntity.ok(response);
        }

        @GetMapping("/students-excellent")
        public ResponseEntity<List<Map<String, Object>>> getStudentsAboveSix(@RequestParam String courseName) {
            List<Map<String, Object>> response = scoreService.getStudentsWithAllScoresAboveSix(courseName);
            return ResponseEntity.ok(response);
        }






    }
