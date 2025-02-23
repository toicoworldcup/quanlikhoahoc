package org.example.ex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="score")
    private double score;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = true)
    @JsonIgnore

    private Course course;

    public Lesson(Course course, String description, int id, String name, double score) {
        this.course = course;
        this.description = description;
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Lesson(String description, String name, double score) {
        this.description = description;
        this.name = name;
        this.score = score;
    }

    public Lesson() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
