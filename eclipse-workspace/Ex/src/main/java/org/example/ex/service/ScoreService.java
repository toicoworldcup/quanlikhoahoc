package org.example.ex.service;

import org.example.ex.entity.*;
import org.example.ex.repository.EnrollmentRepository;
import org.example.ex.repository.LessonRepository;
import org.example.ex.repository.ScoreRepository;
import org.example.ex.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }
    public Score addScore(int enrollmentId,int lessonId,Score score) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ban dang ki với ID: " + enrollmentId));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bai hoc với ID: " + lessonId));
        score.setEnrollment(enrollment);
        score.setLesson(lesson);

        // Gán khóa học cho bài học
        scoreRepository.save(score);

        return score;
    }
    public Score updateScore(int id, Score updatedScore) {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        if (optionalScore.isPresent()) {
            Score existingScore = optionalScore.get();


            existingScore.setScore(updatedScore.getScore());

            return scoreRepository.save(existingScore);
        } else {
            throw new RuntimeException("Không tìm thấy bản ghi điểm với ID: " + id);
        }
    }

    public void deleteScore(int id) {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        if (optionalScore.isPresent()) {
            scoreRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Không tìm thấy ban ghi diem với ID: " + id);
        }
    }
    public List<Score> getScoresByEnrollmentId(int Id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(Id);
        if (optionalEnrollment.isPresent()) {
            return optionalEnrollment.get().getScores();
        } else {
            throw new RuntimeException("Không tìm thấy ban dang ki với ID: " + Id);
        }
    }

    public List<Score> getScoresByStudentId(int studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        if (enrollments.isEmpty()) {
            throw new RuntimeException("Không tìm thấy bản đăng ký nào cho sinh viên với ID: " + studentId);
        }

        // Lấy tất cả Score từ các Enrollment
        return enrollments.stream()
                .flatMap(enrollment -> enrollment.getScores().stream())
                .toList();
    }
    public List<Map<String, Object>> getScoresByStudentName(String studentName) {
        List<Object[]> results = scoreRepository.findScoresByStudentName(studentName);

        return results.stream().map(row -> {
            Map<String, Object> scoreData = new HashMap<>();
            scoreData.put("score", row[0]);   // Điểm số
            scoreData.put("student", row[1]); // Tên học sinh
            scoreData.put("lesson", row[2]);  // Tên bài học
            scoreData.put("course", row[3]);  // Tên khóa học
            return scoreData;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getStudentsWithAllScoresAboveSix(String courseName) {
        List<Object[]> results = scoreRepository.findStudentsWithAllScoresAboveSix(courseName);

        return results.stream().map(row -> {
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("id", row[0]);
            studentData.put("name", row[1]);
            return studentData;
        }).collect(Collectors.toList());
    }




}
