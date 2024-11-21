package com.moriya.project_moriya_java.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moriya.project_moriya_java.model.Ads;
import com.moriya.project_moriya_java.model.Categories;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;

public class AdsDto {
    private Long userId;
    private Categories category;
    @NotNull(message = "Date Posted is required")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate datePosted;
    @NotNull(message = "Update Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate upDate;
    private String title;
    private String desc;
    private String city;
    private String location;
    @NotNull(message = "Date Found or Lost is required")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate dateFoundOrLost;
    private String availability;
    private String phone;
    private byte[] image;
    private Ads.AdType type;
    private Boolean status;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCity() {
        return this.city;
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

    public LocalDate getDateFoundOrLost() {
        return dateFoundOrLost;
    }

    public void setDateFoundOrLost(LocalDate dateFoundOrLost) {
        this.dateFoundOrLost = dateFoundOrLost;
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


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Ads.AdType getType() {
        return type;
    }

    public void setType(Ads.AdType type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
