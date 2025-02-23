package org.example.ex.service;

import org.example.ex.entity.Course;
import org.example.ex.entity.Student;
import org.example.ex.entity.Teacher;
import org.example.ex.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
    public Teacher updateStudent(int id, Teacher updatedTeacher) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isPresent()) {
            Teacher existingTeacher = optionalTeacher.get();
            existingTeacher.setPhoneNumber(updatedTeacher.getPhoneNumber());
            existingTeacher.setName(updatedTeacher.getName());
            existingTeacher.setEmail(updatedTeacher.getEmail());
            existingTeacher.setDescription(updatedTeacher.getDescription());
            return teacherRepository.save(existingTeacher);
        } else {
            throw new RuntimeException("Không tìm thấy học viên với ID: " + id);
        }
    }

    public void deleteTeacher(int id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if(teacherOptional.isPresent()) {
            teacherRepository.deleteById(id);
        }
    }
    public Set<Course> getCoursesByTeacherId(int id){
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if(teacherOptional.isPresent()) {
            return teacherOptional.get().getCourses();

        }
        else {
            throw new RuntimeException("Không tìm thấy học viên với ID: " + id);
        }

    }

}
