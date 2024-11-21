package com.moriya.project_moriya_java.service;

import com.moriya.project_moriya_java.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends  JpaRepository<Categories,Long>{
}
