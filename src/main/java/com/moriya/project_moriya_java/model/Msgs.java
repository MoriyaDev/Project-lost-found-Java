package com.moriya.project_moriya_java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Msgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderID; // מזהה המשתמש השולח
    private Long adID; // מזהה המודעה שקשורה להודעה
    private String messageText; // תוכן ההודעה
    private LocalDate sentAt; // תאריך ושעה שליחה
    @ManyToOne
    @JoinColumn(name = "senderID", referencedColumnName = "id", insertable = false, updatable = false)
    private Users sender;  // קשר עם הטבלה של המשתמשים

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }



    public LocalDate getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDate sentAt) {
        this.sentAt = sentAt;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getAdID() {
        return adID;
    }

    public void setAdID(Long adID) {
        this.adID = adID;
    }

    public Long getSenderID() {
        return senderID;
    }

    public void setSenderID(Long senderID) {
        this.senderID = senderID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}