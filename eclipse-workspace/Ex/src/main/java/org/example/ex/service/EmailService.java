package org.example.ex.service;

import org.example.ex.entity.EmailHistory;
import org.example.ex.entity.Enrollment;
import org.example.ex.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailHistory emailHistory) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailHistory.getRecipientEmail());
        message.setSubject(emailHistory.getTitle());
        message.setText(
                String.format(
                        "📩 ID: %d\n👤 Người nhận: %s\n📝 Tiêu đề: %s\n🕒 Thời gian gửi: %s\n\n📃 Nội dung:\n%s",
                        emailHistory.getId(),
                        emailHistory.getRecipientEmail(),
                        emailHistory.getTitle(),
                        emailHistory.getSendTime().toString(),
                        emailHistory.getContent()
                )
        );
        mailSender.send(message);
    }
    //  Gửi email danh sách điểm theo khóa học
    public void sendScoreReport(Enrollment enrollment) {
        StringBuilder content = new StringBuilder();
        content.append(String.format("👋 Xin chào %s,\n\n", enrollment.getStudent().getName()));
        content.append(String.format("📚 Khoá học: %s\n\n", enrollment.getCourse().getName()));
        content.append("📜 Danh sách điểm:\n");

        for (Score score : enrollment.getScores()) {
            content.append(String.format("- 📝 Bài học: %s | 🎯 Điểm: %.2f\n",
                    score.getLesson().getName(), score.getScore()));
        }

        content.append("\n💡 Chúc bạn học tốt!\n📬 Hệ thống quản lý khoá học");

        // Tạo email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(enrollment.getStudent().getEmail());
        message.setSubject("📊 Báo cáo điểm theo khoá học: " + enrollment.getCourse().getName());
        message.setText(content.toString());

        mailSender.send(message);
    }
}
