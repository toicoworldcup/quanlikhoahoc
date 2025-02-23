package org.example.ex.service;
import org.example.ex.entity.*;
import org.example.ex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired CourseRepository courseRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
    public Enrollment addEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(int id, Enrollment updateEnrollment) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if (optionalEnrollment.isPresent()) {
            Enrollment existingEnrollment = optionalEnrollment.get();

            // 🔍 Kiểm tra Course
            Optional<Course> optionalCourse = courseRepository.findById(updateEnrollment.getCourse().getId());
            if (optionalCourse.isEmpty()) {
                throw new RuntimeException("Không tìm thấy khóa học với ID: " + updateEnrollment.getCourse().getId());
            }

            // 🔍 Kiểm tra Student
            Optional<Student> optionalStudent = studentRepository.findById(updateEnrollment.getStudent().getId());
            if (optionalStudent.isEmpty()) {
                throw new RuntimeException(" Không tìm thấy sinh viên với ID: " + updateEnrollment.getStudent().getId());
            }

            // ✅ Cập nhật nếu cả hai tồn tại
            existingEnrollment.setEnrollment_date(updateEnrollment.getEnrollment_date());
            existingEnrollment.setCourse(optionalCourse.get());
            existingEnrollment.setStudent(optionalStudent.get());

            return enrollmentRepository.save(existingEnrollment);
        } else {
            throw new RuntimeException("Không tìm thấy đơn đăng ký với ID: " + id);
        }
    }

    public void deleteEnrollment(int id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(id);
        if (optionalEnrollment.isPresent()) {
            enrollmentRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Không tìm thấy don dang ki với ID: " + id);
        }
    }




}

