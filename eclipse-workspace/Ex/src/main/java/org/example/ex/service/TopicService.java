package org.example.ex.service;

import org.example.ex.entity.Course;
import org.example.ex.entity.Student;
import org.example.ex.entity.Teacher;
import org.example.ex.entity.Topic;
import org.example.ex.repository.CourseRepository;
import org.example.ex.repository.StudentRepository;
import org.example.ex.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }
    public Topic updateTopic(int id, Topic updatedTopic) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isPresent()) {
            Topic existingTopic = optionalTopic.get();
            existingTopic.setName(updatedTopic.getName());
            existingTopic.setDescription(updatedTopic.getDescription());
            return topicRepository.save(existingTopic);
        } else {
            throw new RuntimeException("Không tìm thấy chu de với ID: " + id);
        }
    }

    // Gán sinh viên vào chủ đề
    public Topic addStudentToTopic(int topicId, int studentId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ đề với ID: " + topicId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID: " + studentId));

        topic.getStudents().add(student);
        student.getTopics().add(topic);

        topicRepository.save(topic);
        return topic;
    }

    // Gán khoa hoc vào chủ đề
    public Topic addCourseToTopic(int topicId, int courseId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ đề với ID: " + topicId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với ID: " + courseId));

        course.setTopic(topic);              // Gán topic cho course
        topic.getCourses().add(course);      // Thêm course vào danh sách của topic

        courseRepository.save(course);       // Lưu khóa học
        return topicRepository.save(topic);  // Lưu chủ đề và trả về
    }


    public void deleteTopic(int id) {
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if(topicOptional.isPresent()) {
            topicRepository.deleteById(id);
        }
    }
    public Set<Course> getCoursesByTopicId(int id){
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if(topicOptional.isPresent()) {
            return topicOptional.get().getCourses();
        }
        else {
            throw new RuntimeException("Không tìm thấy chu de với ID: " + id);
        }

    }
    public Set<Student> getStudentsByTopicId(int id){
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if(topicOptional.isPresent()) {
            return topicOptional.get().getStudents();
        }
        else {
            throw new RuntimeException("Không tìm thấy chu de với ID: " + id);
        }

    }
}
