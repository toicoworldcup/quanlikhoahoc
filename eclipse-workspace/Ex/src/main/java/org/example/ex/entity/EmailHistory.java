package org.example.ex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="email_history")
public class EmailHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="recipient_email")
    private String recipientEmail;
    @Column(name="title")
    private String title;
    @Column(name="content")
    private String content;
    @Column(name="send_time")
    private LocalDateTime sendTime;

    public EmailHistory(String content, int id, String recipientEmail, LocalDateTime sendTime, String title) {
        this.content = content;
        this.id = id;
        this.recipientEmail = recipientEmail;
        this.sendTime = sendTime;
        this.title = title;
    }

    public EmailHistory(String content, String recipientEmail, LocalDateTime sendTime, String title) {
        this.content = content;
        this.recipientEmail = recipientEmail;
        this.sendTime = sendTime;
        this.title = title;
    }

    public EmailHistory() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
