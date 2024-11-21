package com.moriya.project_moriya_java.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // הגדרת אוטומציה
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
