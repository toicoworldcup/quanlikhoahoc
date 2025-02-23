package org.example.ex.service;

import org.example.ex.entity.Course;
import org.example.ex.entity.Enrollment;
import org.example.ex.entity.Student;
import org.example.ex.entity.Topic;
import org.example.ex.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student updateStudent(int id, Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setAddress(updatedStudent.getAddress());
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setPhone(updatedStudent.getPhone());
            return studentRepository.save(existingStudent);
        } else {
            throw new RuntimeException("Không tìm thấy học viên với ID: " + id);
        }
    }
    public void deleteStudent(int id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            studentRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Không tìm thấy học viên với ID: " + id);
        }
    }
    public Set<Enrollment> getEnrollmentsByStudentId(int Id) {
        Optional<Student> optionalStudent = studentRepository.findById(Id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get().getEnrollments();
        } else {
            throw new RuntimeException("Không tìm thấy học viên với ID: " + Id);
        }
    }

    public Set<Topic> getTopicsByStudentId(int Id) {
        Optional<Student> optionalStudent = studentRepository.findById(Id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get().getTopics();
        } else {
            throw new RuntimeException("Không tìm thấy học viên với ID: " + Id);
        }
    }


}
