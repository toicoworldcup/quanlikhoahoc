package org.example.ex.service;
import org.example.ex.entity.*;
import org.example.ex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<EmailHistory> getAllEmailHistory() {
        return emailHistoryRepository.findAll();
    }

    public EmailHistory addEmailHistory(EmailHistory emailHistory) {
        String receiverEmail = emailHistory.getRecipientEmail();

        // Kiểm tra email
        boolean isTeacherEmail = teacherRepository.existsByEmail(receiverEmail);
        boolean isStudentEmail = studentRepository.existsByEmail(receiverEmail);

        if (!isTeacherEmail && !isStudentEmail) {
            throw new IllegalArgumentException("Email người nhận không tồn tại trong hệ thống.");
        }

        // Lưu dữ liệu
        EmailHistory savedEmail = emailHistoryRepository.save(emailHistory);

        // Gửi email sau khi lưu
        emailService.sendEmail(savedEmail);

        return savedEmail;
    }

    public EmailHistory updateEmailHistory(int id, EmailHistory updateEmailHistory) {
        Optional<EmailHistory> optionalEmailHistory = emailHistoryRepository.findById(id);

        if (optionalEmailHistory.isPresent()) {
            EmailHistory existingEmailHistory = optionalEmailHistory.get();
            String recipientEmail = updateEmailHistory.getRecipientEmail();

            boolean studentExists = studentRepository.existsByEmail(recipientEmail);
            boolean teacherExists = teacherRepository.existsByEmail(recipientEmail);

            if (!studentExists && !teacherExists) {
                throw new RuntimeException(" Email người nhận không tồn tại trong danh sách sinh viên hoặc giáo viên.");
            }

            //  Cập nhật thông tin
            existingEmailHistory.setRecipientEmail(recipientEmail);
            existingEmailHistory.setTitle(updateEmailHistory.getTitle());
            existingEmailHistory.setContent(updateEmailHistory.getContent());
            existingEmailHistory.setSendTime(updateEmailHistory.getSendTime());

            EmailHistory updatedEmail = emailHistoryRepository.save(existingEmailHistory);

            emailService.sendEmail(updatedEmail);

            return updatedEmail;
        } else {
            throw new RuntimeException("Không tìm thấy bản ghi email với ID: " + id);
        }
    }

    public void deleteEmailHistory(int id) {
        Optional<EmailHistory> optionalEmailHistory = emailHistoryRepository.findById(id);
        if (optionalEmailHistory.isPresent()) {
            emailHistoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy lịch sử email với ID: " + id);
        }
    }
    public void sendScoreReportByEnrollment(int enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản đăng ký với ID: " + enrollmentId));

        emailService.sendScoreReport(enrollment);
    }
}


