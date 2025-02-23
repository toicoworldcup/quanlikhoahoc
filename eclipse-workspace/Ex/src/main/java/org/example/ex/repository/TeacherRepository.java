package org.example.ex.repository;

import org.example.ex.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    boolean existsByEmail(String email); // ✔ Kiểm tra email giáo viên

}
