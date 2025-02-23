package org.example.ex.repository;

import org.example.ex.entity.Course;
import org.example.ex.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
