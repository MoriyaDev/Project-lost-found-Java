package com.moriya.project_moriya_java.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // הגדרת אוטומציה
    private Long id; // user_id
    private String name; // user_name
    private String password; // user_password
    private String email; // user_email
    private String phone; // user_phone
    private String address; // user_address

    @OneToMany(mappedBy = "userId")
    private List<Ads> adsList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Ads> getAdsList() {
        return adsList;
    }

    public void setAdsList(List<Ads> adsList) {
        this.adsList = adsList;
    }
}
