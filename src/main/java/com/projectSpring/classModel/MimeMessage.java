package com.projectSpring.classModel;

public class MimeMessage {
    private int id;
    private String subject;
    private String sender;
    private String message;

    public MimeMessage(int id, String subject, String sender, String message) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "subject='" + subject + '\'' +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}