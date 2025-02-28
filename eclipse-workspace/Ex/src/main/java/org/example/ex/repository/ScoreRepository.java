package org.example.ex.repository;

import org.example.ex.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    @Query("SELECT sc.score, s.name, l.name, c.name FROM Score sc " +
            "LEFT JOIN sc.enrollment e " +
            "LEFT JOIN e.student s " +
            "LEFT JOIN e.course c " +
            "LEFT JOIN sc.lesson l " +
            "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :studentName, '%'))")
    List<Object[]> findScoresByStudentName(@Param("studentName") String studentName);

    @Query("SELECT s.id, s.name FROM Score sc " +
            "JOIN sc.enrollment e " +
            "JOIN e.student s " +
            "JOIN e.course c " +
            "WHERE c.name = :courseName " +
            "GROUP BY s.id, s.name " +
            "HAVING COUNT(sc) = (SELECT COUNT(l) FROM Lesson l WHERE l.id IN " +
            "(SELECT sc.lesson.id FROM Score sc JOIN sc.enrollment e JOIN e.course c WHERE c.name = :courseName)) " +
            "AND MIN(sc.score) > 6")
    List<Object[]> findStudentsWithAllScoresAboveSix(@Param("courseName") String courseName);
}



