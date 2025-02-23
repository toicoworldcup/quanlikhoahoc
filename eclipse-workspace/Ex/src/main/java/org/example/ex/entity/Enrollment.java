package org.example.ex.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name="enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="enrollment_date")
    private LocalDate Enrollment_date;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Enrollment(Course course, LocalDate enrollment_date, Student student) {
        this.course = course;
        Enrollment_date = enrollment_date;
        this.student = student;
    }

    public Enrollment() {

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollment_date() {
        return Enrollment_date;
    }

    public void setEnrollment_date(LocalDate enrollment_date) {
        Enrollment_date = enrollment_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
