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
public class LessonService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }
    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(int id, Lesson updateLesson) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            Lesson existingLesson = optionalLesson.get();
            existingLesson.setDescription(updateLesson.getDescription());
            updateLesson.setName(updateLesson.getName());
            return lessonRepository.save(existingLesson);
        } else {
            throw new RuntimeException("Không tìm thấy bai hoc với ID: " + id);
        }
    }
    public void deleteLesson(int id) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            lessonRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Không tìm thấy bai hoc với ID: " + id);
        }
    }




}

