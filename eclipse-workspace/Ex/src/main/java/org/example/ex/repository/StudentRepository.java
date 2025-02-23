package org.example.ex.repository;

import org.example.ex.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email); // ✔ Kiểm tra email sinh viên


}
