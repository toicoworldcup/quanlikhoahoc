package org.example.ex.service;

import org.example.ex.entity.EmailHistory;
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
}
