    package org.example.ex.entity;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;

    import java.util.Calendar;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name="course")
    public class Course {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(name="name",nullable = false)
        private String name;
        @Column(name="description")
        private String description;
        @ManyToOne
        @JoinColumn(name = "topic_id")
        private Topic topic;
        @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private Set<Enrollment> enrollments = new HashSet<>();
        @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private Set<Lesson> lessons = new HashSet<>();
        @ManyToMany(mappedBy = "courses")
        @JsonIgnore
        private Set<Teacher> teachers = new HashSet<>();

        public Course() {
        }

        public Course(String description, Set<Enrollment> enrollments, int id, String name, Topic topic) {
            this.description = description;
            this.enrollments = enrollments;
            this.id = id;
            this.name = name;
            this.topic = topic;
        }

        public Course(String description, String name) {
            this.description = description;
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Set<Enrollment> getEnrollments() {
            return enrollments;
        }

        public void setEnrollments(Set<Enrollment> enrollments) {
            this.enrollments = enrollments;
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

        public Topic getTopic() {
            return topic;
        }

        public void setTopic(Topic topic) {
            this.topic = topic;
        }

        public Set<Lesson> getLessons() {
            return lessons;
        }

        public void setLessons(Set<Lesson> lessons) {
            this.lessons = lessons;
        }

        public Set<Teacher> getTeachers() {
            return teachers;
        }

        public void setTeachers(Set<Teacher> teachers) {
            this.teachers = teachers;
        }

    }
