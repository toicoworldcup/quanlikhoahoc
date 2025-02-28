package org.example.ex.service;
import org.example.ex.entity.*;
import org.example.ex.repository.CourseRepository;
import org.example.ex.repository.LessonRepository;
import org.example.ex.repository.StudentRepository;
import org.example.ex.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }
    public Course updateCourse(int id, Course updatedCourse) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setTopic(updatedCourse.getTopic());
            return courseRepository.save(existingCourse);
        } else {
            throw new RuntimeException("Không tìm thấy khoa hoc với ID: " + id);
        }
    }
    public void deleteCourse(int id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            courseRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Không tìm thấy khoa hoc với ID: " + id);
        }
    }
    public Set<Lesson> getLessonsByCourseId(int Id) {
        Optional<Course> optionalCourse = courseRepository.findById(Id);
        if (optionalCourse.isPresent()) {
            return optionalCourse.get().getLessons();
        } else {
            throw new RuntimeException("Không tìm thấy khoa hoc với ID: " + Id);
        }
    }


    public Set<Teacher> getTeachersByCourseId(int Id) {
        Optional<Course> optionalCourse = courseRepository.findById(Id);
        if (optionalCourse.isPresent()) {
            return optionalCourse.get().getTeachers();
        } else {
            throw new RuntimeException("Không tìm thấy khoa hoc với ID: " + Id);
        }
    }

    public Course addTeacherToCourse(int courseId, int teacherId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa hoc với ID: " + courseId));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao vien với ID: " + teacherId));
        course.getTeachers().add(teacher);
        teacher.getCourses().add(course);
        courseRepository.save(course);
        return course;
    }


    public Course addLessonToCourse(int courseId, int lessonId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa hoc với ID: " + courseId));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bai hoc với ID: " + lessonId));
        course.getLessons().add(lesson);
        lesson.setCourse(course);            // Gán khóa học cho bài học
        courseRepository.save(course);
        return course;
    }


}

