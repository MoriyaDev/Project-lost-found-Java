package com.moriya.project_moriya_java.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // הגדרת אוטומציה
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // שמירת מזהה המשתמש בלבד

    @ManyToOne
    private Categories category; // קשר עם קטגוריה


    @OneToMany
    @JoinColumn(name = "ad_id") // קישור לפי מזהה המודעה
    private List<Msgs> msgs;  //  @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate datePosted;
  //  @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate upDate;

    private String title; // כותרת
    private String desc; // תיאור
    private String city; // עיר/אזור
    private String location; // מקום ספציפי
   // @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateFoundOrLost; // תאריך אובדן או מציאה
    private String availability; // זמינות למענה טלפוני
    private String phone; // מספר טלפון
    private String imageUrl; // תמונה אופציונלית

    public enum AdType {

        LOST,
        FOUND
    }
    private AdType type; // "lost" or "found"
    private Boolean status; // true אם רלוונטית, false אם לא


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public LocalDate getUpDate() {
        return upDate;
    }

    public void setUpDate(LocalDate upDate) {
        this.upDate = upDate;
    }

    public LocalDate getDateFoundOrLost() {
        return dateFoundOrLost;
    }

    public void setDateFoundOrLost(LocalDate dateFoundOrLost) {
        this.dateFoundOrLost = dateFoundOrLost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public AdType getType() {
        return type;
    }

    public void setType(AdType type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Msgs> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Msgs> msgs) {
        this.msgs = msgs;
    }
}
