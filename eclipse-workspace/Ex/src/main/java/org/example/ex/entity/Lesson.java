package org.example.ex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = true)
    @JsonIgnore

    private Course course;
    @OneToMany(mappedBy = "lesson")
    private List<Score> scores;



    public Lesson(Course course, String description, String name) {
        this.course = course;
        this.description = description;
        this.name = name;
    }


    public Lesson(String description, String name) {
        this.description = description;
        this.name = name;
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

}
